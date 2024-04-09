package com.example.animcare.main_activity_files.MyPets.Class

import android.os.Build
import androidx.annotation.RequiresApi
import java.io.Serializable

@RequiresApi(Build.VERSION_CODES.O)
data class DataStudy(
    var name: String = "",
    var date: CustomDateAndTime = CustomDateAndTime(),
    var doctor: Doctor = Doctor(),
    var cost: Int = 0,
    var description: String = "",
    var symptoms: ArrayList<String> = arrayListOf(),
    var diagnosis: String = "",
    var clinic: Clinic = Clinic()

): Serializable {

}