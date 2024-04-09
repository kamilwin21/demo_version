package com.example.animcare.main_activity_files.MyPets.Fragments

import android.annotation.SuppressLint
import android.app.ActionBar
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animcare.DatabaseFiles.DataBase
import com.example.animcare.ImportantSettings
import com.example.animcare.R
import com.example.animcare.main_activity_files.MyPets.Adapters.MyPetsAdapter
import com.example.animcare.main_activity_files.MyPets.PetInList
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_add_pet.*
import kotlinx.android.synthetic.main.fragment_pets.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PetsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@RequiresApi(Build.VERSION_CODES.O)
class PetsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val addPetFragment = AddPetFragment()

    //    val oldDisplayOptions = (requireActivity() as AppCompatActivity).supportActionBar!!.displayOptions
    val oldDisplayOptions = 12

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
        return inflater.inflate(R.layout.fragment_pets, container, false)
    }

    override fun onStart() {
        super.onStart()

        println("DisplayOptions: ${oldDisplayOptions}")
        requireActivity().bottomNavMyPet.selectedItemId = R.id.returnRemovePet
        removing_info_TW.setBackgroundColor(ImportantSettings.color)
        println("UserId: ${FirebaseAuth.getInstance().currentUser!!.uid}")

        val dbC = FirebaseDatabase.getInstance(DataBase.dbReferName).reference
        dbC.child("UsersPets").child(FirebaseAuth.getInstance().currentUser!!.uid)



        val dbConnection = FirebaseDatabase.getInstance(DataBase.dbReferName).reference
        dbConnection.child("UsersPets").child(FirebaseAuth.getInstance().currentUser!!.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                @SuppressLint("WrongConstant")
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        tw_pet_list_info.visibility = View.GONE
                        var userPets: ArrayList<PetInList> = arrayListOf()
                        for (pet in snapshot.children) {
                            userPets.add(pet.getValue(PetInList::class.java)!!)
                        }
                        recycler_view_pets_fregment.layoutManager =
                            LinearLayoutManager(requireContext().applicationContext)
                        recycler_view_pets_fregment.adapter =
                            MyPetsAdapter(requireContext().applicationContext, userPets, false)

                        bottomNavMyPet.setOnItemSelectedListener {
                            when (it.itemId) {
                                R.id.addPet -> {
                                    val bundle = Bundle()
                                    bundle.putSerializable("userPets", userPets)
                                    addPetFragment.arguments = bundle
                                    loadFragmentForBottomNavigation(addPetFragment)
                                    removeLastFragmentInStack(addPetFragment)
                                    clearEditTextLastFragmentInStack(addPetFragment, addPetFragment)
                                    hideKeyBoard()

                                    println("Przycisk dodaj")

                                }
                                R.id.removePet -> {

                                    (requireActivity() as AppCompatActivity).supportActionBar?.displayOptions = 0
                                    removeLastFragmentInStack(addPetFragment)
                                    clearEditTextLastFragmentInStack(addPetFragment, addPetFragment)
                                    hideKeyBoard()
                                    var id = 0
//                                recycler_view_pets_fregment.layoutManager = LinearLayoutManager(requireContext().applicationContext)
                                    if (it.isChecked) {
                                        println("Bez wciśnięcia")
                                        recycler_view_pets_fregment.adapter = MyPetsAdapter(
                                            requireContext().applicationContext,
                                            userPets,
                                            false
                                        )
                                    } else {
                                        recycler_view_pets_fregment.adapter = MyPetsAdapter(
                                            requireContext().applicationContext,
                                            userPets,
                                            true
                                        )
                                        requireActivity().bottomNavMyPet.menu.getItem(0).isVisible =
                                            false
                                        requireActivity().bottomNavMyPet.menu.getItem(1).isVisible =
                                            false


                                        requireActivity().bottomNavMyPet.menu.getItem(2).isVisible =
                                            true
                                        println("Wciśnięte")
                                        removing_info_TW.text =
                                            "${requireActivity().applicationContext.getString(R.string.removing_elements_info)}"
                                        removing_info_1_TW.isVisible = true


                                    }

                                }
                                R.id.returnRemovePet -> {
                                    recycler_view_pets_fregment.adapter = MyPetsAdapter(
                                        requireContext().applicationContext,
                                        userPets,
                                        false
                                    )
                                    it.isVisible = false
                                    requireActivity().bottomNavMyPet.menu.getItem(0).isVisible =
                                        true
                                    requireActivity().bottomNavMyPet.menu.getItem(1).isVisible =
                                        true
                                    requireActivity().actionBar?.displayOptions =
                                        ActionBar.DISPLAY_SHOW_TITLE
                                    removing_info_TW.text =
                                        "${requireActivity().applicationContext.getString(R.string.myPets)}"
                                    removing_info_1_TW.isVisible = false

//                                (requireActivity() as AppCompatActivity).supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_TITLE
//                                (requireActivity() as AppCompatActivity).supportActionBar?.displayOptions = ActionBar.DISPLAY_HOME_AS_UP
                                    (requireActivity() as AppCompatActivity).supportActionBar!!.displayOptions = oldDisplayOptions
                                }
                            }

                            true
                        }
                        if (bottomNavMyPet.selectedItemId == R.id.removePet) {
                            println("Po przeładowaniu kliknięte dalej Usuń")

                            (requireActivity() as AppCompatActivity).supportActionBar?.displayOptions = 0
                            removeLastFragmentInStack(addPetFragment)
                            clearEditTextLastFragmentInStack(addPetFragment, addPetFragment)
                            hideKeyBoard()
                            var id = 0
//                                recycler_view_pets_fregment.layoutManager = LinearLayoutManager(requireContext().applicationContext)
                            if (bottomNavMyPet.selectedItemId == R.id.removePet) {
                                println("Bez wciśnięcia")
                                recycler_view_pets_fregment.adapter = MyPetsAdapter(
                                    requireContext().applicationContext,
                                    userPets,
                                    true
                                )
                            } else {
                                recycler_view_pets_fregment.adapter = MyPetsAdapter(
                                    requireContext().applicationContext,
                                    userPets,
                                    true
                                )
                                requireActivity().bottomNavMyPet.menu.getItem(0).isVisible = false
                                requireActivity().bottomNavMyPet.menu.getItem(1).isVisible = false
                                requireActivity().bottomNavMyPet.menu.getItem(2).isVisible = true
                                println("Wciśnięte")
                                removing_info_TW.text =
                                    "${requireActivity().applicationContext.getString(R.string.removing_elements_info)}"
                                removing_info_1_TW.isVisible = true
                            }
                        }
                    } else {
                        bottomNavMyPet.setOnItemSelectedListener(setOnItemSelectedListener)
                        println("Brak zwierząt");
                        tw_pet_list_info.visibility = View.VISIBLE


                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    println("Error: ${error.code} || ${error.message} || ${error.details}")

                }
            })
    }

    private val setOnItemSelectedListener: NavigationBarView.OnItemSelectedListener =
        NavigationBarView.OnItemSelectedListener {

            when (it.itemId) {
                R.id.addPet -> {
                    val bundle = Bundle()
                    bundle.putSerializable("userPets", arrayListOf<PetInList>())
                    addPetFragment.arguments = bundle
                    loadFragmentForBottomNavigation(addPetFragment)
                    removeLastFragmentInStack(addPetFragment)
                    clearEditTextLastFragmentInStack(addPetFragment, addPetFragment)
                    hideKeyBoard()

                    println("Przycisk dodaj")

                }
                R.id.returnRemovePet -> {
                    recycler_view_pets_fregment.adapter = MyPetsAdapter(
                        requireContext().applicationContext,
                        arrayListOf<PetInList>(),
                        false
                    )
                    it.isVisible = false
                    requireActivity().bottomNavMyPet.menu.getItem(0).isVisible = true
                    requireActivity().bottomNavMyPet.menu.getItem(1).isVisible = true
                    requireActivity().actionBar?.displayOptions = ActionBar.DISPLAY_SHOW_TITLE
                    removing_info_TW.text =
                        "${requireActivity().applicationContext.getString(R.string.myPets)}"
                    removing_info_1_TW.isVisible = false

//                                (requireActivity() as AppCompatActivity).supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_TITLE
//                                (requireActivity() as AppCompatActivity).supportActionBar?.displayOptions = ActionBar.DISPLAY_HOME_AS_UP
                    (requireActivity() as AppCompatActivity).supportActionBar!!.displayOptions =
                        oldDisplayOptions


                }


            }


            true

        }

    override fun onResume() {
        super.onResume()
//        recycler_view_pets_fregment.layoutManager = LinearLayoutManager(requireContext().applicationContext)
//        recycler_view_pets_fregment.adapter = MyPetsAdapter(requireContext().applicationContext, PetInList, false)


    }

    private fun hideKeyBoard() {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }


    private fun removeLastFragmentInStack(fragment: Fragment) {
        if (parentFragmentManager.fragments.isNotEmpty()) {
            val fragmentName =
                parentFragmentManager.fragments[parentFragmentManager.fragments.size - 1].javaClass.name
            if (fragmentName == fragment.javaClass.name) {
                parentFragmentManager.apply {
                    beginTransaction().remove(fragment).commit()

                }

            }

        }

    }

    private fun clearEditTextLastFragmentInStack(fragment: Fragment, directFragment: Fragment) {
        if (parentFragmentManager.fragments.isNotEmpty()) {
            val fragmentName =
                parentFragmentManager.fragments[parentFragmentManager.fragments.size - 1].javaClass.name
            if (fragmentName == fragment.javaClass.name && fragmentName == directFragment.javaClass.name) {
                fragment.requireActivity().animalNameAddPet.text = null
                //fragment.requireActivity().animalTypeAddPet.text = null

            }

        }

    }

    private fun loadFragmentForBottomNavigation(fragment: Fragment) {
//        var backName = fragment.javaClass.name
//        val transaction = parentFragmentManager.beginTransaction()
//        transaction.replace(R.id.fragmentContainerViewPetsFragment, fragment)
//        transaction.addToBackStack(backName)
//        transaction.commit()


        var backName = fragment.javaClass.name
        val manager = parentFragmentManager
        var fragmentsInBackStack = manager.popBackStackImmediate(backName, 0)

        if (!fragmentsInBackStack) {
            val fragmentTransaction = manager.beginTransaction()
                .replace(R.id.fragmentContainerViewPetsFragment, fragment)
                .addToBackStack(backName)
            fragmentTransaction.commit()
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PetsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PetsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}