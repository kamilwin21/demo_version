package com.example.animcare.main_activity_files.MyPets.PetDetailsActivityFiles.DiagnosisFragmentFiles.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.animcare.R
import com.example.animcare.main_activity_files.MyPets.Class.CustomDateAndTime
import com.example.animcare.main_activity_files.MyPets.Class.Diagnosis
import com.example.animcare.main_activity_files.MyPets.PetDetailsActivityFiles.DiagnosisFragmentFiles.DeleteDiagnosisActivity
import kotlinx.android.synthetic.main.diagnosis_position_layout_in_diagnosis_adapter.view.*

class DiagnosisAdapter(val context: Context, val diagnosisList: ArrayList<Diagnosis>, val petName: String): RecyclerView.Adapter<MyDiagnosisAdapter>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyDiagnosisAdapter {
        val inflater = LayoutInflater.from(parent.context)
        val positionList = inflater.inflate(R.layout.diagnosis_position_layout_in_diagnosis_adapter, parent, false)
        return MyDiagnosisAdapter(positionList)

    }

    override fun onBindViewHolder(holder: MyDiagnosisAdapter, position: Int) {
        val layout = holder.view.diagnosis_position_layout_in_diagnosis_adapter_id_layout
        val diagnosisName = holder.view.diagnosis_name_adapter
        val diagnosisDate = holder.view.diagnosis_date_adapter
        val diagnosisType = holder.view.diagnosis_type_adapter
        val diagnosisDescription = holder.view.diagnosis_description_adapter

        diagnosisName.text = diagnosisList[position].getDiagnosisName()
        diagnosisDate.text = convertCustomDateTimeToText(diagnosisList[position].getDiagnosisDate(),".")
        diagnosisType.text = diagnosisList[position].getType()
        diagnosisDescription.text = diagnosisList[position].getDescription()

        layout.setOnClickListener{
            Toast.makeText(holder.view.context.applicationContext, "Kliknięto: ${diagnosisList[position].getDiagnosisName()}",
                Toast.LENGTH_SHORT).show()
            var intentDiagnosisDetails = Intent(holder.view.context.applicationContext, DeleteDiagnosisActivity::class.java)
            intentDiagnosisDetails.putExtra("diagnosisChosen", diagnosisList[position])
            intentDiagnosisDetails.putExtra("diagnosisList", diagnosisList)
            intentDiagnosisDetails.putExtra("index", position)
            intentDiagnosisDetails.putExtra("petName", petName)
            holder.view.context.startActivity(intentDiagnosisDetails)


        }

        if (diagnosisList.size == 0){
            println("Adapter: size is 0")
        }



    }

    override fun getItemCount(): Int {
        return diagnosisList.size
    }


}

private fun convertCustomDateTimeToText(customDateAndTime: CustomDateAndTime, separator: String): String{
    return "${customDateAndTime.day}${separator}${customDateAndTime.monthValue}${separator}${customDateAndTime.year}"
}

private fun setCustomText(text: String, textView: TextView){
    textView.text = text
}

class MyDiagnosisAdapter(val view: View): RecyclerView.ViewHolder(view){}