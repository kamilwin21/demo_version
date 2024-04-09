package com.example.animcare.Authorization

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.example.animcare.Authorization.Registration.RegistrationFragment
import com.example.animcare.DatabaseFiles.DataBase
import com.example.animcare.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_authorization.*
import java.io.Serializable

class Authorization: Serializable {
    private var user = User()
    private var fragmentManager: FragmentManager? = null
    private var authorizationFragmentContainer = 0
    private var registrationFragment = RegistrationFragment()
    private var authorization_image_view: ImageView? = null
    private var authorization_text_view: TextView? = null
    private var context: Context? = null

    constructor()

    //======================================================================================
    //==================================----SETTERS----=====================================
    //======================================================================================


    fun setContext(context: Context){
        this.context = context
    }
    fun setImageView(imageView: ImageView){
        this.authorization_image_view = imageView
    }
    fun setTextView(textView: TextView){
        this.authorization_text_view = textView
    }
    fun setAuthorizationFragmentContainer(authorizationFragmentContainer: Int){
        this.authorizationFragmentContainer = authorizationFragmentContainer
    }
    fun setFragmentActivity(fragmentManager: FragmentManager){
        this.fragmentManager = fragmentManager
    }
    fun setUser(user: User){
        this.user = user
    }

    //======================================================================================
    //================================----Getters----=======================================
    //======================================================================================
    fun getImageView(): ImageView{
        return this.authorization_image_view!!
    }
    fun getTextView(): TextView{
        return this.authorization_text_view!!
    }
    fun getAuthorizationFragmentContainer():Int{
        return this.authorizationFragmentContainer
    }
    fun getFragmentActivity(): FragmentManager{
        return this.fragmentManager!!
    }
    fun getUser():User{
        return this.user
    }

    //======================================================================================
    //==============================----Functions----=======================================
    //======================================================================================




    fun setVisibleImageAndTextView(){
        this.authorization_image_view!!.visibility = View.VISIBLE
        this.authorization_text_view!!.visibility = View.VISIBLE
    }
    fun setGone(){
        this.authorization_image_view!!.visibility = View.GONE
        this.authorization_text_view!!.visibility = View.GONE

    }
    fun setTextInTextView(text: String){
        this.authorization_text_view?.text = text
    }

    fun removeLastFragmentFromFragmentsArray(nameFragmentToBeDelete: String, nameFragmentToBeLoadedInFragmentContainer: Fragment){
        val supportFragmentManager = this.fragmentManager!!
        val size = supportFragmentManager.fragments.size
        if (supportFragmentManager.fragments[size -1].javaClass.simpleName == "${nameFragmentToBeDelete}"){
            supportFragmentManager.popBackStack()
            var transaction = supportFragmentManager.beginTransaction()
                .replace(authorizationFragmentContainer, nameFragmentToBeLoadedInFragmentContainer)

            transaction.commit()
        }
    }


    fun getAndLoadStartingPhoto(){
        var imgView = this.authorization_image_view
        val dbC = FirebaseDatabase.getInstance(DataBase.dbReferName).reference
        dbC.child("AuthorizationImages").child("login_registration")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var srcImg = String()
                    if (snapshot.exists()){
                        srcImg = snapshot.getValue(String::class.java)!!

                    }

                    Picasso.get().load(srcImg).into(imgView)
                }

                override fun onCancelled(error: DatabaseError) {

                }


            })


    }


    fun login(){

    }
    fun register(){

    }
    fun goToFragment(fragment: Fragment){
        val supportFragmentManager = this.fragmentManager!!
        supportFragmentManager.beginTransaction()
            .replace(this.authorizationFragmentContainer, fragment)
            .addToBackStack(fragment.javaClass.name)
            .commit()
    }
    fun goToActivity(className: Class<*>){
        val intent = Intent(this.context, className)
        this.context!!.startActivity(intent)
    }


}