package com.example.animcare.Options.NewImage

import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animcare.DatabaseFiles.DataBase
import com.example.animcare.ImportantSettings
import com.example.animcare.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_new_image.*
import kotlinx.android.synthetic.main.fragment_new_image.view.*
import kotlinx.android.synthetic.main.fragment_studies_list.*
import kotlinx.android.synthetic.main.nav_header.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NewImageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewImageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        return inflater.inflate(R.layout.fragment_new_image, container, false)
    }

    override fun onStart() {
        super.onStart()



        val connectionFromFBDB = FirebaseDatabase.getInstance(DataBase.dbReferName).reference
        connectionFromFBDB.child("Users").child(FirebaseAuth.getInstance().currentUser!!.uid)
            .child("imagePath").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val imagePath = snapshot.getValue(String::class.java)!!
                        println("To jest wywołanie adaptera:");
                        var uri = Uri.parse(imagePath)
                        var inputSteam = requireContext().contentResolver.openInputStream(uri)

                        current_default_profile_image?.background =
                            Drawable.createFromStream(inputSteam, imagePath)
                        new_profile_image_recycler_view.layoutManager =
                            GridLayoutManager(requireContext(), 3)
                        new_profile_image_recycler_view.adapter = NewImageProfileAdapter(
                            requireContext(),
                            current_default_profile_image,
                            new_profile_image_recycler_view,
                            imagePath
                        )


                    } else {
                        println("Dane nie istnieją: ${FirebaseAuth.getInstance().currentUser!!.uid}")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }


            })

        newImageTW.setBackgroundColor(ImportantSettings.color)
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NewImageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewImageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}