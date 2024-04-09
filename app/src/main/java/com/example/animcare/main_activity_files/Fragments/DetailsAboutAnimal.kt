package com.example.animcare.main_activity_files.Fragments

import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.animcare.Classes.CustomText
import com.example.animcare.Classes.Dog
import com.example.animcare.DatabaseFiles.DataBase
import com.example.animcare.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_details_about_animal.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailsAboutAnimal.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailsAboutAnimal : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        return inflater.inflate(R.layout.fragment_details_about_animal, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val breedOfAnimal = this.arguments?.getString("BreedOfAnimal")
        val dbConnection = FirebaseDatabase.getInstance(DataBase.dbReferName).reference
        if (breedOfAnimal != null) {
            dbConnection.child("Dogs").child(breedOfAnimal)
                .addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()){
                            var dog: Dog = Dog()

                            println("SNAPSHOT: ${snapshot}")
                            for (dogDetails in snapshot.children){



                                when(dogDetails.key){
                                    "character" -> {
                                        dog.character = dogDetails.value.toString()
                                    }
                                    "height" -> {
                                        dog.height = dogDetails.value.toString()
                                    }
                                    "lifetime" ->{
                                        dog.lifetime = dogDetails.value.toString()
                                    }
                                    "race" -> {
                                        dog.race = dogDetails.value.toString()
                                    }
                                    "weight" -> {
                                        dog.weight = dogDetails.value.toString()
                                    }
                                    "origins" -> {
                                        dog.origins = dogDetails.value.toString()
                                    }
                                    "marking" -> {
                                        dog.marking = dogDetails.value.toString()
                                    }
                                    "image" -> {
                                        dog.image = dogDetails.value.toString()
                                    }
                                    "detailedDescription" -> {
//                                        dog.detailedDescription = dogDetails.getValue(CustomText::class.java)!!
                                        println("Dog detailedDescription : ${dogDetails.value}")
                                        dog.detailedDescription = dogDetails.getValue(CustomText::class.java)!!

                                    }

                                }

                            }

                            Picasso.get().load(dog.image).into(imageView)
                            dogName.text = dog.race
                            dogCharacter.text = dog.character
                            origins.text = dog.origins
                            weight.text= dog.weight
                            lifetime.text = dog.lifetime
                            height.text = dog.height

                            for (section in 0 until dog.detailedDescription.sections.size){
                                val sectionName = TextView(context!!.applicationContext)
                                sectionName.text = dog.detailedDescription.sections[section].name
                                sectionName.textSize = 20f

                                val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                                params.setMargins(0,40,0,20)
                                params.gravity = Gravity.CENTER
                                sectionName.layoutParams = params


                                //sectionName.gravity = Gravity.CENTER
                                sectionName.setTypeface(sectionName.typeface, Typeface.BOLD)
                                layout_to_add_value_from_db.addView(sectionName)


                                for (paragraph in 0 until dog.detailedDescription.sections[section].paragraphs.size){
                                    when(dog.detailedDescription.sections[section].paragraphs[paragraph].viewType){
                                        "text" -> {
                                            val paragraphText = TextView(context!!.applicationContext)
                                            paragraphText.text = dog.detailedDescription.sections[section].paragraphs[paragraph].text
                                            paragraphText.textSize = 20f
                                            paragraphText.gravity = Gravity.CENTER

                                            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                                            params.setMargins(0,20,0,0)
                                            params.gravity = Gravity.CENTER
                                            paragraphText.layoutParams = params
                                            layout_to_add_value_from_db.addView(paragraphText)

                                        }
                                        "image" -> {
                                            val link: String = dog.detailedDescription.sections[section].paragraphs[paragraph].imageLocalization.localization
                                            val paragraphImage = ImageView(context!!.applicationContext)
                                            val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                                            paragraphImage.layoutParams = layoutParams
                                            layout_to_add_value_from_db.addView(paragraphImage)
                                            Picasso.get().load(link).into(paragraphImage)

                                        }

                                    }




                                }

                            }



                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        println("Nie znaleziono psa(DetailsAboutAnimal - 53 line)")
                    }


                })
        }else{
            println("Nie ma rasy zwierzaka (DetailsAboutAnimal - 52 line)")
        }




    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailsAboutAnimal.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailsAboutAnimal().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}