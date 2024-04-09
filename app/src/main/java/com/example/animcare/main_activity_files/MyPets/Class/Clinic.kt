package com.example.animcare.main_activity_files.MyPets.Class

import java.io.Serializable

class Clinic: Serializable {
     private var name: String = String()
     private var address: String = String()

    constructor()
    fun getName():String{
        return this.name
    }
    fun getAddress():String{
        return this.address
    }
    fun setName(value: String){
        this.name = value
    }
    fun setAddress(value: String){
        this.address = value
    }

//    var getName: String = String()
//        get() = this.name
//    var getAddress: String = String()
//        get() = this.address
//
//
//    var setName: String = String()
//        set(value){
//            this.name = value
//        }
//    var setAddress: String = String()
//        set(value){
//            this.address = value
//        }


}