package com.example.animcare.main_activity_files.MyPets.PetDetailsActivityFiles.DiagnosisFragmentFiles

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animcare.DatabaseFiles.DataBase
import com.example.animcare.R
import com.example.animcare.main_activity_files.MyPets.Class.Diagnosis
import com.example.animcare.main_activity_files.MyPets.PetDetailsActivityFiles.DiagnosisFragmentFiles.Adapters.DiagnosisAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_diagnosis.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DiagnosisFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DiagnosisFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_diagnosis, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {

        var petName = requireArguments().getString("PetName")!!

        //bottomNavDiagnosisFragment.selectedItemId = R.id.add
        bottomNavDiagnosisFragment.setOnItemSelectedListener {


            when(it.itemId){
                R.id.add -> {
                    println("Klik ADD")
                    val dbConnection = FirebaseDatabase.getInstance(DataBase.dbReferName).reference
                    dbConnection.child("Diagnosis").child(FirebaseAuth.getInstance().currentUser!!.uid)
                        .child(petName)
                        .addListenerForSingleValueEvent(object : ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                var diagnosisCount = 0
                                if (snapshot.exists()){
                                    println("Jest odniesienie do bazy")
                                    for (diagnosis in snapshot.children){
                                        diagnosisCount++


                                    }
                                    var intent = Intent(requireContext(), AddNewDiagnosisActivity::class.java)
                                    intent.putExtra("diagnosisCount", diagnosisCount)
                                    intent.putExtra("PetName", petName)
                                    startActivity(intent)



                                }else{
                                    println("Nie m odniesienia do bazy")
                                    var intent = Intent(requireContext(), AddNewDiagnosisActivity::class.java)
                                    intent.putExtra("PetName", petName)
                                    startActivity(intent)

                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                            }


                        })


//                    if (diagnosisCount == 0){
//                        var intent = Intent(requireContext(), AddNewDiagnosisActivity::class.java)
//                        startActivity(intent)
//                    }else if(diagnosisCount > 0){
//
//                        println("DiagnosisCount: ${diagnosisCount}")
//                    }

                }


            }

            true
        }





        var diagnosisList: ArrayList<Diagnosis> = arrayListOf()

        val gettingDiagnosisUserListFromDB = FirebaseDatabase.getInstance(DataBase.dbReferName).reference
        gettingDiagnosisUserListFromDB.child("Diagnosis").child(FirebaseAuth.getInstance().currentUser!!.uid)
            .child(petName)
            .addListenerForSingleValueEvent(object : ValueEventListener{
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
//                    var diagnosis: Diagnosis = snapshot.getValue(Diagnosis::class.java)!!
//                    println("Diagnosis: ${diagnosis.getName()}/ ${diagnosis.getType()} / ${diagnosis.getZonedDateTime()}")
                    for (diag in snapshot.children){
                        var diagnosis = diag.getValue(Diagnosis::class.java)!!
                        diagnosisList.add(diagnosis)
                        println("Diagnosis: ${diagnosis.getDiagnosisName()}/ ${diagnosis.getType()}/ ${diagnosis.getDescription()}")

                    }

                    recycler_view_diagnosis_in_diagnosis_fragment.layoutManager = LinearLayoutManager(requireContext())
                    recycler_view_diagnosis_in_diagnosis_fragment.adapter = DiagnosisAdapter(requireActivity(),
                        diagnosisList, petName)

                    if (diagnosisList.size == 0){
                        createMyView(requireContext(), diagnosis_layout_id, "TextView", "Brak diagnoz")
                    }


                }else{
                    println("Lista jest czysta")
                    recycler_view_diagnosis_in_diagnosis_fragment.layoutManager = LinearLayoutManager(requireContext())
                    recycler_view_diagnosis_in_diagnosis_fragment.adapter = DiagnosisAdapter(requireActivity(),
                        diagnosisList, petName)


//                    createMyView(requireContext(), diagnosis_layout_id, "TextView", "Brak diagnoz")
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })








        super.onStart()
    }




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DiagnosisFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DiagnosisFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    private fun createMyView(context: Context, layout: RelativeLayout, typeLayout: String, text: String?){
        when(typeLayout) {
            "TextView" -> {
                var textView = TextView(context)
                textView.text = text
                textView.setTextSize(20f)
                layout.addView(textView)

            }
        }
    }
}