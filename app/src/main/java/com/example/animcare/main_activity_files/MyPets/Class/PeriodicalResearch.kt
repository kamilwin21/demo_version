package com.example.animcare.main_activity_files.MyPets.Class

import java.io.Serializable

class PeriodicalResearch: Serializable {
    private var name: String = String()
    private var description: String = String()
    private var reason: String = String()
    private var period: Int = 0 //In months
    private var lastStudy: CustomDateAndTime = CustomDateAndTime()
    private var nextStudy: CustomDateAndTime = CustomDateAndTime()

    constructor()

    fun getName(): String{
        return this.name
    }
    fun getDescription(): String{
        return this.description
    }
    fun getReason(): String{
        return this.reason
    }
    fun getPeriod(): Int{
        return this.period
    }
    fun geLastStudy(): CustomDateAndTime{
        return this.lastStudy
    }
    fun getNextStudy(): CustomDateAndTime{
        return this.nextStudy
    }

    fun setName(value: String){
        this.name = value
    }
    fun setDescription(value: String){
        this.description = value
    }
    fun setReason(value: String){
        this.reason = value
    }
    fun setPeriod(value: Int){
        this.period = value
    }
    fun setLastStudy(value: CustomDateAndTime){
        this.lastStudy = value
    }
    fun setNextStudy(value: CustomDateAndTime){
        this.nextStudy = value
    }





}