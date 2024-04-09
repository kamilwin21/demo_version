package com.example.animcare.main_activity_files.Fragments.QuizFragmentFiles.SummaryQuizFiles

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
import kotlinx.android.synthetic.main.fragment_summary_detailed.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SummaryDetailedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SummaryDetailedFragment : Fragment() {
    internal  var status1 = 0
    internal  var status2 = 0
    internal  var status3 = 0
    private val handler1 = Handler()
    private val handler2 = Handler()
    private val handler3 = Handler()
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
        return inflater.inflate(R.layout.fragment_summary_detailed, container, false)
    }

    override fun onStart() {
        super.onStart()


        requireActivity().layoutQuizActivity.gravity = Gravity.NO_GRAVITY
        val bundle = arguments
        val quizBundle = bundle!!.getSerializable("quiz")
        val quiz: Quiz = quizBundle as Quiz
        val userAnswers = bundle!!.getSerializable("userAnswers") as ArrayList<UserAnswer>
        val listener = bundle!!.getSerializable("listener") as ButtonsListenerForSingleChoise

        val gettedPoints = countingPoints(userAnswers, quiz.amountQuestion.toInt())

        val progressBarTimer1= setProgressBar(gettedPoints[0])
        val progressBarTimer2= setProgressBarDetailedMultipleChoice(gettedPoints[1])
        val progressBarTimer3 = setProgressBarDetailedTrueOrFalseChoice(gettedPoints[2])
        txtProgress1.text = "${progressBarTimer1}%"
        txtProgress2.text = "${progressBarTimer2}%"
        txtProgress3.text = "${progressBarTimer3}%"



    }

    private fun countingPoints(userAnswers: ArrayList<UserAnswer>, quizAmount: Int):ArrayList<Pair<Int, Int>>{
        var totalPointsToGetSCH = 0
        var totalPointsToGetMCH = 0
        var totalPointsToGetTOF = 0
        var pointsGettedSCH = 0
        var pointsGettedMCH = 0
        var pointsGettedTOF = 0

        for(userAnswer in userAnswers){
            when(userAnswer.question!!.type){
                "SingleChoise" -> {
                    if (userAnswer.userAnswer == userAnswer.correctAnswer){
                        pointsGettedSCH += userAnswer.question!!.points.toInt() * quizAmount
                    }
                    totalPointsToGetSCH += userAnswer.question!!.points.toInt() * quizAmount

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
                    pointsGettedMCH += localPoint

                    for (question in userAnswer.question!!.correctAnswers!!){
                        totalPointsToGetMCH += userAnswer.question!!.points.toInt()

                    }

                }
                "TrueOrFalse" -> {
                    if (userAnswer.userAnswer == userAnswer.correctAnswer){
                        pointsGettedTOF += userAnswer.question!!.points.toInt() * quizAmount
                    }
                    totalPointsToGetTOF += userAnswer.question!!.points.toInt() * quizAmount

                }

            }

        }


        return arrayListOf(
            Pair(pointsGettedSCH, totalPointsToGetSCH),
                        Pair(pointsGettedMCH, totalPointsToGetMCH),
                    Pair(pointsGettedTOF, totalPointsToGetTOF)
                    )
    }

    private fun setProgressBarDetailedTrueOrFalseChoice(gettedPoints: Pair<Int, Int>): Int{

        val progressBar = progressBar3
        var getted: Int = gettedPoints.first
        var total: Int = gettedPoints.second
        val percentagePoints: Int = ((getted.toDouble()/total.toDouble()) * 100.0).toInt()
        println("PercentagePoints: ${percentagePoints}")
        println("%  ${(getted.toDouble()/total.toDouble()) * 100.0}")
        Thread{
            if (percentagePoints > 0){
                while (status3 <= percentagePoints){
                    handler3.post{
                        progressBar.progress = status3
                        progressBar.secondaryProgress = status3
                        txtProgress3.text = "${status3}%"
                    }
                    try{
                        Thread.sleep(ImportantSettings.sleepInSummaryDetailedFragment)
                    }catch (e:InterruptedException){
                        println("Wyjątek: ${e.printStackTrace()}")
                    }
                    status3++

                }

                status3--
            }else {
                status3 = 0
            }





        }.start()






        gettedPointsText3.text = "${gettedPoints.first}/" +
                "${gettedPoints.second}"

        return status3
    }



    private fun setProgressBarDetailedMultipleChoice(gettedPoints: Pair<Int, Int>): Int{

        val progressBar = progressBar2
        var getted: Int = gettedPoints.first
        var total: Int = gettedPoints.second
        val percentagePoints: Int = ((getted.toDouble()/total.toDouble()) * 100.0).toInt()
        println("PercentagePoints: ${percentagePoints}")

        Thread{
            if (percentagePoints > 0){
                while (status2 <= percentagePoints){
                    handler2.post{
                        progressBar.progress = status2
                        progressBar.secondaryProgress = status2
                        txtProgress2.text = "${status2}%"
                    }
                    try{
                        Thread.sleep(ImportantSettings.sleepInSummaryDetailedFragment)
                    }catch (e:InterruptedException){
                        println("Wyjątek: ${e.printStackTrace()}")
                    }
                    status2++


                }
                status2--


            }else{
                status2 =0
            }

        }.start()






        gettedPointsText2.text = "${gettedPoints.first}/" +
                "${gettedPoints.second}"

        return status2
    }





    private fun setProgressBar(gettedPoints: Pair<Int, Int>): Int{

        val progressBar = progressBar1
        var getted: Int = gettedPoints.first
        var total: Int = gettedPoints.second
        val percentagePoints: Int = ((getted.toDouble()/total.toDouble()) * 100.0).toInt()
        println("PercentagePoints: ${percentagePoints}")

        Thread{
            if (percentagePoints > 0){
                while (status1 <= percentagePoints){
                    handler1.post{
                        progressBar.progress = status1
                        progressBar.secondaryProgress = status1
                        txtProgress1.text = "${status1}%"
                    }
                    try{
                        Thread.sleep(ImportantSettings.sleepInSummaryDetailedFragment)
                    }catch (e:InterruptedException){
                        e.printStackTrace()
                    }
                    status1++


                }
                status1--
            }else{
                status1 = 0
            }

        }.start()






        gettedPointsText1.text = "${gettedPoints.first}/" +
                "${gettedPoints.second}"

        return status1
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SummaryDetailedFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SummaryDetailedFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}