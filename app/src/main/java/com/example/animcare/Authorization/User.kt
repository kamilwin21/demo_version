package com.example.animcare.Authorization

import android.content.Context
import android.content.Intent
import android.text.BoringLayout
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.animcare.DatabaseFiles.DataBase
import com.example.animcare.MainActivity
import com.example.animcare.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_registration.*
import java.io.Serializable
import java.lang.Exception
import java.util.regex.Pattern
import kotlin.math.log

class User: Serializable {
    private var email: String = String()
    private var password: String = String()
    private var repeatPassword: String = String()
    private var uid: String = "0"
    private var name: String = String()
    private var age: Int = 0
    private var imagePath: String = "android.resource://com.example.animcare/drawable/default_profile_image"
    private var dataStatusValidationLogin: Boolean = false
    private var dataStatusValidationPassword: Boolean = false
    private var dataStatusValidationRepeatPassword: Boolean = false
    private var dataStatusValidationName: Boolean = false
    private var dataStatusValidationAge: Boolean = false
    private var context: Context? = null
    private var activity: FragmentActivity? = null

    constructor()



    fun setActivity(activity: FragmentActivity){
        this.activity
    }

    fun setImagePath(imagePath: String){
        this.imagePath = imagePath
    }
    fun setEmail(email: String): Boolean{
        var emailCorrectness = emailCorrectness(email)

        return when(emailCorrectness){
            true -> {
                this.email = email
                true
            }
            false -> {
                false
            }

        }
    }
    fun setRepeatPassword(repeatPassword: String): Boolean{
        return when(passwordCorrectness(repeatPassword)){
            true -> {
                this.repeatPassword = repeatPassword
                true
            }
            false -> {
                false
            }
        }
    }

    fun setPassword(password: String): Boolean{
        var passwordCorrectness = passwordCorrectness(password)

        return when(passwordCorrectness){
            true -> {
                this.password = password
                true
            }
            false -> {
                false
            }
        }
    }
    fun setUid(uid: String){
        this.uid = uid
    }
    fun setName(name: String): Boolean{
        var correctness = nameCorrectness(name)
        return when(correctness){
            true -> {
                this.name = name
                true
            }
            false -> {
                false
            }
        }
    }
    fun setAge(age: String): Boolean{
        var correctness = false
        if (age.isNullOrEmpty()){
            correctness = false
        }else{
            correctness = ageCorrectness(age.toInt())
        }
        return when(correctness){
            true -> {
                this.age = age.toInt()
                true
            }
            false -> { false }
        }
    }
    fun setContext(context: Context){
        this.context = context
    }

    fun getActivity(): FragmentActivity{
        return this.activity!!
    }

    fun getRepeatPassword(): String{
        return this.repeatPassword
    }

    fun getImagePath(): String{
        return this.imagePath
    }

    fun getEmail(): String{
        return this.email
    }
    fun getPassword(): String{
        return this.password
    }
//    fun getUid():String{
//        return this.uid
//    }
    fun getName(): String{
        return this.name
    }
    fun getAge(): Int{
        return this.age
    }
    fun getContext(): Context{
        return this.context!!
    }

    private fun getDataStatusValidation(): Boolean{

        return if (dataStatusValidationLogin && dataStatusValidationPassword && dataStatusValidationRepeatPassword &&
            dataStatusValidationName && dataStatusValidationAge){
            dataStatusValidationPassword == dataStatusValidationRepeatPassword

        }else{
            false
        }
    }

