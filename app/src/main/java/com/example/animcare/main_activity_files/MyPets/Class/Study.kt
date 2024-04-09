package com.example.animcare.main_activity_files.MyPets.Class

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.io.Serializable

@RequiresApi(Build.VERSION_CODES.O)
class Study: Serializable {
    private var name: String = String()
    private var date: CustomDateAndTime = CustomDateAndTime()
    private var doctor: Doctor = Doctor()
    private var cost: String = String()
    private var description: String = String()
    private var clinic: Clinic = Clinic()
    private var symptoms: ArrayList<String> = arrayListOf()
    private var diagnosis: String = String()
    private var DBInstance: String = String()
    private var dataStudy: DataStudy = DataStudy()

    constructor()
    private fun setValueToDataStudy(){
        this.dataStudy.name = this.name
        this.dataStudy.date = this.date
        this.dataStudy.doctor = this.doctor
        this.dataStudy.cost = this.cost.toInt()
        this.dataStudy.description = this.description
        this.dataStudy.clinic = this.clinic
        this.dataStudy.symptoms = this.symptoms
        this.dataStudy.diagnosis = this.diagnosis

    }
    private fun setValueForThisClass(dataStudy: DataStudy){
        this.name = dataStudy.name
        this.date = dataStudy.date
        this.doctor = dataStudy.doctor
        this.cost = dataStudy.cost.toString()
        this.description = dataStudy.description
        this.clinic = dataStudy.clinic
        this.symptoms = dataStudy.symptoms
        this.diagnosis = dataStudy.diagnosis
    }

    fun setValueTODataBase(){
        setValueToDataStudy()
        var connection = connectFromDB(this.DBInstance)
        connection.child("Studies").child(FirebaseAuth.getInstance().currentUser!!.uid)
            .setValue(dataStudy)

    }

    fun getValueFromDataBase(){
        var gettedData = DataStudy()
        var connection = connectFromDB(this.DBInstance)
        connection.child("Studies").child(FirebaseAuth.getInstance().currentUser!!.uid)
            .addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        println("SNAPSHOT: ${snapshot}")
                        gettedData = snapshot.getValue(DataStudy::class.java)!!
                        println("Name: ${gettedData.name}")
                        println("Name: ${gettedData.description}")
                        println("Name: ${gettedData.cost}")
                        println("Name: ${gettedData.date.month}")
                        println("Name: ${gettedData.clinic.getAddress()}")
                        println("Name: ${gettedData.doctor.getName()}")
                        println("Name: ${gettedData.doctor.getSpecialization()}")



                    }
                    setValueForThisClass(gettedData)
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
    }
    private fun connectFromDB(DBInstance: String):DatabaseReference{
        return FirebaseDatabase.getInstance(DBInstance).reference
    }

    fun getName(): String{
        return this.name
    }
    fun getDate(): CustomDateAndTime{
        return this.date
    }
    fun getDoctor(): Doctor{
        return this.doctor
    }
    fun getCost(): Int{
        return this.cost.toInt()
    }
    fun getDescription(): String{
        return this.description
    }
    fun getClinic(): Clinic{
        return this.clinic
    }
    fun getSymptoms(): ArrayList<String>{
        return this.symptoms
    }
    fun getDiagnosis(): String{
        return this.diagnosis
    }

    //SETTERS
    fun setDBInstance(value: String){
        this.DBInstance = value
    }
    fun setName(value: String){
        this.name = value
    }
    fun setDate(value: CustomDateAndTime){
        this.date = value
    }
    fun setDoctor(value: Doctor){
        this.doctor = value
    }
    fun setCost(value: Int){
        this.cost = value.toString()
    }
    fun setDescription(value: String){
        this.description = value
    }
    fun setClinic(value: Clinic){
        this.clinic = value
    }
    fun setSymptoms(value: ArrayList<String>){
        this.symptoms = value
    }
    fun setDiagnosis(value: String){
        this.name = value
    }

}