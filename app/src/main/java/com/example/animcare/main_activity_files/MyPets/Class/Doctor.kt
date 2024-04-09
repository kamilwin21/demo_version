package com.example.animcare.main_activity_files.MyPets.Class

import java.io.Serializable

class Doctor: Serializable {
    private var name: String = String()
    private var surname: String = String()
    private var specialization: String = String()


    constructor()

    //Getters
    fun getName():String{
        return this.name
    }
    fun getSurname():String{
        return this.surname
    }
    fun getSpecialization():String{
        return this.specialization
    }


    //Setters
    fun setName(value: String){
        this.name = value
    }
    fun setSurname(value: String){
        this.surname = value
    }
    fun setSpecialization(value: String){
        this.specialization = value
    }






}