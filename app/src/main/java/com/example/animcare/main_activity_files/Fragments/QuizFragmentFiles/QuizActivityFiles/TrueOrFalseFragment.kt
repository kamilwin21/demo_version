package com.example.animcare.main_activity_files.Fragments.QuizFragmentFiles.QuizActivityFiles

import android.animation.ObjectAnimator
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import com.example.animcare.Classes.Question
import com.example.animcare.Classes.Quiz
import com.example.animcare.R
import kotlinx.android.synthetic.main.fragment_true_or_false.*
import java.io.Serializable

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TrueOrFalseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TrueOrFalseFragment : Fragment(), Serializable {
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
        return inflater.inflate(R.layout.fragment_true_or_false, container, false)
    }

    override fun onStart() {
        super.onStart()

        progressBarTrueOrFalse.progressTintList = ColorStateList.valueOf(Color.parseColor("#CD038350"))
        var listener = ButtonsListenerForSingleChoise()
        var bundle = arguments
        if (bundle!!.getSerializable("listener") != null){
            listener = bundle!!.getSerializable("listener") as ButtonsListenerForSingleChoise
        }
        var id = 0
        if(bundle!!.getInt("id") != null){
            id = bundle!!.getInt("id")
        }

        if (id == 0){
            var quiz = bundle!!.getSerializable("quiz") as Quiz
            listener.question = questionTF
            listener.quiz = quiz
            listener.id = id
            listener.supportFragmentManager = parentFragmentManager
            listener.buttonsTF = arrayListOf(buttonTrue, buttonFalse)
            listener.progressBarTrueOrFalse = progressBarTrueOrFalse
            printQuestionTrueOrFalse(listener.quiz.questions[id])
            buttonTrue.setOnClickListener(listener)
            buttonFalse.setOnClickListener(listener)



        }else if (id > 0){
            val clickListener = listener
            var animation = ObjectAnimator.ofInt(progressBarTrueOrFalse, "progress", 100,0)
            animation.duration = clickListener.time_max
            animation.interpolator = DecelerateInterpolator(1f)
            animation.start()
            println("Animacja TrueOrFalse start()")


            clickListener.question = questionTF
            clickListener.supportFragmentManager = parentFragmentManager
            clickListener.buttonsTF = arrayListOf(buttonTrue, buttonFalse)
            clickListener.progressBarTrueOrFalse = progressBarTrueOrFalse

            buttonTrue.setOnClickListener(clickListener)
            buttonFalse.setOnClickListener(clickListener)
            printQuestionTrueOrFalse(clickListener.quiz.questions[id])


        }




    }

    private fun printQuestionTrueOrFalse(questionA: Question){
        questionTF.text = questionA.question
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TrueOrFalseFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TrueOrFalseFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}