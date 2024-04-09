package com.example.animcare.Classes

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class Quiz: Serializable {
    var quizName: String = String()
    var description: String = String()
    var amountQuestion: String = String()
    var questions: ArrayList<Question> = arrayListOf()


    constructor()
    constructor(quizName: String, description: String, amountQuestion: String, questions: ArrayList<Question>){
        this.quizName = quizName
        this.description = description
        this.amountQuestion = amountQuestion
        this.questions = questions

    }



}