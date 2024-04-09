package com.example.animcare.main_activity_files.MyPets.Class

import java.io.Serializable

class Age: Serializable {
    private var year: String = "0"
    private var months: String = "0"

    constructor()

    fun getYear(): Int{
        return this.year.toInt()
    }
    fun getMonths():Int{
        return this.months.toInt()
    }

    fun setValues(value: Int){
        this.year = value.toString()
        this.months = (value * 12).toString()

    }
    fun setYear(value: Int){
        this.year = value.toString()

    }
    fun setMonths(value: Int){
        this.months = value.toString()
    }


}