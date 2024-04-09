package com.example.animcare.main_activity_files.Fragments.QuizFragmentFiles.QuizActivityFiles

import android.animation.ObjectAnimator
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.CheckBox
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.animcare.Classes.Question
import com.example.animcare.Classes.Quiz
import com.example.animcare.Classes.UserAnswer
import com.example.animcare.ImportantSettings
import com.example.animcare.R
import com.example.animcare.main_activity_files.Fragments.QuizFragmentFiles.SummaryFragment
import com.example.animcare.main_activity_files.Fragments.QuizFragmentFiles.SummaryQuizFiles.SummaryBaseFragment
import kotlinx.android.synthetic.main.fragment_multiple_choise.*
import kotlinx.android.synthetic.main.fragment_single_choice.*
import java.io.Serializable

class ButtonsListenerForSingleChoise: View.OnClickListener, Serializable {
    var question: TextView? = null
    var buttons: ArrayList<Button>? = arrayListOf()
    var checkboxes: ArrayList<CheckBox>? = arrayListOf()
    var buttonsTF: ArrayList<Button>? = arrayListOf()
    var quiz: Quiz = Quiz()
    var id:Int = 0
    var userAnswers: ArrayList<UserAnswer> = arrayListOf()
    var supportFragmentManager: FragmentManager? = null
    val summaryFragment: SummaryFragment = SummaryFragment()
    val singleChoise: SingleChoiceFragment = SingleChoiceFragment()
    val multipleChose: MultipleChoiseFragment = MultipleChoiseFragment()
    val trueORFalse: TrueOrFalseFragment = TrueOrFalseFragment()
    var summaryBaseFragment: SummaryBaseFragment = SummaryBaseFragment()
    var progressBar: ProgressBar? = null
    var progressBarMultipleChoise: ProgressBar? = null
    var progressBarTrueOrFalse: ProgressBar? = null
    val time_max: Long = ImportantSettings.timePerQuestion

    val timerStartConstructor = object : CountDownTimer(time_max,1000){

        override fun onTick(millisUntilFinished: Long) {
            var currentTime = millisUntilFinished / 1000
            println("Seconds1: ${millisUntilFinished/1000}")
            if (quiz.questions[id].type == "SingleChoise"){

                progressBar?.progress = (millisUntilFinished/1000).toInt()
                setProgressBarColor(progressBar, currentTime)
            }else if (quiz.questions[id].type == "MultipleChoise"){
                progressBarMultipleChoise?.progress = (millisUntilFinished/1000).toInt()
                setProgressBarColor(progressBarMultipleChoise, currentTime)
            }else if(quiz.questions[id].type == "TrueOrFalse"){
                progressBarTrueOrFalse?.progress = (millisUntilFinished/1000).toInt()
                setProgressBarColor(progressBarTrueOrFalse, currentTime)
            }





        }

        override fun onFinish() {
            if (id< quiz.questions.size){
                buttons?.get(0)?.text = "błędna odpowiedź"
                this@ButtonsListenerForSingleChoise.onClick(buttons?.get(0))
            }else{


            }



        }


    }



    constructor()
    constructor(question: TextView, buttons: ArrayList<Button>, quiz: Quiz,
                id: Int, supportFragmentManager: FragmentManager,
                progressBar: ProgressBar
                ){
        this.question = question
        this.buttons = buttons
        this.quiz = quiz
        this.id = id
        this.supportFragmentManager = supportFragmentManager
        this.progressBar = progressBar


        printQuestion(quiz.questions[id], question!!, buttons)
        //Animacja Progress Bar oraz CountDownTimer przy inicjalizacji obiektu
        if (this.quiz.questions[id].type == "SingleChoise"){

            var animation = ObjectAnimator.ofInt(progressBar, "progress", 100, 0)
            animation.duration = time_max
            animation.interpolator = DecelerateInterpolator(1f)
            animation.start()



        }

        timerStartConstructor.start()

    }


