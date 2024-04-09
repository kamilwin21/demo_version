package com.example.animcare.main_activity_files.MyPets.PetDetailsActivityFiles

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.CalendarView
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.animcare.DatabaseFiles.DataBase
import com.example.animcare.R
import com.example.animcare.main_activity_files.MyPets.Class.Age
import com.example.animcare.main_activity_files.MyPets.Class.CustomDateAndTime
import com.example.animcare.main_activity_files.MyPets.Pet
import com.example.animcare.main_activity_files.MyPets.PetInList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_add_new_pet_details.*
import java.time.*
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddNewPetDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddNewPetDetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val pickImage = 100
    private var imageUri: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null

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
        return inflater.inflate(R.layout.fragment_add_new_pet_details, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("WrongConstant")
    override fun onStart() {
        super.onStart()

        calendarBirthday.visibility = View.GONE
        var counting = 0
        var day = String()
        var monthOfYear: String = String()
        var yearOfCalendar: String = String()


        var pet = PetInList()
        var position = -1
        var bundle = arguments
        if (bundle!!.getSerializable("nameAndType") != null){
            pet = bundle!!.getSerializable("nameAndType") as PetInList
            setFirstlyNameAndType(pet)
            position  = bundle!!.getInt("position")
        }


        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        imageViewAddNewPetDetailsFragment.setOnClickListener {

            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)


        }
        animalBirthdayAddNewPetDetailsFragment.setOnClickListener {
            if (counting == 0){
                image_view_open_close_calendar_add_new_pet_details_fragment
                    .background = requireActivity().getDrawable(R.drawable.ic_baseline_arrow_back_24)
                counting = 1
            }else if (counting ==1){
                image_view_open_close_calendar_add_new_pet_details_fragment
                    .background = requireActivity().getDrawable(R.drawable.ic_baseline_arrow_downward_24)
                counting = 0
            }
            if (calendarBirthday.visibility == View.GONE){
                calendarBirthday.visibility = View.VISIBLE
            }else if (calendarBirthday.visibility == View.VISIBLE){
                calendarBirthday.visibility = View.GONE
            }
        }


        image_view_open_close_calendar_add_new_pet_details_fragment.setOnClickListener {
            if (counting == 0){
                image_view_open_close_calendar_add_new_pet_details_fragment
                    .background = requireActivity().getDrawable(R.drawable.ic_baseline_arrow_back_24)
                counting = 1
            }else if (counting ==1){
                image_view_open_close_calendar_add_new_pet_details_fragment
                    .background = requireActivity().getDrawable(R.drawable.ic_baseline_arrow_downward_24)
                counting = 0
            }
            if (calendarBirthday.visibility == View.GONE){
                calendarBirthday.visibility = View.VISIBLE
            }else if (calendarBirthday.visibility == View.VISIBLE){
                calendarBirthday.visibility = View.GONE
            }



        }


        println("Long this day: ${System.currentTimeMillis()}")
        calendarBirthday.maxDate = System.currentTimeMillis()
        var stringDate = String()
        calendarBirthday.setOnDateChangeListener(object: CalendarView.OnDateChangeListener{
            override fun onSelectedDayChange(
                view: CalendarView, year: Int, month: Int, dayOfMonth: Int
            ) {
                setTextInView(animalBirthdayAddNewPetDetailsFragment, "${dayOfMonth}/${month+1}/${year}")

                //println("Dog's birthday: ${year}: ${month+1}: ${dayOfMonth}")
                stringDate = "${dayOfMonth}/${month+1}/${year}"
                dateFormatter(dayOfMonth.toString(), month.toString(), year.toString())
                var datesDifference = Period.between(convertToLocalDate(stringDate), LocalDate.now())
                if (datesDifference.years > 0){
                    ageViewInfo_add_new_pet_details.setText("${resources.getString(R.string.age)}(${resources.getString(R.string.inYears)})")
                    animalAgeAddNewPetDetailsFragment.setText(datesDifference.years.toString())

                }else if (datesDifference.years == 0){
                    ageViewInfo_add_new_pet_details.setText("${resources.getString(R.string.age)}(${resources.getString(R.string.inMonths)})")
                    animalAgeAddNewPetDetailsFragment.setText(datesDifference.months.toString())
                }

                day = dayOfMonth.toString()
                monthOfYear = (month+1).toString()
                yearOfCalendar = year.toString()






            }


        })



        btn_addNewPetDetailsFragment.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.textFC, LoadingFragment())
                .commit()
            hideKeyBoard()
            requireActivity()
