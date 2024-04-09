package com.example.animcare.main_activity_files.MyPets

import java.io.Serializable

class PetInList: Serializable {
    var pName: String = String()
    var type: String = String()
    var imagePath: String = String()
    constructor()
    constructor(pName: String, type: String, imagePath: String){
        this.pName = pName
        this.type = type
        this.imagePath = imagePath
    }
}