    override fun onClick(v: View?) {




        timerStartConstructor.cancel()

        if (id < quiz.questions.size){
            timerStartConstructor.start()
        }


        if (id < quiz.questions.size){


            when(quiz.questions[id].type){
                "SingleChoise" -> {
                    var userAnswer = UserAnswer()
                    userAnswer.question = this.quiz.questions[id]
                    userAnswer.correctAnswer = this.quiz.questions[id].correctAnswer.toString()
                    userAnswer.userAnswer = v!!.findViewById<Button>(v!!.id).text.toString()
                    this.userAnswers.add(userAnswer)

                }
                "MultipleChoise" -> {

                    val userAnswersCB = getUserAnswersCB()
                    this.userAnswers.add(userAnswersCB)

                    //println("USERANSWERSCB: ${userAnswersCB.userAnswers}")


                }
                "TrueOrFalse" -> {
                    val userAnswerTF = getUserAnswerTrueOrFalse(v)
                    this.userAnswers.add(userAnswerTF)

                    //DOPISAĆ FRAGMENT KODU DLA TRUEORFALSE FRAGMENT


                }

            }

        }

        id++


        if (id < quiz.questions.size){

            if (this.quiz.questions[id].type == "SingleChoise"){
                var animation = ObjectAnimator.ofInt(progressBar, "progress", 100,0)
                animation.duration = time_max
                animation.interpolator = DecelerateInterpolator(1f)
                animation.start()

                println("Animacja single start()")


            }
            if (this.quiz.questions[id].type == "MultipleChoise"){
                var animation = ObjectAnimator.ofInt(progressBarMultipleChoise, "progress", 100,0)
                animation.duration = time_max
                animation.interpolator = DecelerateInterpolator(1f)
                animation.start()
                println("Animacja multiple start()")


            }
            if (this.quiz.questions[id].type == "TrueOrFalse"){
                var animation = ObjectAnimator.ofInt(progressBarTrueOrFalse, "progress", 100,0)
                animation.duration = time_max
                animation.interpolator = DecelerateInterpolator(1f)
                animation.start()
                println("Animacja trueOrFalse start()")




            }


            if (this.buttons!!.isNotEmpty()){
//                printQuestion(this.quiz.questions[id], this.question!!, this.buttons!!)
            }else{
                println("ButtonsListenerForSingleChoise: this.buttons is empty arrayList")
            }

            println("=======================================================")
            if (quiz.questions[id].type == "MultipleChoise"){

                if (this.quiz.questions[id-1].type != "MultipleChoise" && id > 0){

                }else{
                    unCheckingCheckBoxes(this.checkboxes!!)
                    printQuestionCheckBoxes(this.quiz.questions[id], this.question!!, this.checkboxes!!)
                }


                val bundle = Bundle()
                bundle.putSerializable("listener", this)
                bundle.putInt("id", this.id)
                multipleChose.arguments = bundle
                goToFragment(multipleChose)
                println("Typ MultipleChoise")



            }else if (quiz.questions[id].type == "SingleChoise"){

                if (this.quiz.questions[id-1].type != "SingleChoise" && id > 0){

                }else{
                    printQuestion(this.quiz.questions[id], this.question!!, this.buttons!!)

                }




                val bundle = Bundle()
                bundle.putSerializable("listener", this)
                bundle.putInt("id", this.id)
                singleChoise.arguments = bundle
                goToFragment(singleChoise)




                println("Do następnego fragmentu")
            }else if (quiz.questions[id].type == "TrueOrFalse"){
                if (this.quiz.questions[id-1].type != "TrueOrFalse" && id > 0){

                }else{
                    //PRINT TRUE OR FALSE QUESTION
                    printQuestionTrueOrFalse(this.quiz.questions[id], this.question!!)
                }


                val bundle = Bundle()
                bundle.putSerializable("listener", this)
                bundle.putInt("id", this.id)
                trueORFalse.arguments = bundle
                goToFragment(trueORFalse)

            }


        }else if (id == quiz.questions.size){

            println("Koniec pytań")
//            for (uA in userAnswers){
//                println("UserAnswers: ${uA.question} | ${uA.correctAnswer} | ${uA.userAnswer}")
//                println("USERANSWERSMULTIPLECHOISE: ${uA.correctAnswers}")
//
//            }
            val bundle = Bundle()
            bundle.putSerializable("quiz", this.quiz)
            bundle.putSerializable("userAnswers", this.userAnswers)
            bundle.putSerializable("listener", this)
            summaryBaseFragment.arguments = bundle
            goToFragment(summaryBaseFragment)
            //goToFragment(summaryFragment)

        }
        if (id == quiz.questions.size){
            timerStartConstructor.cancel()
        }
    }

