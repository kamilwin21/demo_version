package com.example.animcare.Classes

import java.io.Serializable

class Question: Serializable {
    var question: String? = null
    var answer1: String? = null
    var answer2: String? = null
    var answer3: String? = null
    var answer4: String? = null
    //var trueOrfalse: TruthFalsehood? = null
    var correctAnswer: String? = null
    var correctAnswers: ArrayList<String>? = null
    var points: String = String()
    var type: String = String()//  SingleChoise, MultipleChoise, TrueOrFalse, DragAndDrop

    constructor()
    constructor(question: String,
                correctAnswer: String,points: String,type: String){
        this.question = question
        //this.trueOrfalse = trueOrfalse
        this.correctAnswer = correctAnswer
        this.points = points
        this.type = type
    }

    constructor(question: String, answer1: String, answer2:String, answer3:String,
                answer4: String, correctAnswer: String, points: String, type: String
    ){
        this.question = question
        this.answer1 = answer1
        this.answer2 = answer2
        this.answer3 = answer3
        this.answer4 = answer4
        this.correctAnswer = correctAnswer
        this.points = points
        this.type = type

    }
    constructor(question: String, answer1: String, answer2:String, answer3:String,
                answer4: String, correctAnswers: ArrayList<String>, points: String,
                type: String
    ){
        this.question = question
        this.answer1 = answer1
        this.answer2 = answer2
        this.answer3 = answer3
        this.answer4 = answer4
        this.correctAnswers = correctAnswers
        this.points = points
        this.type = type

    }


}