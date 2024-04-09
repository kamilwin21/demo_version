package com.example.animcare.main_activity_files.MyPets.Class

import java.io.Serializable

class Diagnosis : Serializable {
    private var diagnosisName: String = String()
    private var type: String = String()
    private var date: CustomDateAndTime = CustomDateAndTime()
    private var description: String = String()
    private var dateNextDiagnosis: CustomDateAndTime = CustomDateAndTime()

    constructor()

    fun getDiagnosisName(): String {
        return this.diagnosisName
    }

    fun getType(): String {
        return this.type
    }

    fun getDescription(): String {
        return this.description
    }

    fun getDiagnosisDate(): CustomDateAndTime {
        return this.date
    }

    fun getNextDiagnosisDate(): CustomDateAndTime {
        return this.dateNextDiagnosis
    }

    fun setDiagnosisName(diagName: String) {
        this.diagnosisName = diagName
    }

    fun setType(type: String) {
        this.type = type
    }

    fun setDiagnosisDate(date: CustomDateAndTime) {
        this.date = date

    }

    fun setDescription(description: String) {
        this.description = description
    }

    fun setNextDiagnosisDate(nextDiagnosisDate: CustomDateAndTime) {
        this.dateNextDiagnosis = nextDiagnosisDate
    }


}