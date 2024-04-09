package com.example.animcare.main_activity_files.Fragments.QuizFragmentFiles.SummaryQuizFiles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.animcare.Classes.Quiz
import com.example.animcare.Classes.UserAnswer
import com.example.animcare.R
import com.example.animcare.main_activity_files.Fragments.QuizFragmentFiles.QuizActivityFiles.ButtonsListenerForSingleChoise
import com.example.animcare.main_activity_files.Fragments.QuizFragmentFiles.SummaryFragment
import kotlinx.android.synthetic.main.fragment_summary_base.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SummaryBaseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SummaryBaseFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_summary_base, container, false)
    }

    override fun onStart() {
        super.onStart()



        bottomNav.isVisible = true
        val summaryFragment = SummaryFragment()
        val summaryDetailedFragment = SummaryDetailedFragment()
        var bundle = arguments
        var listener = bundle!!.getSerializable("listener") as ButtonsListenerForSingleChoise
        var quiz = bundle!!.getSerializable("quiz") as Quiz
        var userAnswers = bundle!!.getSerializable("userAnswers") as ArrayList<UserAnswer>


        bundle.putSerializable("quiz", quiz)
        bundle.putSerializable("userAnswers", userAnswers)
        bundle.putSerializable("listener", listener)
        summaryFragment.arguments = bundle
        loadFragmentForBottomNavigation(summaryFragment)

        bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.general -> {
                    bundle.putSerializable("quiz", quiz)
                    bundle.putSerializable("userAnswers", userAnswers)
                    bundle.putSerializable("listener", listener)
                    summaryFragment.arguments = bundle
                    loadFragmentForBottomNavigation(summaryFragment)
                }
                R.id.detailed -> {
                    bundle.putSerializable("quiz", quiz)
                    bundle.putSerializable("userAnswers", userAnswers)
                    bundle.putSerializable("listener", listener)
                    summaryDetailedFragment.arguments = bundle
                    loadFragmentForBottomNavigation(summaryDetailedFragment)
                }
                R.id.finish -> {
                   // println("QuizPoints: ${countingPoints(userAnswers, quiz.amountQuestion.toInt())}")

                    requireActivity().finish()

                }

            }
            true

        }



    }


    private fun loadFragmentForBottomNavigation(fragment: Fragment){
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerViewQuizAcivityQuizSummary, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SummaryBaseFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SummaryBaseFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}