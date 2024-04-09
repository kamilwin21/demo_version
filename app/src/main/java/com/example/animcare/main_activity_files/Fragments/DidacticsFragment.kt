package com.example.animcare.main_activity_files.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animcare.R
import com.example.animcare.main_activity_files.adapters.AnimalsTypesAdapter
import kotlinx.android.synthetic.main.fragment_didactics.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DidacticsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DidacticsFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_didactics, container, false)





    }

    override fun onStart() {
        super.onStart()
        println("DidacticsFragment onStart()")
    }

    override fun onResume() {
        super.onResume()
        println("DidacticsFragment onResume()")
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        recycler_view_type_of_animals.layoutManager = LinearLayoutManager(context?.applicationContext)
        recycler_view_type_of_animals.adapter = context?.let { AnimalsTypesAdapter(it.applicationContext, parentFragmentManager) }

//        recycler_view_type_of_animals.layoutManager = LinearLayoutManager(context?.applicationContext)
//        recycler_view_type_of_animals.adapter = context?.let { TestingAdapter(it) }

    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DidacticsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DidacticsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}