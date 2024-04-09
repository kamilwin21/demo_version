package com.example.animcare.main_activity_files.MyPets.Fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.size
import androidx.fragment.app.Fragment
import com.example.animcare.DatabaseFiles.DataBase
import com.example.animcare.R
import com.example.animcare.main_activity_files.MyPets.PetInList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_pet.*
import kotlinx.android.synthetic.main.fragment_pets.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddPetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddPetFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_add_pet, container, false)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        var grade: String = radioButton.text.toString()

       radioButton.setOnClickListener {
                grade = "${radioButton.text}"

            }
        radioButton2.setOnClickListener {
            grade = "${radioButton2.text}"

            }
        radioButton3.setOnClickListener {
            grade = "${radioButton3.text}"

            }




        buttonAddPet.setOnClickListener{
           radioButton.isActivated = true



            hideKeyBoard()
            if (animalNameAddPet.text.isNullOrEmpty()){
                Toast.makeText(requireActivity().applicationContext, "${requireActivity().getString(R.string.put_values)}",
                    Toast.LENGTH_SHORT).show()


            }else if (animalNameAddPet.text.isNotEmpty()){
                requireActivity().supportFragmentManager.apply {
                    beginTransaction().remove(fragments[fragments.size-1]).commit()
                    popBackStack()


                }
                var bundle = arguments

                var userPets: ArrayList<PetInList> = arrayListOf()

                if (bundle!!.getSerializable("userPets") as ArrayList<PetInList> != null){
                    userPets = bundle!!.getSerializable("userPets") as ArrayList<PetInList>
                }else{
                    println("PetsFragment -> Empty userPetsList")

                }


                val addPet = PetInList(animalNameAddPet.text.toString(), grade, String())

                userPets.add(addPet)
                val dbConnection = FirebaseDatabase.getInstance(DataBase.dbReferName).reference
                dbConnection.child("UsersPets").child(FirebaseAuth.getInstance().currentUser!!.uid)
                    .setValue(userPets)

1
                animalNameAddPet.text = null
                //animalTypeAddPet.text = null




            }





            if (requireActivity().recycler_view_pets_fregment.size == 0){

                println("popBackStack()")
                requireActivity().supportFragmentManager.popBackStack()
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(requireActivity().fragmentContainerView.id, PetsFragment())
                    .commit()
            }
        }


    }


    private fun hideKeyBoard(){
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddPetFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddPetFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}