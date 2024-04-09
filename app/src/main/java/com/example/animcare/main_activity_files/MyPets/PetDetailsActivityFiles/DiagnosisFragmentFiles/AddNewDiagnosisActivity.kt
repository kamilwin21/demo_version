package com.example.animcare.main_activity_files.MyPets.PetDetailsActivityFiles.DiagnosisFragmentFiles

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.animcare.DatabaseFiles.DataBase
import com.example.animcare.R
import com.example.animcare.main_activity_files.MyPets.Class.CustomDateAndTime
import com.example.animcare.main_activity_files.MyPets.Class.Diagnosis
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_new_diagnosis.*
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

class AddNewDiagnosisActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_diagnosis)

        setCustomActionBar()
        var counting = 0
        var diagnosisCount = intent?.getIntExtra("diagnosisCount", 0)
        calendar_diagnosis.visibility = View.GONE
        image_view_open_close_calendar.background = getDrawable(R.drawable.ic_baseline_arrow_downward_24)

        var typesDiagnosis = resources.getStringArray(R.array.types_diagnosis)
        if (type_diagnosis_spinner != null){
            val adapter = ArrayAdapter(applicationContext, R.layout.field_spinner , typesDiagnosis)
//            val adapter = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_item , typesDiagnosis)
            type_diagnosis_spinner.adapter = adapter
        }
//        var diagnosisList: ArrayList<Diagnosis> = arrayListOf()
//        if (intent.hasExtra("diagnosisList")){
//            diagnosisList = intent.getSerializableExtra("diagnosisList") as ArrayList<Diagnosis>
//        }



        //var diagnosis = Diagnosis("Morfologia - Październik", "Morfologia", ZonedDateTime.now(), "Kontrolne badanie krwi psa.")
        var diagnosis = Diagnosis()
        diagnosis.setDiagnosisName("Morfologia - Październik")
        diagnosis.setType("Morfologia")
        diagnosis.setDescription("Kontrolne badanie krwi psa. \n" +
                "Nowa linijka testowa dla badania \n" +
                "Kolejna linijka testowa dla badania")
        diagnosis.setDiagnosisDate(convertZoneDateTimeToCustomDateTime(ZonedDateTime.now()))
//        var diagnosis1 = Diagnosis("Parametry netrowe - Styczeń", "Parametry nerkowe", ZonedDateTime.now(), "Badanie parametrów nerkowych.")
        var diagnosis1 = Diagnosis()
        diagnosis1.setDiagnosisName("Parametry netrowe - Styczeń")
        diagnosis1.setType("Parametry nerkowe")
        diagnosis1.setDescription("Badanie parametrów nerkowych.")
        diagnosis1.setDiagnosisDate(convertZoneDateTimeToCustomDateTime(ZonedDateTime.now().minusDays(10)))


