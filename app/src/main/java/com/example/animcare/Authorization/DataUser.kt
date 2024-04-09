package com.example.animcare.Authorization

data class DataUser(
    val email: String,
    val password: String,
    val repeatPassword: String,
    val uid: String,
    val name: String,
    val age: Int,
    val imagePath: String

)