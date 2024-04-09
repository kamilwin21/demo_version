package com.example.animcare.main_activity_files.MyPets.Class

import java.io.Serializable

class CustomDateAndTime: Serializable {
    var day: String = String()
    var dayOfWeek: String = String()
    var month: String = String()
    var monthValue: String = String()
    var year: String = String()
    var hour: String = String()
    var minutes: String = String()
    var seconds: String = String()
    var zonedId: String = String()

    constructor()

    constructor(day: String,dayOfWeek: String, month:String,monthValue: String, year: String,
                hour: String, minutes: String, seconds: String, zonedId: String
                ){
        this.day = day
        this.dayOfWeek = dayOfWeek
        this.month = month
        this.monthValue = monthValue
        this.year = year
        this.hour = hour
        this.minutes = minutes
        this.seconds = seconds
        this.zonedId = zonedId
    }




}