//        val connection = FirebaseDatabase.getInstance(DataBase.dbReferName).reference
//        connection.child("Diagnosis").child(FirebaseAuth.getInstance().currentUser!!.uid)
//            .setValue(arrayListOf(diagnosis, diagnosis1, diagnosis, diagnosis1))

        type_diagnosis_spinner.onItemSelectedListener = object:
        AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                println("PARENT: ${parent?.selectedItem.toString()}")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }


        calendar_diagnosis.setOnDateChangeListener { view, year, month, dayOfMonth ->

            date_diagnosis_edit_text.setText("${dayOfMonth}/${month+1}/${year}")

        }



        date_diagnosis_edit_text.setOnClickListener {
            if (counting == 0){
                image_view_open_close_calendar.background = getDrawable(R.drawable.ic_baseline_arrow_back_24)
                counting = 1
            }else if (counting == 1){
                image_view_open_close_calendar.background = getDrawable(R.drawable.ic_baseline_arrow_downward_24)
                counting = 0
            }

            when(calendar_diagnosis.visibility){
                View.VISIBLE -> {
                    calendar_diagnosis.visibility = View.GONE
                }
                View.GONE -> {
                    calendar_diagnosis.visibility = View.VISIBLE
                }
            }
        }



        image_view_open_close_calendar.setOnClickListener {

            if (counting == 0){
                it.background = getDrawable(R.drawable.ic_baseline_arrow_back_24)
                counting = 1
            }else if (counting == 1){
                it.background = getDrawable(R.drawable.ic_baseline_arrow_downward_24)
                counting = 0
            }
            when(calendar_diagnosis.visibility){
                View.VISIBLE -> {
                    calendar_diagnosis.visibility = View.GONE
                }
                View.GONE -> {
                    calendar_diagnosis.visibility = View.VISIBLE
                }
            }

        }

        btn_save_new_diagnosis.setOnClickListener {
            var localDate = convertToLocalDate(date_diagnosis_edit_text.text.toString())
            var customDateAndTime = convertZoneDateTimeToCustomDateTime(ZonedDateTime.of(localDate, LocalTime.now(), ZoneId.of("Europe/Warsaw")))
            var diagnosis = Diagnosis()
            diagnosis.setDiagnosisName(name_diagnosis.text.toString())
            diagnosis.setDescription(description_diagnosis.text.toString())
            diagnosis.setType(type_diagnosis_spinner.selectedItem.toString())
            diagnosis.setDiagnosisDate(customDateAndTime)

            val petName = intent.getStringExtra("PetName")!!
            val dbConnection = FirebaseDatabase.getInstance(DataBase.dbReferName).reference
            dbConnection.child("Diagnosis").child(FirebaseAuth.getInstance().currentUser!!.uid)
                .child(petName)
                .child("${diagnosisCount}").setValue(diagnosis)

            finish()
        }




    }

    override fun onBackPressed() {
        super.onBackPressed()
    }


    private fun setCustomActionBar(){
        supportActionBar!!.title = "${resources.getString(R.string.creating_diagnosis)}"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun convertToLocalDate(date: String): LocalDate {
        var regex = Regex("[0-9]+[\\/][0-9]+[\\/][0-9]+")
        var summary = date.matches(regex)
        var localDate = LocalDate.of(1000,1,1)
        when(summary){
            true -> {
                val separator = "/"
                var currentDate = date.split(separator)
                localDate = LocalDate.of(currentDate[2].toInt(), currentDate[1].toInt(), currentDate[0].toInt())


            }
            false -> {
                Toast.makeText(applicationContext, "Nieprawidłowy format daty", Toast.LENGTH_SHORT).show()
            }
        }



        return localDate
    }


    @RequiresApi(Build.VERSION_CODES.O)
        fun convertZoneDateTimeToCustomDateTime(zonedDateTime: ZonedDateTime): CustomDateAndTime {
        var accualDate = zonedDateTime
        var myCustomDateAndTime = CustomDateAndTime()
        myCustomDateAndTime.year = accualDate.year.toString()
        myCustomDateAndTime.day = accualDate.dayOfMonth.toString()
        myCustomDateAndTime.dayOfWeek = accualDate.dayOfWeek.toString()
        myCustomDateAndTime.hour = accualDate.hour.toString()
        myCustomDateAndTime.minutes = accualDate.minute.toString()
        myCustomDateAndTime.month = accualDate.month.toString()
        myCustomDateAndTime.monthValue = accualDate.monthValue.toString()
        myCustomDateAndTime.seconds = accualDate.second.toString()
        myCustomDateAndTime.zonedId = accualDate.zone.id.toString()

        return myCustomDateAndTime
    }
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    fun getZonedDateTime(): ZonedDateTime{
//
//        var year = this.year.toInt()
//        var month = this.monthValue.toInt()
//        var day = this.day.toInt()
//        var hour = this.hour.toInt()
//        var minutes = this.minutes.toInt()
//        var seconds = this.seconds.toInt()
//        var zonedDateTime = ZonedDateTime.of(LocalDate.of(year,month,day), LocalTime.of(hour, minutes,seconds), ZoneId.of(this.zonedId))
//
//        return zonedDateTime
//    }

}