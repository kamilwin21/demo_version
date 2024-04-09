package com.example.animcare.Classes

class Section {
    var name: String? = null
    var id: String = ""
    var paragraphs: ArrayList<Paragraph> = arrayListOf()
    constructor()

    constructor(id: String, name: String, paragraphs: ArrayList<Paragraph>){
        this.name = name
        this.id = id
        this.paragraphs = paragraphs
    }

}