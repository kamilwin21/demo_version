package com.example.animcare.main_activity_files.Fragments.QuizFragmentFiles

import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.animcare.Classes.Quiz
import com.example.animcare.Classes.UserAnswer
import com.example.animcare.ImportantSettings
import com.example.animcare.R
import com.example.animcare.main_activity_files.Fragments.QuizFragmentFiles.QuizActivityFiles.ButtonsListenerForSingleChoise
import kotlinx.android.synthetic.main.activity_quiz.*
import kotlinx.android.synthetic.main.fragment_summary.*
import java.io.Serializable

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SummaryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SummaryFragment : Fragment(), Serializable {
    internal  var status = 0
    private val handler = Handler()
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
        return inflater.inflate(R.layout.fragment_summary, container, false)
    }



    override fun onStart() {
        super.onStart()




        requireActivity().layoutQuizActivity.gravity = Gravity.NO_GRAVITY
        val bundle = arguments
        val quizBundle = bundle!!.getSerializable("quiz")
        val quiz: Quiz = quizBundle as Quiz
        val userAnswers = bundle!!.getSerializable("userAnswers") as ArrayList<UserAnswer>
        val listener = bundle!!.getSerializable("listener") as ButtonsListenerForSingleChoise


        println("====================================================================")
        println("Odpowiedzi użytkownika u SummaryFragment")

        for (uA in userAnswers){

            if (uA.question!!.type == "SingleChoise"){

                //println("UserAnswers: ${uA.question?.question} | ${uA.correctAnswer} | ${uA.userAnswer}")
                println()
                println()
                println()
                println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
                println("Typ pytania: ${uA.question?.type}")
                println("Pytanie: ${uA.question?.question}")
                println("Prawidłowa odpowiedź: ${uA.question?.correctAnswer}")
                println("Odpowiedź użytkownika: ${uA.userAnswer}")
                println("Punkty: ${uA.question!!.points}")
                println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
            }else if (uA.question?.type == "MultipleChoise"){

                println()
                println()
                println()
                println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
                println("Typ pytania: Multiplechoise")
                //println("UserAnswers: ${uA.question?.question} | ${uA.correctAnswer} | ${uA.userAnswer}")
                println("Pytanie: ${uA.question?.question}")
                println("Punkty: ${uA.question!!.points}")

              //  println("UserAnswersARRAY: ${uA.correctAnswers}")
                for (correctAnswer in uA.question?.correctAnswers!!){
                    println("   Prawidłowa odpowiedź: ${correctAnswer}")
                }

                for (i in uA.userAnswers!!){
                    println("           Odpowiedzi użytkownika: ${i}")

                }
                println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")

            }else if (uA.question?.type == "TrueOrFalse"){

                println()
                println()
                println()
                println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
                println("Typ pytania: ${uA.question?.type}")
                println("Pytanie: ${uA.question?.question}")
                println("Prawidłowa odpowiedź: ${uA.question?.correctAnswer}")
                println("Odpowiedź użytkownika: ${uA.userAnswer}")
                println("Punkty: ${uA.question!!.points}")
                println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")

            }



        }
        println("====================================================================")



        val gettedPoints = countingPoints(userAnswers, quiz.amountQuestion.toInt())
        val progressBarTimer= setProgressBar(gettedPoints)

        txtProgress.text = "${progressBarTimer}%"
        progressBar.secondaryProgress = progressBarTimer



        //=================================================






    }



    private fun setProgressBar(gettedPoints: ArrayList<Int>): Int{

        val progressBar = progressBar
        var getted: Int = gettedPoints[0]
        var total: Int = gettedPoints[1]
        val percentagePoints: Int = ((getted.toDouble()/total.toDouble()) * 100.0).toInt()
        println("PercentagePoints: ${percentagePoints}")

        Thread{
            if (percentagePoints>0){
                while (status <= percentagePoints){
                    handler.post{
                        progressBar.progress = status
                        progressBar.secondaryProgress = status
                        txtProgress.text = "${status}%"
                    }
                    try{
                        Thread.sleep(ImportantSettings.sleepInSummaryFragment)
                    }catch (e:InterruptedException){
                        println("Wyjątek: ${e.printStackTrace()}")
                    }
                    status++


                }

                status--
            }else{
                status = 0
            }

        }.start()






        gettedPointsText.text = "${gettedPoints[0]}/" +
                "${gettedPoints[1]}"

        return status
    }

    private fun countingPoints(userAnswers: ArrayList<UserAnswer>, quizAmount: Int): ArrayList<Int>{
        var points = 0
        var totalPoints = 0
        for (userAnswer in userAnswers){

            when(userAnswer.question!!.type){
                "SingleChoise" -> {
                    if (userAnswer.userAnswer == userAnswer.correctAnswer ){
                        points += userAnswer!!.question!!.points.toInt() * quizAmount

                    }
                    totalPoints += userAnswer.question!!.points.toInt()
                }
                "MultipleChoise" -> {
                    var wrongAnswers = 0
                    var localPoint = 0
                    for (uA in userAnswer.userAnswers!!){


                        if(userAnswer.correctAnswers!!.find { it == uA } == null){
                            wrongAnswers +=1

                            println("WRONG ANSWER: ${wrongAnswers}")


                        }else{
                            localPoint += userAnswer.question!!.points.toInt() * quizAmount
                            println("Local Points: ${localPoint}")
                        }
                    }
                    if (wrongAnswers > 0){

                        var helpVar = userAnswer.question!!.points.toInt() * quizAmount * wrongAnswers
                        println("HelpVar: ${helpVar}")
                        if (localPoint - helpVar > 0) {
                            localPoint -= helpVar
                        }else if (localPoint - helpVar <=0){
                            localPoint = 0
                        }
                    }
                    points += localPoint

                    for (question in userAnswer.question!!.correctAnswers!!){
                        totalPoints += userAnswer.question!!.points.toInt()

                    }



                }
                "TrueOrFalse" -> {
                    if (userAnswer.userAnswer == userAnswer.correctAnswer){
                        points += userAnswer!!.question!!.points.toInt() * quizAmount
                    }

                    totalPoints += userAnswer.question!!.points.toInt()
                }
            }



        }


        return arrayListOf(points,totalPoints)
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SummaryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SummaryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}