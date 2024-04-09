package com.example.animcare.Authorization.Login

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.example.animcare.Authorization.Authorization
import com.example.animcare.Authorization.Registration.RegistrationFragment
import com.example.animcare.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_login.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    val registrationFragment = RegistrationFragment()

    var authorization = Authorization()
    override fun onStart() {
        super.onStart()

//        authorization_login.setText("kamilwin21@gmail.com")
//        authorization_password.setText("123456789")


        val user = authorization.getUser()
        user.setContext(requireContext())
        authorization = arguments?.getSerializable("authorization") as Authorization
        authorization.setTextInTextView(resources.getString(R.string.login))
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)


        authorization_login.setOnTouchListener(myViewOnTouchListener)
        authorization_password.setOnTouchListener(myViewOnTouchListener)

        authorization_login.setOnClickListener {


        }
        authorization_password.setOnClickListener {


        }


        btn_registration.setOnClickListener(btnRegistrationClickListener)


        btn_login.setOnClickListener{

            val loginDataValidation = user.loginDataValidation(
                requireContext(),
                authorization_login.text.toString(),
                authorization_password.text.toString()
            )
//            val loginDataValidation = user.loginDataValidation(
//                requireContext(),
//                "kamilus384@o2.pl",
//                "123456789"
//            )

            when(loginDataValidation){
                true -> {
                    //user.login(Firebase.auth, requireContext(), requireActivity(), "kamilus384@o2.pl", "123456789")
                    user.login(Firebase.auth, requireContext(), requireActivity(), authorization_login.text.toString(), authorization_password.text.toString())

                }
                false -> {

                }
            }







        }



    }

    private val myViewOnTouchListener = View.OnTouchListener { v, event ->
        if (MotionEvent.ACTION_UP == event!!.action){
            authorization.setGone()
        }
        false
    }


    val btnRegistrationClickListener = View.OnClickListener {

        authorization.setTextInTextView(resources.getString(R.string.registration))
        authorization.setVisibleImageAndTextView()
        authorization.goToFragment(registrationFragment)

        val bundle = Bundle()
        bundle.putSerializable("authorization", authorization)
        registrationFragment.arguments = bundle
        authorization.goToFragment(registrationFragment)

        hideKeyBoard()


    }

    private fun hideKeyBoard(){
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    private fun openKeyBoard(){
        val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}