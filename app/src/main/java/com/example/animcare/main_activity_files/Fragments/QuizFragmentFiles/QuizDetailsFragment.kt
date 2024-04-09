package com.example.animcare.main_activity_files.Fragments.QuizFragmentFiles

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.animcare.Classes.Question
import com.example.animcare.Classes.Quiz
import com.example.animcare.DatabaseFiles.DataBase
import com.example.animcare.ImportantSettings
import com.example.animcare.R
import com.example.animcare.main_activity_files.Fragments.QuizFragmentFiles.QuizActivityFiles.MultipleChoiseFragment
import com.example.animcare.main_activity_files.Fragments.QuizFragmentFiles.QuizActivityFiles.SingleChoiceFragment
import com.example.animcare.main_activity_files.Fragments.QuizFragmentFiles.QuizActivityFiles.TrueOrFalseFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_quiz.*
import kotlinx.android.synthetic.main.fragment_quiz_details.*
import kotlin.random.Random

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [QuizDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuizDetailsFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_quiz_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)




    }


    override fun onStart() {
        super.onStart()

        requireActivity().layoutQuizActivity.gravity = Gravity.NO_GRAVITY
        val singleChoiceFragment = SingleChoiceFragment()
        val multipleChoseFragment = MultipleChoiseFragment()
        val trueOrFalseFragment = TrueOrFalseFragment()


        val detailesOfQuiz = this.arguments?.getString("DetailesOfQuiz")


        if (detailesOfQuiz.isNullOrEmpty()){
            println("Brakuje typu quizu. Przejście przez: QuizActivity -> QuizDetailsFragment. Wartość zmiennej: $detailesOfQuiz")
        }

        //println("DETAILESOFQUIZ: ${detailesOfQuiz}")
        val dbConnection = FirebaseDatabase.getInstance(DataBase.dbReferName).reference
        dbConnection.child("Quizzes").child(detailesOfQuiz!!)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        var quiz: Quiz = Quiz()
                        quiz = snapshot.getValue(Quiz::class.java)!!
                        quiz_name_text_view.text = quiz.quizName
                        description_text_view.text = quiz.description


                        start_quiz.setOnClickListener {

                            if (quiz.questions.size < 2){
                                println("Zbyt mało pytań w bazie danych: ${3}")
                            }else{



                                val id = 0
                                val newQuestionForQuiz = drawQuestions(quiz.questions,ImportantSettings.numberQuestionOnQuiz)
                                quiz.questions = arrayListOf()
                                quiz.questions = newQuestionForQuiz

                                var args = Bundle()
                                args.putSerializable("quiz", quiz)
                                args.putInt("id", id)

                                when(quiz.questions[0].type){
                                    "SingleChoise" -> {

                                        singleChoiceFragment.arguments = args
                                        goToFragment(singleChoiceFragment)
                                    }
                                    "MultipleChoise" -> {
                                        multipleChoseFragment.arguments = args
                                        goToFragment(multipleChoseFragment)
                                    }
                                    "TrueOrFalse" -> {
                                        trueOrFalseFragment.arguments = args
                                        goToFragment(trueOrFalseFragment)
                                    }


                                }






//                                val intent = Intent(activity!!.applicationContext, QuizActivity::class.java)
//                                val newQuestionForQuiz = drawQuestions(quiz.questions,3)
//                                quiz.questions = arrayListOf()
//                                quiz.questions = newQuestionForQuiz
//                                intent.putExtra("quiz", quiz)
//                                startActivity(intent)
                                println("SIze: ${parentFragmentManager.fragments.size
                                }")
                                for (i in quiz.questions){
                                    println("===============================================")

                                    println("question: ${i.question}")
                                    println("answer1: ${i.answer1}")
                                    println("answer2: ${i.answer2}")
                                    println("answer3: ${i.answer3}")
                                    println("answer4: ${i.answer4}")
                                    println("type: ${i.type}")
                                    println("===============================================")
                                }
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }


            })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment QuizDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            QuizDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }



    private fun drawQuestions(questionList: ArrayList<Question>, numberOfQuestion: Int ): ArrayList<Question>{
        var randQuestionList: ArrayList<Question> = arrayListOf()
        var hashSet: HashSet<Question> = HashSet()
        var helpList: ArrayList<Question> = arrayListOf()

        for (i in 0 until questionList.size)
        {
            var randId: Int = Random.nextInt(0, questionList.size)
            randQuestionList.add(questionList[randId])
        }
        hashSet = randQuestionList.toHashSet()

        if(hashSet.size < questionList.size)
        {
            while (hashSet.size < questionList.size)
            {
                var randomId: Int = Random.nextInt(0, questionList.size)
                hashSet.add(questionList[randomId])
            }
        }
        for (hs in hashSet)
        {
            helpList.add(hs)
        }
        randQuestionList = arrayListOf()
        for(i in 0 until numberOfQuestion)
        {
            randQuestionList.add(helpList[i])
        }
        var checkSingleChoise = false
        if (randQuestionList[0].type != "SingleChoise"){
            for (i in 1 until randQuestionList.size){
                if (randQuestionList[i].type == "SingleChoise"){
                    var helpV = randQuestionList[0]
                    randQuestionList[0] = randQuestionList[i]
                    randQuestionList[i] = helpV
                    checkSingleChoise = true
                }

            }
            if (checkSingleChoise){
            }else{
                var finder= questionList.find { it.type == "SingleChoise" }
                randQuestionList[0] = finder!!
            }
        }
        return randQuestionList
    }
    private fun lineUpFragments(questions: ArrayList<Question>): ArrayList<String>{
        var q = questions
        var arrayList: ArrayList<String> = arrayListOf()
        for(i in 0 until questions.size){
            when(questions[i].type){
                "SingleChoice" -> {
                    arrayList.add("")
                }
                "MultipleChoice" -> {
                }
            }
        }
        return arrayListOf()
    }
    private fun goToFragment(fragment: Fragment){
        var backName = fragment.javaClass.name
        val manager = parentFragmentManager
        var fragmentsInBackStack = manager.popBackStackImmediate(backName,0)
        val fragmentTransaction = manager.beginTransaction()
            .replace(R.id.fragmentContainerViewQuizAcivity, fragment)
        fragmentTransaction.commit()
    }
}


