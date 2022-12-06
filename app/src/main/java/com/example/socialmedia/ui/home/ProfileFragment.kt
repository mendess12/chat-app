package com.example.socialmedia.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.socialmedia.R
import com.example.socialmedia.databinding.FragmentProfileBinding
import com.example.socialmedia.ui.user.MainActivity
import com.example.socialmedia.util.BaseCurrent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentProfileBinding
    private lateinit var firestore: FirebaseFirestore
    var baseCurrent = BaseCurrent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        getUserData()

        binding.profileScreenSignOutTextView.setOnClickListener {
            auth.signOut()
            //baseCurrent.currentUser = null
            println("${baseCurrent.currentUser}")
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        binding.profileScreenChangePasswordTextView.setOnClickListener {

            val action = ProfileFragmentDirections.actionProfileFragmentToChangePasswordFragment()
            findNavController().navigate(action)

        }
    }

    private fun getUserData() {
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        firestore.collection("Users").document("${uid}").get().addOnSuccessListener { document ->

            if (document != null) {
                val userName = document.get("userName") as String
                val userEmail = document.get("userEmail") as String
                binding.profileScreenUserNameTextView.text = userName
                binding.profileScreenEmailTextView.text = userEmail

            } else {
                Toast.makeText(requireContext(), "Document is null", Toast.LENGTH_LONG).show()
            }
        }.addOnFailureListener {
            Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_LONG).show()
        }
    }
}