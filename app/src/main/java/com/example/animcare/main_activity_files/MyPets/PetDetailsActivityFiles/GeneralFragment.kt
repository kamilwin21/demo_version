package com.example.animcare.main_activity_files.MyPets.PetDetailsActivityFiles

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.animcare.DatabaseFiles.DataBase
import com.example.animcare.R
import com.example.animcare.main_activity_files.MyPets.Pet
import com.example.animcare.main_activity_files.MyPets.PetInList
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_general.*
import kotlinx.android.synthetic.main.nav_header_pet_details.*
import java.io.File
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.abs

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GeneralFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GeneralFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var pickImage = 100
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_general, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()


        ageViewInfo.text = "${resources.getString(R.string.age)}"
        val editingExistingFragment = EditingExistingFragment()


        change_image.setOnClickListener {
            changeImage(imageViewGeneralFragment)
        }


        var pet = Pet()
        var usersPets = arrayListOf<PetInList>()
        var bundle = arguments
        if (bundle!!.getSerializable("pet") != null){
            pet = bundle!!.getSerializable("pet") as Pet


            getPhoto(imageViewGeneralFragment, pet.getProfileSrc())
            animalName.text = pet.getNameAndType().pName
            animalType.text = pet.getNameAndType().type
//            animalAge.text = pet.getAge().getYear().toString()
            usersPets = bundle!!.getSerializable("userPets") as ArrayList<PetInList>

           // animalBirthday.text = "${pet.getBirthday().day}.${pet.getBirthday().month}.${pet.getBirthday().year}r"
            animalBirthday.text = "${customFormatDate(pet.getBirthday().day, pet.getBirthday().monthValue, pet.getBirthday().year)}r"

            var countingAge = countAge(LocalDate.of(pet.getBirthday().year.toInt(), pet.getBirthday().monthValue.toInt(), pet.getBirthday().day.toInt()), ageViewInfo)
            animalAge.text = countingAge
            animalHeight.text = pet.getHeight().toString()


            btn_edit_animal_general_fragment.setOnClickListener {

                var bundle = Bundle()
                println("NameAndType: ${pet.getNameAndType().pName} ${pet.getNameAndType().type}")
                bundle.putSerializable("nameAndType", pet.getNameAndType())
                bundle.putString("age", pet.getAge().getYear().toString())
                bundle.putDouble("height", pet.getHeight())
                bundle.putSerializable("birthday", pet.getBirthday())
                bundle.putSerializable("pet", pet)
                bundle.putSerializable("userPets", usersPets)
                editingExistingFragment.arguments = bundle
                goToEditingAnimalFragments(editingExistingFragment)


            }


        }




    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun convertToLocalDate(date: String):LocalDate{
        var regex = Regex("[0-9]+[\\/][0-9]+[\\/][0-9]+")
        var summary = date.matches(regex)
        var localDate = LocalDate.of(1000,1,1)
        when(summary){
            true -> {
                val separator = "/"
                var currentDate = date.split(separator)
                localDate = LocalDate.of(currentDate[2].toInt(), currentDate[1].toInt(), currentDate[0].toInt())
            }
            false -> {
                Toast.makeText(requireContext().applicationContext, "Nieprawidłowy format daty", Toast.LENGTH_SHORT).show()
            }
        }



        return localDate
    }


    private fun changeImage(image: ImageView){
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, pickImage)


    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == pickImage){
            imageUri = data?.data

            imageViewGeneralFragment.setImageURI(imageUri)
        }
        println("Po wybraniu obrazka")
        var path = "profilesImages/${FirebaseAuth.getInstance().currentUser!!.uid}/${UUID.randomUUID().toString()}"
        var pet = Pet()
        var bundle = arguments
        var position  = bundle!!.getInt("position")

        pet = bundle!!.getSerializable("pet") as Pet
        saveImageToCloudStorage(pet, position, path, imageUri)



    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun countAge(birthday: LocalDate, textView: TextView):String{
        var localDateNow = LocalDate.now()
        var returnedValue = 0
        Period.between(localDateNow, birthday)
        println("Period.beteen: ${Period.between(localDateNow, birthday).years}")
        var year = abs(Period.between(localDateNow, birthday).years)
        if (year > 0){
            returnedValue = year
            setContentInView(textView, requireActivity().getString(R.string.inYears))
        }else if (year == 0){
            returnedValue = abs(Period.between(localDateNow, birthday).months)
            setContentInView(textView, requireActivity().getString(R.string.inMonths))
        }
        return returnedValue.toString()
    }

    private fun setContentInView(textView: TextView, newValue: String){
        var getText = textView.text.toString()
        textView.text = ""
        textView.text = "${getText}(${newValue})"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun customFormatDate(day: String, month: String, year: String):String{
        var date = "${day} ${month} ${year}"
        var localDate = LocalDate.of(year.toInt(), month.toInt(), day.toInt())
        //println("Formatted Date: ${localDate.format(DateTimeFormatter.ofPattern("dd-MMMM-yyyy"))}")
//        println("Formatted Date: ${localDate.format(DateTimeFormatter.ofPattern("dd-MMMM-yyyy", Locale("en")))}")

        return localDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
    }

    private fun updatePetInDB(pet: PetInList){
        val connection = FirebaseDatabase.getInstance(DataBase.dbReferName).reference
        connection.child("Pets").child(FirebaseAuth.getInstance().currentUser!!.uid)
            .child("${pet.pName}").child("profileSrc")
            .setValue("${pet.imagePath}")
        connection.child("Pets").child(FirebaseAuth.getInstance().currentUser!!.uid)
            .child("${pet.pName}").child("nameAndType").child("imagePath")
            .setValue(pet.imagePath)
    }
    private fun removeImageFromCloudStorage(path: String){
        println("RemoveImageFromCloudStorage: ${path}")
        val storageReference = FirebaseStorage.getInstance().reference
        storageReference.child(path).delete().addOnSuccessListener(
            object: OnSuccessListener<Void>{
                override fun onSuccess(p0: Void?) {
                    println("GeneralFragment -> Usunięto zdjęcie z Cloud Storage.")
                }

            }
        ).addOnFailureListener(object: OnFailureListener{
            override fun onFailure(p0: Exception) {
                println("Error: General Fragment -> ${p0}")

            }

        })

    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveImageToCloudStorage(pet: Pet, position: Int, path: String, imageUri: Uri?){

        var storageReference = FirebaseStorage.getInstance().reference
        val reference = storageReference?.child(path)
        var uploadTask = reference?.putFile(imageUri!!)
        uploadTask?.addOnFailureListener{




        }?.addOnSuccessListener {

            removeImageFromCloudStorage(pet.getProfileSrc())
            updateUserPetsToDB(pet, path, position)
            updatePetInDB(pet.getNameAndType())
            println("Przesłano zdjęcie")
            imageViewGeneralFragment.setImageURI(imageUri)

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateUserPetsToDB(pet: Pet, path: String, position: Int){
        var petInList = PetInList()
        petInList = pet.getNameAndType()
        petInList.imagePath = path
        println("UpdateUserPetsToDB: ${position}")
        val connectionRDB = FirebaseDatabase.getInstance(DataBase.dbReferName).reference
        connectionRDB.child("UsersPets").child(FirebaseAuth.getInstance().currentUser!!.uid)
            .child(position.toString())
            .setValue(petInList)

    }



    private fun goToEditingAnimalFragments(editingExistingFragment: Fragment){
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_general_fragment, editingExistingFragment)
            .addToBackStack(editingExistingFragment::class.java.name)
            .commit()
    }
    private fun setPhotoRotation(image: ImageView,imageBackground: ImageView , imgNav: ImageView, isVertically: Boolean){
        when(isVertically){
            true -> {
                image.rotation = 90F
                imgNav.rotation = 90F
                imageBackground.rotation = 90F
            }
            false -> {
                image.rotation = 0F
                imgNav.rotation = 0F
                imageBackground.rotation = 0F
            }
        }
    }
    private fun isVartically(bitmap: Bitmap): Boolean{
        when(bitmap.width > bitmap.height){
            true -> return true
            false -> return false
        }
    }

    private fun getPhoto(imageView: ImageView, path: String){
        val connection = FirebaseDatabase.getInstance(DataBase.dbReferName).reference
        connection.child("DefaultPhoto").addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){

                        for (s in snapshot.children){

                            if (path.isEmpty()){
                            }else{
                                println("Path: ${path}")

                                var loalFile: File = File.createTempFile("tempFile", ".jpeg")
                                var storageReference: StorageReference = FirebaseStorage.getInstance()
                                    .getReference(path)
                                storageReference.getFile(loalFile)
                                    .addOnSuccessListener(object: OnSuccessListener<FileDownloadTask.TaskSnapshot>{
                                        override fun onSuccess(p0: FileDownloadTask.TaskSnapshot?) {
                                            var bitmap: Bitmap = BitmapFactory.decodeFile(loalFile.absolutePath)
                                            imageView.setImageBitmap(bitmap)
                                            requireActivity().imageViewPetDetails.setImageBitmap(bitmap)
                                            imageViewGeneralFragmentBackground.setImageBitmap(bitmap)

                                            when(isVartically(bitmap)){
                                                true -> setPhotoRotation(imageView,imageViewGeneralFragmentBackground , requireActivity().imageViewPetDetails, true)
                                                false -> setPhotoRotation(imageView,imageViewGeneralFragmentBackground, requireActivity().imageViewPetDetails, false)

                                            }
                                        }

                                    })
//                                var storageReference: StorageReference = FirebaseStorage.getInstance().reference

//                                storageReference.child(path)
//                                    .downloadUrl
//                                    .addOnSuccessListener(
//                                        object:  OnSuccessListener<Uri>{
//                                            @RequiresApi(Build.VERSION_CODES.P)
//                                            override fun onSuccess(p0: Uri?) {
//                                                var downloadedUri = p0
//
//
//                                               // Picasso.get().load(downloadedUri.toString()).into(imageView)
//                                                //imageView.setImageBitmap(bitmap)

////                                                val imageV = requireActivity().imageViewPetDetails
////                                                Picasso.get().load(downloadedUri.toString()).into(imageV)
////                                                val bitmap: Bitmap = BitmapFactory.decodeStream(context!!.contentResolver.openInputStream(downloadedUri!!))
//
//                                            }
//
//                                        }
//                                    ).addOnFailureListener(object: OnFailureListener{
//                                        override fun onFailure(p0: Exception) {
//                                            println("GeneralFragment -> Failure while reading image from db")
//                                        }
//
//                                    })


                            }




                        }
                        println("Snapshot: ${snapshot}")

//                        println("SRC: ${src}")
//                        Picasso.get().load(src).into(imageViewPetDetails)

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            }
        )
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GeneralFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GeneralFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}