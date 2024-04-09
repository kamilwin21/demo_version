package com.example.animcare.main_activity_files.Fragments.QuizFragmentFiles.QuizActivityFiles

import android.animation.ObjectAnimator
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.animcare.Classes.Question
import com.example.animcare.Classes.Quiz
import com.example.animcare.R
import kotlinx.android.synthetic.main.activity_quiz.*
import kotlinx.android.synthetic.main.fragment_single_choice.*
import java.io.Serializable

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SingleChoiceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SingleChoiceFragment : Fragment(), Serializable {
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
        return inflater.inflate(R.layout.fragment_single_choice, container, false)
    }


    override fun onStart() {
        super.onStart()

        requireActivity().layoutQuizActivity.gravity = Gravity.CENTER
        progressBar.progressTintList = ColorStateList.valueOf(Color.parseColor("#CD038350"))


        var layout = LinearLayout(requireContext())
        var textView = TextView(requireContext())
        textView.text = "Example text"
        layout.addView(textView)





        var buttonListener = ButtonsListenerForSingleChoise()

            val bundle = arguments
            var quiz: Quiz = Quiz()

        if (bundle!!.getSerializable("quiz") != null){
            val quizBundle = bundle!!.getSerializable("quiz")
            val idBundle = bundle!!.getInt("id")
            quiz = quizBundle as Quiz
        }

            var id = 0
            if (bundle!!.getSerializable("listener") != null){
                id = bundle!!.getInt("id")

            }

        if (id == 0 ){
            buttonListener = ButtonsListenerForSingleChoise(
                question,
                arrayListOf(answer1, answer2, answer3, answer4),
                quiz,
                id,
                parentFragmentManager,
                progressBar
            )


            question.setOnClickListener(buttonListener)
            answer1.setOnClickListener(buttonListener)
            answer2.setOnClickListener(buttonListener)
            answer3.setOnClickListener(buttonListener)
            answer4.setOnClickListener(buttonListener)


        }
        if (id > 0){

            var listener = bundle!!.getSerializable("listener") as ButtonsListenerForSingleChoise


            var animation = ObjectAnimator.ofInt(progressBar, "progress", 100,0)
                animation.duration = listener.time_max
                animation.interpolator = DecelerateInterpolator(1f)
                animation.start()
                println("Animacja multiple start()")

            listener.progressBar = progressBar
            listener.supportFragmentManager = parentFragmentManager
            listener.buttons = arrayListOf()
            listener.buttons = arrayListOf(answer1, answer2, answer3, answer4)
            listener.question = question

            printQuestion(listener.quiz.questions[id])

            answer1.setOnClickListener(listener)
            answer2.setOnClickListener(listener)
            answer3.setOnClickListener(listener)
            answer4.setOnClickListener(listener)

        }





















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
         * @return A new instance of fragment SingleChoiceFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SingleChoiceFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun printQuestion(questionA: Question){
        question.text = questionA.question
        answer1.text = questionA.answer1
        answer2.text = questionA.answer2
        answer3.text = questionA.answer3
        answer4.text = questionA.answer4
    }
}