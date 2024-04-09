package com.example.animcare.main_activity_files.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.animcare.R
import com.example.animcare.main_activity_files.Fragments.QuizFragmentFiles.QuizActivity
import com.example.animcare.main_activity_files.Fragments.QuizFragmentFiles.QuizDetailsFragment
import com.example.animcare.main_activity_files.Objects.AnimalsTypes
import kotlinx.android.synthetic.main.quiz_type_position_recycler_view.view.*

class QuizTypeAdapter(val context: Context, val supportFragmentManager: FragmentManager):RecyclerView.Adapter<MyQuizTypeAdapter>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyQuizTypeAdapter {
        val inflater = LayoutInflater.from(parent.context)
        val positionList = inflater.inflate(R.layout.quiz_type_position_recycler_view, parent, false)
        return MyQuizTypeAdapter(positionList)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onBindViewHolder(holder: MyQuizTypeAdapter, position: Int) {
        val quizDetailsFragment = QuizDetailsFragment()

        val quizType = holder.view.quiz_type_text_view
        val layout = holder.view.recycler_view_quiz_type_layout



        quizType.text = AnimalsTypes.typesOfQuiz[position]

        when(position){
            0 -> {
                layout.isEnabled  = false
                layout.isActivated = false
                quizType.isEnabled = false

            }
            2 -> {
                layout.isEnabled  = false
                layout.isActivated = false
                quizType.isEnabled = false


            }
            3 -> {
                layout.isEnabled  = false
                quizType.isEnabled = false

            }
        }


        val oldColor = quizType.textColors
        layout.setOnClickListener {
            //it.setBackgroundColor(Color.parseColor("#25875C"))
            println("Text Size: ${quizType.textSize.inc()}")

            quizType.textSize = 26f
//            quizType.setTextColor(Color.parseColor("#25875C"))
            quizType.setTextColor(Color.WHITE)
            it.setBackgroundColor(Color.parseColor("#8EC5AD"))

            Handler().postDelayed({
                val args = Bundle()

                //args.putString("DetailesOfQuiz", quizType.text.toString())
                //quizDetailsFragment.arguments = args
               // goToFragment(quizDetailsFragment)

                it.setBackgroundColor(Color.WHITE)
                quizType.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30.0F)
                quizType.setTextColor(oldColor)
                val intent = Intent(holder.view.context.applicationContext, QuizActivity::class.java)
                intent.putExtra("DetailesOfQuiz", quizType.text.toString())
                holder.view.context.startActivity(intent)






            }, 300)


        }


    }

    override fun getItemCount(): Int {
        return AnimalsTypes.typesOfQuiz.size
    }

    private fun goToFragment(fragment: Fragment){
        var backName = fragment.javaClass.name
        val manager = supportFragmentManager

            val fragmentTransaction = manager.beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .addToBackStack(backName)
            fragmentTransaction.commit()
            println("GoToFragment: ${manager.fragments.size}")


    }



}



class MyQuizTypeAdapter(val view: View):RecyclerView.ViewHolder(view){}