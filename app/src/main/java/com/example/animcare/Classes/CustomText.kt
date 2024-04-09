package com.example.animcare.Classes

class CustomText {
    var customTextName: String = "null"
    var sections: ArrayList<Section> = arrayListOf()

    constructor()
    constructor(customTextName: String, sections: ArrayList<Section>):this(){
        this.customTextName = customTextName
        this.sections = sections
    }
}