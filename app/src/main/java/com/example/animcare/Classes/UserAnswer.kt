package com.example.animcare.Classes

import java.io.Serializable

class UserAnswer: Serializable {
    var question: Question? = null
    var correctAnswer: String? = null
    var correctAnswers: ArrayList<String>? = null
    var userAnswer: String? = null
    var userAnswers: ArrayList<String>? = null

    constructor()
    constructor(question: Question, correctAnswer: String, userAnswer: String){
        this.question = question
        this.correctAnswer = correctAnswer
        this.userAnswer = userAnswer
    }
    constructor(question: Question, correctAnswers: ArrayList<String>, userAnswers: ArrayList<String>){
        this.question = question
        this.correctAnswers = correctAnswers
        this.userAnswers = userAnswers
    }
}