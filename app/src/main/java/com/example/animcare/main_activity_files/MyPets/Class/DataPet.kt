package com.example.animcare.main_activity_files.MyPets.Class

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.animcare.main_activity_files.MyPets.PetInList
import java.io.Serializable

@RequiresApi(Build.VERSION_CODES.O)
data class DataPet(
    var nameAndType: PetInList = PetInList(),
    var birthday: CustomDateAndTime = CustomDateAndTime(),
    var weight: Double = 0.0,
    var age: Age = Age(),
    var height: Double = 0.0,
    var studies: ArrayList<Study> = arrayListOf(),
    var periodicalResearch: ArrayList<PeriodicalResearch> = arrayListOf(),
    var profileSrc: String = String()

): Serializable {
}