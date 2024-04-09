package com.example.animcare.Classes

import java.io.Serializable

class Dog: Serializable {
    var race: String = "null"
    var image: String = "null"
    var character: String = "null"
    var weight: String = "null"
    var marking: String = "null"
    var height: String = "null"
    var origins: String = "null"
    var lifetime: String = "null"
    var detailedDescription: CustomText = CustomText()


    constructor()
    constructor(race: String, image: String, character: String, weight: String, marking: String, height: String,
                origins: String, lifetime: String, detailedDescription: CustomText
                ){
        this.race = race
        this.image = image
        this.character = character
        this.weight = weight
        this.marking = marking
        this.height = height
        this.origins = origins
        this.lifetime = lifetime
        this.detailedDescription = detailedDescription
    }

}