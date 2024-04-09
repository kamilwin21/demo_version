package com.example.animcare.main_activity_files.MyPets.Adapters

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.animcare.DatabaseFiles.DataBase
import com.example.animcare.ImportantSettings
import com.example.animcare.R
import com.example.animcare.main_activity_files.MyPets.Class.DataPet
import com.example.animcare.main_activity_files.MyPets.Pet
import com.example.animcare.main_activity_files.MyPets.PetDetailsActivity
import com.example.animcare.main_activity_files.MyPets.PetInList
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.my_pets_position_recycler_view.view.*

@RequiresApi(Build.VERSION_CODES.O)
class MyPetsAdapter(val context: Context, val userPets: ArrayList<PetInList>, var isRemoving: Boolean): RecyclerView.Adapter<MyMyPetsAdapter>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyMyPetsAdapter {
        val inflater = LayoutInflater.from(parent.context)
        val positionList = inflater.inflate(R.layout.my_pets_position_recycler_view, parent, false)
        return MyMyPetsAdapter(positionList)

    }




    override fun onBindViewHolder(holder: MyMyPetsAdapter, position: Int) {



        val layout = holder.view.layout_my_pets_position_recycler_view
        val petName = holder.view.myPet_name_TW
        val petType = holder.view.myPet_type_TW
        val imgView = holder.view.myPet_profile_img_TW
        val removeIcon = holder.view.removingIcon
        val petProfileIMGPath = userPets[position].imagePath
        val innerLinearLayout = holder.view.layout_my_pet
        val removingColor = Color.parseColor("#8A1616")
        val delayHandler = Handler()

        setRandomBackgroundColors(innerLinearLayout, position, context)
        petName.text = userPets[position].pName
        petType.text = userPets[position].type


        layout.setOnClickListener {
            println("Kliknięto: Nazwa: ${petName.text}")
            println("Rasa: ${petType.text}")
            println("Pozycja: ${position}")


        }



        if (isRemoving){
            removeIcon.isVisible = true

            removeIcon.setOnClickListener {

                Toast.makeText(holder.view.context.applicationContext, "${holder.view.context.applicationContext.getString(R.string.hold_to_remove)}", Toast.LENGTH_SHORT).show()

            }
            layout.setOnClickListener {
                Toast.makeText(holder.view.context.applicationContext, "${holder.view.context.applicationContext.getString(R.string.hold_to_remove)}", Toast.LENGTH_SHORT).show()

            }
            removeIcon.setOnLongClickListener {
                var oldColorPetName = petName.currentTextColor
                var oldColorPetType = petType.currentTextColor


                //holder.view.bottomNavMyPet.selectedItemId = R.id.returnRemovePet
                removeIcon.setImageResource(R.drawable.ic_removing_my_pets_red)
                petName.setTextColor(removingColor)
                petType.setTextColor(removingColor)
                val background = layout.background
                var color = 0
                if (background is ColorDrawable){
                    color = background.color
                }

                layout.setBackgroundColor(Color.parseColor("#F1F1F1"))
                delayHandler.postDelayed({

                    if(userPets.size > 0){
                        removeImageFromCloudStorage(position)
                    }
                    removeIcon.setImageResource(R.drawable.ic_removing_my_pets)
                    petName.setTextColor(oldColorPetName)
                    petType.setTextColor(oldColorPetType)
                    layout.setBackgroundColor(color)

                },700)

                true

            }

            layout.setOnLongClickListener {
                var oldColorPetName = petName.currentTextColor
                var oldColorPetType = petType.currentTextColor

                //removeIcon.setImageResource(R.drawable.ic_removing_my_pets_red)
                petName.setTextColor(removingColor)
                petType.setTextColor(removingColor)
                val background = layout.background
                var color = 0
                if (background is ColorDrawable){
                    color = background.color
                }
                layout.setBackgroundColor(Color.parseColor("#F1F1F1"))
                delayHandler.postDelayed({

                    if (userPets.size > 0){
                        removeImageFromCloudStorage(position)

                    }
                    removeIcon.setImageResource(R.drawable.ic_removing_my_pets)
                    petName.setTextColor(oldColorPetName)
                    petType.setTextColor(oldColorPetType)
                    layout.setBackgroundColor(color)

                },700)
                true
            }



        }else{

            layout.setOnClickListener{

                it.setBackgroundColor(ImportantSettings.backgroundColorOnClick)
                petName.setTextColor(ImportantSettings.textColor)
                petType.setTextColor(ImportantSettings.textColor)
                val intent = Intent(holder.view.context.applicationContext, PetDetailsActivity::class.java)
                intent.putExtra("petInList", PetInList("${petName.text}", "${petType.text}",""))
                intent.putExtra("userPets", userPets)
                intent.putExtra("position", position)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                holder.view.context.applicationContext.startActivity(intent)
            }
        }
    }

    private fun setRandomBackgroundColors(layout: LinearLayout, position: Int, context: Context){
        val colors: Array<out String> = context.resources.getStringArray(R.array.colors)
        if (position % colors.size >= 0)
            layout.setBackgroundColor(Color.parseColor(colors[position % colors.size]))





//        when(position % 3){
//            0 -> layout.setBackgroundColor(ContextCompat.getColor(context, R.color.customOrange))
//            1 -> layout.setBackgroundColor(ContextCompat.getColor(context, R.color.customGreen))
//            2 -> layout.setBackgroundColor(ContextCompat.getColor(context, R.color.customBlue))
//
//        }


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
                                var storageReference: StorageReference = FirebaseStorage.getInstance().reference
                                storageReference.child(path)
                                    .downloadUrl
                                    .addOnSuccessListener(
                                        object:  OnSuccessListener<Uri>{
                                            override fun onSuccess(p0: Uri?) {
                                                var downloadedUri = p0

                                                println("p0: ${p0}")
//                                                Picasso.get().load(downloadedUri.toString()).into(imageView)
//                                                val imageV = requireActivity().imageViewPetDetails
//                                                Picasso.get().load(downloadedUri.toString()).into(imageV)

                                            }

                                        }
                                    ).addOnFailureListener(object: OnFailureListener{
                                        override fun onFailure(p0: Exception) {
                                            println("GeneralFragment -> Failure while reading image from db")
                                        }

                                    })


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


    private fun removeImageFromCloudStorage(position: Int){

        var imagePath = String()

        val connection = FirebaseDatabase.getInstance(DataBase.dbReferName).reference
        connection.child("UsersPets").child(FirebaseAuth.getInstance().currentUser!!.uid)
            .child("${position}").addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        val petInList = snapshot.getValue(PetInList::class.java)!!
                        if (imagePath.isNullOrEmpty()){
                            println("========================================")
                            println("Position: ${position}")
                            println("${petInList.pName}")
                            println("${petInList.imagePath}")

                            println("========================================")

                            val storageReference = FirebaseStorage.getInstance().reference
                               // println("StorageReference ${storageReference.child(petInList.imagePath)}")

                            if(petInList.imagePath != ""){
                                println("Znaleziono ścieżkę do obrazka z firestorage")
                                storageReference.child(petInList.imagePath).delete().addOnSuccessListener(object: OnSuccessListener<Void>{
                                    override fun onSuccess(p0: Void?) {
                                        Log.d(ContentValues.TAG, "onSuccess")
                                        println("Deleing success")
                                        println("========================================")
                                        println("Position: ${position}")
                                        println("${petInList.pName}")
                                        println("${petInList.imagePath}")

                                        removeAnimalFromList(userPets[position])
                                        userPets.removeAt(position)
                                        val dbConnection = FirebaseDatabase.getInstance(DataBase.dbReferName).reference
                                        dbConnection.child("UsersPets").child(FirebaseAuth.getInstance().currentUser!!.uid)
                                            .setValue(userPets)
                                        notifyDataSetChanged()
                                        println("========================================")
                                    }


                                }).addOnFailureListener(object : OnFailureListener {
                                    override fun onFailure(p0: Exception) {
                                        println("Error: General Fragment -> ${p0}")

                                    }

                                })

                            }else{
                                println("Nie znaleziono ścieżki do obrazka z firestorage")
                                removeAnimalFromList(userPets[position])
                                userPets.removeAt(position)
                                val dbConnection = FirebaseDatabase.getInstance(DataBase.dbReferName).reference
                                dbConnection.child("UsersPets").child(FirebaseAuth.getInstance().currentUser!!.uid)
                                    .setValue(userPets)
                                notifyDataSetChanged()

                            }


                        }else{
                            println("MyPetsAdapter -> path is null or empty")
                        }


                    }


                }

                override fun onCancelled(error: DatabaseError) {
                }


            })




    }


    private fun removeAnimalFromList(petInList: PetInList){
        val dbC = FirebaseDatabase.getInstance(DataBase.dbReferName).reference
        dbC.child("Pets").child(FirebaseAuth.getInstance().currentUser!!.uid)
            .child("${petInList.pName}").removeValue()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun convertToPet(dataPet: DataPet): Pet {
        var pet = Pet()
        pet.setNameAndType(dataPet.nameAndType)
        pet.setBirthday(dataPet.birthday)
        pet.setWeight(dataPet.weight)
        pet.setStudy(dataPet.studies)
        pet.setHeight(dataPet.height)
        pet.setAge(dataPet.age)
        return pet
    }


    override fun getItemCount(): Int {
        return userPets.size
    }

}



class MyMyPetsAdapter(val view: View): RecyclerView.ViewHolder(view){}