//
//            this.childFragmentManager.beginTransaction()
//                .replace(R.id.fragment_container_view_add_new_details_fragment, LoadingFragment())
//                .commit()
//
//            requireActivity().supportFragmentManager.beginTransaction()
//                .replace(R.id.fragmentContainerViewPetDetailsActivity, LoadingFragment())
//                .commit()

            var path = String()

            if (imageUri != null){


                path = "profilesImages/${FirebaseAuth.getInstance().currentUser!!.uid}/${UUID.randomUUID().toString()}"

                pet.imagePath = path

                val connectionRDB = FirebaseDatabase.getInstance(DataBase.dbReferName).reference
                connectionRDB.child("UsersPets").child(FirebaseAuth.getInstance().currentUser!!.uid)
                    .child(position.toString())
                    .setValue(pet)
                val reference = storageReference?.child(path)
                var uploadTask = reference?.putFile(imageUri!!)
                uploadTask?.addOnFailureListener{


                }?.addOnSuccessListener {
//                    requireActivity().supportFragmentManager.beginTransaction()
//                        .replace(R.id.fragmentContainerViewPetDetailsActivity, this)
//                        .commit()
                    println("Przesłano zdjęcie")

//                    var s = requireActivity().supportFragmentManager.fragments[requireActivity().supportFragmentManager.fragments.size-1]
//                    var size = requireActivity().supportFragmentManager.fragments.size
                    requireActivity().onBackPressed()
                    var pet = conwertEditTextDataToPetObject(path, day, monthOfYear, yearOfCalendar, convertToLocalDate(stringDate))
                    savePetToDB(pet)



                }





//            val uploadTask = reference?.putFile(imageUri!!)
            }

            (requireActivity() as AppCompatActivity).supportActionBar?.displayOptions = 12
            //var w =requireActivity().supportFragmentManager.fragments[requireActivity().supportFragmentManager.fragments.size]

            //savePhotoToDB()
           // println("${w.javaClass.name} || ${w.javaClass.simpleName}")
//            println("${s.javaClass.name} || ${s.javaClass.simpleName}")
//            println("${size}")


        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage){
            imageUri = data?.data
            imageViewAddNewPetDetailsFragment.setImageURI(imageUri)
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun countingDifferenceBetweenTwoDatesAndGetAge(local: LocalDate): Age{
        var dateDifference = Period.between(local, LocalDate.now())
        var age = Age()
        if (dateDifference.years > 0){
            age.setYear(dateDifference.years)
//            age.setValues(animalAge_editing_existing_fragment.text.toString().toInt())
        }else if (dateDifference.years == 0){
            age.setMonths(dateDifference.months)
        }
        return age
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


    private fun setTextInView(editText: EditText, text: String){
        editText.setText(text)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun dateFormatter(day: String, month: String, year: String): ArrayList<String>{



        return arrayListOf()
    }

    private fun setFirstlyNameAndType(nameAndType: PetInList){
        animalNameAddNewPetDetailsFragment.setText(nameAndType.pName)
        animalTypeAddNewPetDetailsFragment.setText(nameAndType.type)
        animalNameAddNewPetDetailsFragment.isEnabled = false
        animalTypeAddNewPetDetailsFragment.isEnabled = false
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun conwertEditTextDataToPetObject(imagePath: String, dayOfMonth: String, month: String, year: String, local: LocalDate): Pet {
        var pet = Pet()
        pet.setNameAndType(PetInList("${animalNameAddNewPetDetailsFragment.text}", "${animalTypeAddNewPetDetailsFragment.text}", ""))
        var age = countingDifferenceBetweenTwoDatesAndGetAge(local)
//        age.setValues(animalAgeAddNewPetDetailsFragment.text.toString().toInt())
        pet.setAge(age)
       // var birthday = CustomDateAndTime("20", "Monday", "January","1","2018","0","0","0","Europe/Warsaw")
        var zonedDateTime = ZonedDateTime.of(LocalDate.of(year.toInt(), month.toInt(), dayOfMonth.toInt()), LocalTime.of(0,0,0), ZoneId.of("Europe/Warsaw"))

        var birthday = CustomDateAndTime("${zonedDateTime.dayOfMonth}","${zonedDateTime.dayOfWeek}", "${zonedDateTime.month}", "${zonedDateTime.monthValue}", "${zonedDateTime.year}", "${zonedDateTime.hour}", "${zonedDateTime.minute}", "${zonedDateTime.second}","${zonedDateTime.zone}")
        pet.setBirthday(birthday)
        pet.setHeight(animalHeightAddNewPetDetailsFragment.text.toString().toDouble())
        pet.setProfileSrc(imagePath)

        return pet
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun savePetToDB(pet: Pet){
        val connection = FirebaseDatabase.getInstance(DataBase.dbReferName).reference
        connection.child("Pets").child(FirebaseAuth.getInstance().currentUser!!.uid)
            .child("${pet.getNameAndType().pName}").setValue(pet)
    }

    private fun savePhotoToDB(): String{


        return "path"
    }

    private fun hideKeyBoard(){
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddNewPetDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddNewPetDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}