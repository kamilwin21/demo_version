package com.example.animcare.main_activity_files.adapters

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.animcare.ImportantSettings
import com.example.animcare.R
import com.example.animcare.main_activity_files.Fragments.BreedOfAnimal
import com.example.animcare.main_activity_files.Objects.AnimalsTypes
import kotlinx.android.synthetic.main.animals_types_position_in_list_rw.view.*

class AnimalsTypesAdapter(val context: Context, private val supportFragmentManager: FragmentManager): RecyclerView.Adapter<MyAnimalsTypesAdapter>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAnimalsTypesAdapter {
        val inflater = LayoutInflater.from(parent.context)
        val positionList = inflater.inflate(R.layout.animals_types_position_in_list_rw, parent, false)
        return MyAnimalsTypesAdapter(positionList)

    }

    override fun onBindViewHolder(holder: MyAnimalsTypesAdapter, position: Int) {
        val breedOfAnimals = BreedOfAnimal()

        val layout = holder.view.animals_types_position_in_list_rw
        val typeOfAnimal = holder.view.typeOfAnimal
        typeOfAnimal.text = AnimalsTypes.typesOfAnimals[position]

        val delayedHandler = Handler()

        inActiveOption(layout, typeOfAnimal, position)

        layout.setOnClickListener{
            //it.setBackgroundColor(Color.GREEN)
            //it.setBackgroundColor(Color.parseColor("#25875C"))
            it.setBackgroundColor(ImportantSettings.backgroundColorOnClick)
            typeOfAnimal.setTextColor(Color.WHITE)
            typeOfAnimal.textSize = 28f

            delayedHandler.postDelayed({

                val args = Bundle()
                args.putString("TypeOfAnimal", AnimalsTypes.typesOfAnimals[position])
                breedOfAnimals.arguments = args
                goToFragment(breedOfAnimals)


            }, 300)







        }

    }

    private fun inActiveOption(layout: LinearLayout, textView: TextView , position: Int){
        when{
            position == 0 -> {
                layout.isEnabled = true
            }
            position > 0 -> {
                layout.isEnabled = false
                textView.setTextColor(Color.GRAY)
            }

        }

    }

    override fun getItemCount(): Int {
        return AnimalsTypes.typesOfAnimals.size
    }

    private fun goToFragment(fragment: Fragment){
        var backName = fragment.javaClass.name
        val manager = supportFragmentManager
        var fragmentsInBackStack = manager.popBackStackImmediate(backName,0)

        if (!fragmentsInBackStack){
            val fragmentTransaction = manager.beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .addToBackStack(backName)
            fragmentTransaction.commit()
        }

    }


}




class MyAnimalsTypesAdapter(val view: View): RecyclerView.ViewHolder(view){}