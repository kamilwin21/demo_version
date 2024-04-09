package com.example.animcare.Options.NewImage

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.animcare.DatabaseFiles.DataBase
import com.example.animcare.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.layout_in_new_profile_image_recycler_view.view.*

class NewImageProfileAdapter(context: Context,val current_default_profile_image: ImageView,val recyclerView: View, var defaultImagePath: String): RecyclerView.Adapter<MyNewImageProfileAdapter>(){
    var selectedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyNewImageProfileAdapter {
        val inflater = LayoutInflater.from(parent.context)
        val positionList = inflater.inflate(R.layout.layout_in_new_profile_image_recycler_view, parent, false)
        return MyNewImageProfileAdapter(positionList)
    }

    override fun onBindViewHolder(holder: MyNewImageProfileAdapter, position: Int) {
        val layout = holder.view.linearLayout_in_new_profile_image
        val image = holder.view.image_view_in_new_profile_image
        val selectedImage = holder.view.image_view_in_new_profile_image_selected_image

        if (position == selectedPosition){
            selectedImage.visibility = View.VISIBLE
        }else{
            selectedImage.visibility = View.GONE
        }

        when(position){
            0 ->{
                image.background = ContextCompat.getDrawable(holder.view.context, R.drawable.default_profile_image)

                val imagePath = "android.resource://com.example.animcare/drawable/default_profile_image"

              //  setDefaultProfileImageSelected(imagePath, defaultImagePath, selectedImage)
                if (imagePath == defaultImagePath){
                    selectedImage.visibility = View.VISIBLE
                }

                layout.setOnClickListener{
                    var uri = Uri.parse(imagePath)
                    var inputSteam = holder.view.context.contentResolver.openInputStream(uri)
                    current_default_profile_image.background = Drawable.createFromStream(inputSteam, imagePath)
                    selectImageProfile(imagePath)
                    println("Position : ${position}")
                    defaultImagePath = imagePath
                    refreshRecyclerViewAndSetPosition(position)



                }

            }
            1 -> {
                image.background = ContextCompat.getDrawable(holder.view.context, R.drawable.default_profile_image_2)
                val imagePath = "android.resource://com.example.animcare/drawable/default_profile_image_2"
                if (imagePath == defaultImagePath){
                    selectedImage.visibility = View.VISIBLE
                }


                layout.setOnClickListener{

                    var uri = Uri.parse(imagePath)
                    var inputSteam = holder.view.context.contentResolver.openInputStream(uri)
                    current_default_profile_image.background = Drawable.createFromStream(inputSteam, imagePath)
                    selectImageProfile(imagePath)
                    defaultImagePath = imagePath
                    refreshRecyclerViewAndSetPosition(position)


                }


            }
            2 -> {
                image.background = ContextCompat.getDrawable(holder.view.context, R.drawable.default_profile_image_3)
                val imagePath = "android.resource://com.example.animcare/drawable/default_profile_image_3"
                if (imagePath == defaultImagePath){
                    selectedImage.visibility = View.VISIBLE
                }

                layout.setOnClickListener{
                    var uri = Uri.parse(imagePath)
                    var inputSteam = holder.view.context.contentResolver.openInputStream(uri)
                    current_default_profile_image.background = Drawable.createFromStream(inputSteam, imagePath)
                    selectImageProfile(imagePath)
                    defaultImagePath = imagePath
                    refreshRecyclerViewAndSetPosition(position)


                    println("Position : ${position}")






                }

            }
            3 -> {
                image.background = ContextCompat.getDrawable(holder.view.context, R.drawable.default_profile_image_4)
                val imagePath = "android.resource://com.example.animcare/drawable/default_profile_image_4"
                if (imagePath == defaultImagePath){
                    selectedImage.visibility = View.VISIBLE
                }

                layout.setOnClickListener{
                    var uri = Uri.parse(imagePath)
                    var inputSteam = holder.view.context.contentResolver.openInputStream(uri)
                    current_default_profile_image.background = Drawable.createFromStream(inputSteam, imagePath)
                    selectImageProfile(imagePath)
                    defaultImagePath = imagePath
                    refreshRecyclerViewAndSetPosition(position)

                    println("Position : ${position}")


                }
            }
            4 -> {
                image.background = ContextCompat.getDrawable(holder.view.context, R.drawable.default_profile_image_5)
                val imagePath = "android.resource://com.example.animcare/drawable/default_profile_image_5"
                if (imagePath == defaultImagePath){
                    selectedImage.visibility = View.VISIBLE
                }

                layout.setOnClickListener{
                    var uri = Uri.parse(imagePath)
                    var inputSteam = holder.view.context.contentResolver.openInputStream(uri)
                    current_default_profile_image.background = Drawable.createFromStream(inputSteam, imagePath)
                    selectImageProfile(imagePath)
                    defaultImagePath = imagePath
                    refreshRecyclerViewAndSetPosition(position)

                    println("Position : ${position}")



                }
            }
            5 -> {
                image.background = ContextCompat.getDrawable(holder.view.context, R.drawable.default_profile_image_6)
                val imagePath = "android.resource://com.example.animcare/drawable/default_profile_image_6"
                if (imagePath == defaultImagePath){
                    selectedImage.visibility = View.VISIBLE
                }

                layout.setOnClickListener{
                    var uri = Uri.parse(imagePath)
                    var inputSteam = holder.view.context.contentResolver.openInputStream(uri)
                    current_default_profile_image.background = Drawable.createFromStream(inputSteam, imagePath)
                    selectImageProfile(imagePath)
                    defaultImagePath = imagePath
                    refreshRecyclerViewAndSetPosition(position)

                    println("Position : ${position}")
                }


            }
            6 -> {
                image.background = ContextCompat.getDrawable(holder.view.context, R.drawable.default_profile_image_7)
                val imagePath = "android.resource://com.example.animcare/drawable/default_profile_image_7"
                if (imagePath == defaultImagePath){
                    selectedImage.visibility = View.VISIBLE
                }

                layout.setOnClickListener{
                    var uri = Uri.parse(imagePath)
                    var inputSteam = holder.view.context.contentResolver.openInputStream(uri)
                    current_default_profile_image.background = Drawable.createFromStream(inputSteam, imagePath)
                    selectImageProfile(imagePath)
                    defaultImagePath = imagePath
                    refreshRecyclerViewAndSetPosition(position)
                    println("Position : ${position}")

                }
            }

            else -> {
//                image.background = ContextCompat.getDrawable(holder.view.context, R.drawable.default_profile_image)

            }

        }










    }
    private fun setDefaultProfileImageSelected(imagePath: String, currentPath: String, selectedImage: ImageView){
        if (imagePath == currentPath){
            selectedImage.visibility = View.VISIBLE
        }else{
            selectedImage.visibility = View.GONE
        }


    }

    private fun refreshRecyclerViewAndSetPosition(position: Int){
        selectedPosition = position
        notifyDataSetChanged()
    }
    private fun setVisibilityOnSelectedImageView(selectedImage: ImageView){
//        if (selectedImage.visibility == View.GONE){
//            selectedImage.visibility = View.VISIBLE
//        }else{
//            selectedImage.post {
//                selectedImage.visibility = View.GONE
//            }
//        }
    }


    override fun getItemCount(): Int {
        return 7
    }


    private fun selectImageProfile(imagePath: String){
        val dbConnection = FirebaseDatabase.getInstance(DataBase.dbReferName).reference
        dbConnection.child("Users").child(FirebaseAuth.getInstance().currentUser!!.uid)
            .child("imagePath").setValue(imagePath)
    }


}



class MyNewImageProfileAdapter(val view: View): RecyclerView.ViewHolder(view){}