package com.example.animcare.main_activity_files.MyPets

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.animcare.main_activity_files.MyPets.Class.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.io.Serializable

@RequiresApi(Build.VERSION_CODES.O)
class Pet: Serializable {
    private var nameAndType: PetInList = PetInList()
    private var birthday: CustomDateAndTime = CustomDateAndTime()
    private var weight: Double = 0.0
    private var age: Age = Age()
    private var height: Double = 0.0
    private var studies: ArrayList<Study> = arrayListOf()
    private var periodicalResearch: ArrayList<PeriodicalResearch> = arrayListOf()
    private var profileSrc: String = String()


    private var DBInstance: String = String()
    private var dataPet: DataPet = DataPet()

    constructor()
    private fun connectFromDB(DBInstance: String): DatabaseReference {
        return FirebaseDatabase.getInstance(DBInstance).reference
    }

    private fun setValueToDataPet(){
        this.dataPet.nameAndType = this.nameAndType
        this.dataPet.birthday = this.birthday
        this.dataPet.height = this.weight
        this.dataPet.age = this.age
        this.dataPet.height = this.height
        this.dataPet.studies = this.studies
        this.dataPet.periodicalResearch = this.periodicalResearch
        this.dataPet.profileSrc = this.profileSrc
    }
    fun saveValueToDataBase(){
        setValueToDataPet()
        var connection = connectFromDB(this.DBInstance)
        connection.child("Pets").child(FirebaseAuth.getInstance().currentUser!!.uid).child("${this.nameAndType.pName}")
            .setValue(this.dataPet)
    }


    private fun saveDataFromDBToClass(dataPetH: DataPet): DataPet{
        this.nameAndType = dataPetH.nameAndType
        this.height = dataPetH.height
        this.age = dataPetH.age
        this.height = dataPetH.height
        this.studies = dataPetH.studies



        return dataPetH
    }


    fun getNameAndType(): PetInList{
        return this.nameAndType
    }
    fun getBirthday(): CustomDateAndTime{
        return this.birthday
    }
    fun getWeight(): Double{
        return weight
    }
    fun getAge(): Age{
        return this.age
    }
    fun getHeight(): Double{
        return this.height
    }
    fun getStudy(): ArrayList<Study>{
        return this.studies
    }
    fun getPeriodicalResearch(): ArrayList<PeriodicalResearch>{
        return this.periodicalResearch
    }
    fun getProfileSrc(): String{
        return this.profileSrc
    }


    fun setDBInstance(value: String){
        this.DBInstance = value
    }

    fun setNameAndType(value: PetInList){
        this.nameAndType = value
    }
    fun setBirthday(value: CustomDateAndTime){
        this.birthday = value
    }
    fun setWeight(value: Double){
        this.weight = value
    }
    fun setAge(value: Age){
        this.age = value
    }
    fun setHeight(value: Double){
        this.height = value
    }
    fun setStudy(value: ArrayList<Study>){
        this.studies = value
    }
    fun setPeriodicalResearch(value: ArrayList<PeriodicalResearch>){
        this.periodicalResearch = value
    }
    fun setProfileSrc(value: String){
        this.profileSrc = value
    }





}