    fun login(firebaseAuth: FirebaseAuth,requireContext: Context, requireActivity: FragmentActivity , login: String, password: String): Boolean{
        var auth = firebaseAuth
        auth = Firebase.auth
        println("Email: ${login}")
        println("Password: ${password}")
        auth.signInWithEmailAndPassword(login, password)
            .addOnCompleteListener(requireActivity){
                if(it.isSuccessful){
                    Toast.makeText(requireContext, "Successed Login", Toast.LENGTH_SHORT).show()
                    val intent = Intent(requireContext, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    requireActivity.startActivity(intent)
                    Toast.makeText(requireContext, "Pomyślnie zalogowano.", Toast.LENGTH_SHORT).show()


                }else{
                    Toast.makeText(requireContext, "Failed Login", Toast.LENGTH_SHORT).show()
                }
            }


        return false
    }

    fun register(): Boolean{
        when(getDataStatusValidation()){
            true -> {
                Toast.makeText(this.context, "Pomyślnie zalogowano", Toast.LENGTH_SHORT).show()
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(this.email, this.password)
                    .addOnCompleteListener{

                        val dataUser = DataUser(this.email, this.password,
                            this.repeatPassword, FirebaseAuth.getInstance().currentUser!!.uid
                            , this.name,
                            this.age,
                            this.imagePath)

                        val dbConnect = FirebaseDatabase.getInstance(DataBase.dbReferName).reference
                        dbConnect.child("Users").child(FirebaseAuth.getInstance().currentUser!!.uid).setValue(dataUser)



                    }
                return true


            }
            false -> {
                Toast.makeText(this.context, "Błąd rejestracji", Toast.LENGTH_SHORT).show()
                return false
            }
        }



    }

    fun loginDataValidation(requireContext: Context, checkEmail: String, checkPassword: String): Boolean{


        return when(checkEmail.isNullOrEmpty() || checkPassword.isNullOrEmpty()){
            true -> {
                Toast.makeText(requireContext, "Błędne dane!", Toast.LENGTH_SHORT).show()
                false
            }
            false -> {
                true

            }

        }


    }


    fun registrationDataValidation(requireContext: Context, checkEmail: Boolean,
        checkPassword: Boolean, checkRepeatPassword: Boolean, checkName: Boolean, checkAge: Boolean,
                                   authorization_login_registration: EditText,
                                    authorization_password_registration: EditText,
                                   authorization_repeat_password_registration: EditText,
                                   authorization_name_registration: EditText,
                                   authorization_age_registration: EditText,
                                   eye_showing_password: ImageView,
                                   eye_showing_repeat_password: ImageView
                                   ){


        when(checkEmail){
            true -> {
                println("Email -> poprawny")
                authorization_login_registration.background = ContextCompat.getDrawable(requireContext, R.drawable.green_border)
                dataStatusValidationLogin= true

            }
            false -> {
                println("Email -> niepoprawny")
                authorization_login_registration.background = ContextCompat.getDrawable(requireContext, R.drawable.red_border)

                dataStatusValidationLogin = false
            }
        }
        when(checkPassword){
            true -> {
                println("Password -> poprawny")
                authorization_password_registration.background = ContextCompat.getDrawable(requireContext, R.drawable.green_border)
                eye_showing_password.background = ContextCompat.getDrawable(requireContext, R.drawable.ic_baseline_remove_green_eye_24)
                dataStatusValidationPassword = true


            }
            false -> {
                println("Password -> niepoprawny")
                authorization_password_registration.background = ContextCompat.getDrawable(requireContext, R.drawable.red_border)
                eye_showing_password.background = ContextCompat.getDrawable(requireContext, R.drawable.ic_baseline_remove_red_eye_24)
                dataStatusValidationPassword = false

            }

        }
        when(checkRepeatPassword){
            true -> {
                println("RepeatPassword -> poprawny")
                authorization_repeat_password_registration.background = ContextCompat.getDrawable(requireContext, R.drawable.green_border)
                eye_showing_repeat_password.background = ContextCompat.getDrawable(requireContext, R.drawable.ic_baseline_remove_green_eye_24)
                dataStatusValidationRepeatPassword = true

                if (this.repeatPassword == this.password){
                    authorization_repeat_password_registration.background = ContextCompat.getDrawable(requireContext, R.drawable.green_border)
                    eye_showing_repeat_password.background = ContextCompat.getDrawable(requireContext, R.drawable.ic_baseline_remove_green_eye_24)
                    dataStatusValidationRepeatPassword = true

                }else {
                    authorization_repeat_password_registration.background = ContextCompat.getDrawable(requireContext, R.drawable.red_border)
                    eye_showing_repeat_password.background = ContextCompat.getDrawable(requireContext, R.drawable.ic_baseline_remove_red_eye_24)
                    dataStatusValidationRepeatPassword = false

                }

            }
            false -> {
                println("RepeatPassword -> niepoprawny")
                authorization_repeat_password_registration.background = ContextCompat.getDrawable(requireContext, R.drawable.red_border)
                eye_showing_repeat_password.background = ContextCompat.getDrawable(requireContext, R.drawable.ic_baseline_remove_red_eye_24)
                dataStatusValidationRepeatPassword = false
            }

        }
        when(checkName) {
            true -> {
                println("Name -> poprawny")
                authorization_name_registration.background =
                    ContextCompat.getDrawable(requireContext, R.drawable.green_border)
                dataStatusValidationName = true
            }
            false -> {
                println("Name -> niepoprawny")
                authorization_name_registration.background =
                    ContextCompat.getDrawable(requireContext, R.drawable.red_border)
                dataStatusValidationName = false
            }
        }

        when(checkAge){
            true -> {
                println("Age -> poprawny")
                authorization_age_registration.background =
                    ContextCompat.getDrawable(requireContext, R.drawable.green_border)
                dataStatusValidationAge = true

            }
            false -> {
                println("Age -> niepoprawny")
                authorization_age_registration.background =
                    ContextCompat.getDrawable(requireContext, R.drawable.red_border)
                dataStatusValidationAge = false

            }
        }

    }

    private fun ageCorrectness(age: Int): Boolean{
        var pattern = Pattern.compile("[0-9]+")
        val matcher = pattern.matcher(age.toString())
        return when(matcher.matches()){
            true -> { true }
            false -> { false }
        }
    }

    private fun nameCorrectness(name: String): Boolean{
        var pattern = Pattern.compile("[A-Z]{1}[a-z]+")
        var manager = pattern.matcher(name)
        var matches = manager.matches()
        return when(matches){
            true -> {
                true

            }
            false -> {
                false
            }
        }
    }

    private fun emailCorrectness(email: String): Boolean{
        var pattern = Pattern.compile("[\\w]+@[\\w]+\\.[\\w]+")
        var matcher = pattern.matcher(email)
        var matches = matcher.matches()

        return when(matches){
            true -> {
                true
            }
            false -> {
                false
            }

        }

    }
    private fun passwordCorrectness(password: String): Boolean{
        var pattern = Pattern.compile("[[a-zA-Z0-9]]{9,}+")
        var matcher = pattern.matcher(password)
        var matches = matcher.matches()

        return when(matches){
            true -> {
                true
            }
            false -> {
                false
            }

        }

    }



}