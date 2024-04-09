package com.example.animcare.main_activity_files.MyPets.PetDetailsActivityFiles.DiagnosisFragmentFiles

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.animcare.DatabaseFiles.DataBase
import com.example.animcare.R
import com.example.animcare.main_activity_files.MyPets.Class.CustomDateAndTime
import com.example.animcare.main_activity_files.MyPets.Class.Diagnosis
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_delete_diagnosis.*

class DeleteDiagnosisActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_diagnosis)
        supportActionBar!!.title = "${resources.getString(R.string.preview)}"


        var diagnosisChosen = Diagnosis()
        val petName = intent.getStringExtra("petName")!!
        if (intent.hasExtra("diagnosisChosen")){
            diagnosisChosen = intent.getSerializableExtra("diagnosisChosen") as Diagnosis
        }else{
            println("INTENT: DeleteDiagnosisActivity -> diagnosisChosen is NULL")
        }
        var diagnosisList: ArrayList<Diagnosis> = arrayListOf()
        var position = 0
        if (intent.hasExtra("diagnosisList")){
            diagnosisList = intent.getSerializableExtra("diagnosisList") as ArrayList<Diagnosis>
            position = intent.getIntExtra("index", 0)
        }
        name_deleting_diagnosis.setText(diagnosisChosen.getDiagnosisName())
        type_deleting_diagnosis.setText(diagnosisChosen.getType())
        date_deleting_diagnosis_edit_text.setText(convertCustomDateAndTimeToString(diagnosisChosen.getDiagnosisDate()))
        description_deleting_diagnosis.text = diagnosisChosen.getDescription()

        btn_delete_diagnosis.setOnClickListener{
            diagnosisList.removeAt(position)

            val dbConnectionDiagnosis = FirebaseDatabase.getInstance(DataBase.dbReferName).reference

            dbConnectionDiagnosis.child("Diagnosis").child(FirebaseAuth.getInstance().currentUser!!.uid)
                .child(petName)
                .removeValue()
            if (diagnosisList.size > 0){
                dbConnectionDiagnosis.child("Diagnosis")
                    .child(FirebaseAuth.getInstance().currentUser!!.uid)
                    .child(petName)
                    .setValue(diagnosisList)

            }else{
                dbConnectionDiagnosis.child("Diagnosis")
                    .child(FirebaseAuth.getInstance().currentUser!!.uid)
                    .child(petName)
                    .setValue(arrayListOf<Diagnosis>())
            }

            finish()
        }


    }




    private fun convertCustomDateAndTimeToString(dateAndTime: CustomDateAndTime): String{
        return "${dateAndTime.day}/${dateAndTime.monthValue}/${dateAndTime.year}"
    }
}