package com.example.animcare.main_activity_files.Fragments.QuizFragmentFiles.QuizActivityFiles

import android.animation.ObjectAnimator
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import com.example.animcare.Classes.Question
import com.example.animcare.Classes.Quiz
import com.example.animcare.ImportantSettings
import com.example.animcare.R
import kotlinx.android.synthetic.main.fragment_multiple_choise.*
import java.io.Serializable

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MultipleChoiseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MultipleChoiseFragment : Fragment(), Serializable {
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
        return inflater.inflate(R.layout.fragment_multiple_choise, container, false)
    }

    override fun onStart() {
        super.onStart()
        progressBarMultipleChoise.progressTintList = ColorStateList.valueOf(Color.parseColor("#CD038350"))
        var listener = ButtonsListenerForSingleChoise()
       var bundle = arguments
        if (bundle!!.getSerializable("listener") != null){
            listener = bundle!!.getSerializable("listener") as ButtonsListenerForSingleChoise

        }

        var id = 0
        if (bundle!!.getInt("id") != null) {
            id = bundle!!.getInt("id")
        }

        if (id == 0){


            val quiz = bundle!!.getSerializable("quiz") as Quiz
            listener.question = question
            listener.quiz = quiz
            listener.id = id
            listener.supportFragmentManager = parentFragmentManager
            buttonNext.setOnClickListener(listener)
            listener.checkboxes = arrayListOf(answerCB1, answerCB2, answerCB3, answerCB4)
            listener.progressBarMultipleChoise = progressBarMultipleChoise
            printQuestion(listener.quiz.questions[id])

        }else if(id > 0){
            val clickListener = listener

            var animation = ObjectAnimator.ofInt(progressBarMultipleChoise, "progress", 100,0)
                animation.duration = clickListener.time_max
                animation.interpolator = DecelerateInterpolator(1f)
                animation.start()
                println("Animacja multiple start()")

            clickListener.question = question
            clickListener.supportFragmentManager = parentFragmentManager
            clickListener.checkboxes = arrayListOf(answerCB1, answerCB2, answerCB3, answerCB4)
            clickListener.progressBarMultipleChoise = progressBarMultipleChoise
           // printQuestion(clickListener.quiz.questions[clickListener.id])


            buttonNext.setOnClickListener(clickListener)
            uncheckingCheckboxes(arrayListOf(answerCB1, answerCB2, answerCB3, answerCB4))

            answerCB1.setOnClickListener{
                it.setBackgroundColor(ImportantSettings.textBackgroundColor)
                if (!answerCB1.isChecked){
                    answerCB1.setBackgroundColor(Color.WHITE)

                }

            }
            answerCB2.setOnClickListener{
                it.setBackgroundColor(ImportantSettings.textBackgroundColor)
                if (!answerCB2.isChecked){
                    answerCB2.setBackgroundColor(Color.WHITE)

                }
            }
            answerCB3.setOnClickListener{
                it.setBackgroundColor(ImportantSettings.textBackgroundColor)
                if (!answerCB3.isChecked){
                    answerCB3.setBackgroundColor(Color.WHITE)

                }
            }
            answerCB4.setOnClickListener{
                it.setBackgroundColor(ImportantSettings.textBackgroundColor)
                if (!answerCB4.isChecked){
                    answerCB4.setBackgroundColor(Color.WHITE)

                }
            }


        }

        printQuestion(listener.quiz.questions[id])


    }

    override fun onResume() {
        super.onResume()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MultipleChoiseFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MultipleChoiseFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    private fun printQuestion(questionA: Question){
        question.text = questionA.question
        answerCB1.text = questionA.answer1
        answerCB2.text = questionA.answer2
        answerCB3.text = questionA.answer3
        answerCB4.text = questionA.answer4
    }

    private fun uncheckingCheckboxes(checkboxes: ArrayList<CheckBox>){
        if (buttonNext.hasOnClickListeners()) {
            answerCB1.isChecked = false
            answerCB2.isChecked = false
            answerCB3.isChecked = false
            answerCB4.isChecked = false

        }

    }
}