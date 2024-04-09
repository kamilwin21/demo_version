package com.example.animcare.Authorization

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.animcare.Authorization.Login.LoginFragment
import com.example.animcare.Authorization.Registration.RegistrationFragment
import com.example.animcare.DatabaseFiles.DataBase
import com.example.animcare.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_authorization.*
import kotlinx.android.synthetic.main.fragment_details_about_animal.*
import kotlinx.android.synthetic.main.fragment_registration.*

class AuthorizationActivity : AppCompatActivity() {

    var authorization = Authorization()
    val loginFragment = LoginFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)

        authorization = setAuthorization()



        var bundle = Bundle()
        bundle.putSerializable("authorization", authorization)
        loginFragment.arguments = bundle
        authorization.goToFragment(loginFragment)
        authorization.getAndLoadStartingPhoto()

    }

    override fun onBackPressed() {
        authorization = setAuthorization()

        clearEditTextsFromRegistrationFragmentOnBackPressed()

        authorization.setTextInTextView(resources.getString(R.string.login))
        authorization.setVisibleImageAndTextView()
        authorization.removeLastFragmentFromFragmentsArray("RegistrationFragment", loginFragment)



    }

    //================================================================================================
    //====================================----Functions----===========================================
    //================================================================================================

    private fun clearEditTextsFromRegistrationFragmentOnBackPressed(){

        var id = supportFragmentManager.fragments.size - 1
        var fragment = supportFragmentManager.fragments[id]
        if (fragment.javaClass.simpleName == "RegistrationFragment"){
            fragment.authorization_login_registration.setText("")
            fragment.authorization_password_registration.setText("")
            fragment.authorization_repeat_password_registration.setText("")
            fragment.authorization_name_registration.setText("")
            fragment.authorization_age_registration.setText("")
        }

    }

    private fun setAuthorization():Authorization{
        authorization.setAuthorizationFragmentContainer(R.id.authorizationFragmentContainer)
        authorization.setFragmentActivity(supportFragmentManager)
        authorization.setImageView(image_view_authorization)
        authorization.setTextView(text_view_authorization)
        return authorization
    }

    private fun openKeyBoard(){
        val inputMethodManager = applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }


}