    private fun setProgressBarColor(progressBar: ProgressBar?, currentTime: Long){
        if (currentTime >= 0.6 * time_max / 1000 && currentTime <= 1 * time_max / 1000){
            progressBar?.progressTintList = ColorStateList.valueOf(Color.parseColor("#CD038350"))
            println("ZIELONY")
        } else if(currentTime >= 0.4 * time_max / 1000 && currentTime <= 0.59 * time_max / 1000){
            progressBar?.progressTintList = ColorStateList.valueOf(Color.parseColor("#CDFF920C"))
            println("ŻÓŁTY")
        } else {
            println("CZERWONY")
            progressBar?.progressTintList = ColorStateList.valueOf(Color.parseColor("#CDAF1010"))

        }
    }



    private fun printQuestion(questionA: Question,question: TextView ,buttons: ArrayList<Button>){
        question.text = questionA.question
        buttons[0].text = questionA.answer1
        buttons[1].text = questionA.answer2
        buttons[2].text = questionA.answer3
        buttons[3].text = questionA.answer4
    }
    private fun printQuestionCheckBoxes(questionA: Question,question: TextView ,buttons: ArrayList<CheckBox>){
        question.text = questionA.question
        buttons[0].text = questionA.answer1
        buttons[1].text = questionA.answer2
        buttons[2].text = questionA.answer3
        buttons[3].text = questionA.answer4
    }
    private fun printQuestionTrueOrFalse(questionA: Question, question: TextView){
        question.text = questionA.question
    }

    private fun goToFragment(fragment: Fragment){
        var backName = fragment.javaClass.name
        val manager = supportFragmentManager
       // var fragmentsInBackStack = manager!!.popBackStackImmediate(backName,0)


            val fragmentTransaction = manager?.beginTransaction()
                ?.replace(R.id.fragmentContainerViewQuizAcivity, fragment)
            fragmentTransaction?.commit()


    }

    private fun itemClicked(checkBox: CheckBox): ArrayList<String>{
        var arrayList: ArrayList<String> = arrayListOf()
        if (checkBox.isChecked){
            arrayList.add(checkBox.text.toString())
        }
        return arrayList
    }

    private fun getUserAnswersCB(): UserAnswer{
        var userAnswersCB = UserAnswer()
        userAnswersCB.question = this.quiz.questions[id]
        userAnswersCB.correctAnswers = this.quiz.questions[id].correctAnswers
        userAnswersCB.userAnswers = arrayListOf()

        if (checkboxes?.get(0)!!.isChecked){
            userAnswersCB.userAnswers!!.add(checkboxes?.get(0)?.text.toString())
            checkboxes?.get(0)!!.setBackgroundColor(Color.WHITE)
            println("Wybrano checkbox 1")

        }
        if (checkboxes?.get(1)!!.isChecked){
            userAnswersCB.userAnswers!!.add(checkboxes?.get(1)?.text.toString())
            checkboxes?.get(1)!!.setBackgroundColor(Color.WHITE)
            println("Wybrano checkbox 2")


        }
        if (checkboxes?.get(2)!!.isChecked){
            userAnswersCB.userAnswers!!.add(checkboxes?.get(2)?.text.toString())
            checkboxes?.get(2)!!.setBackgroundColor(Color.WHITE)
            println("Wybrano checkbox 3")

        }
        if (checkboxes?.get(3)!!.isChecked){
            userAnswersCB.userAnswers!!.add(checkboxes?.get(3)?.text.toString())
            checkboxes?.get(3)!!.setBackgroundColor(Color.WHITE)
            println("Wybrano checkbox 4")

        }
//        for (uA in 0 until userAnswersCB.correctAnswers!!.size){
//
//
//        }

        return userAnswersCB
    }

    private fun getUserAnswerTrueOrFalse(v: View?): UserAnswer{
        var userAnswerTF = UserAnswer()
        userAnswerTF.question = this.quiz.questions[id]
        userAnswerTF.correctAnswer = this.quiz.questions[id].correctAnswer.toString()
        userAnswerTF.userAnswer = v!!.findViewById<Button>(v!!.id).text.toString()

        return userAnswerTF
    }

    private fun unCheckingCheckBoxes(checkboxes: ArrayList<CheckBox>){

        checkboxes?.get(0)!!.isChecked = false
        checkboxes?.get(1)!!.isChecked = false
        checkboxes?.get(2)!!.isChecked = false
        checkboxes?.get(3)!!.isChecked = false

    }

}