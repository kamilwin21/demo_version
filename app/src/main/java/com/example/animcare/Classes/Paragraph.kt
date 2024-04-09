package com.example.animcare.Classes

class Paragraph{
    var position: String = ""
    var viewType: String = ""
    var imageLocalization: ImageLocalization = ImageLocalization()
    var text: String = ""

    constructor()

    constructor(position: String, viewType: String,
                imageLocalization: ImageLocalization, text: String){
        this.position = position
        this.viewType = viewType
        this.imageLocalization = imageLocalization
        this.text = text
    }



}