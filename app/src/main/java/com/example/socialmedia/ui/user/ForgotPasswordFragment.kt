package com.example.socialmedia.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.socialmedia.R
import com.example.socialmedia.databinding.FragmentForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordFragment : Fragment() {

    private lateinit var binding: FragmentForgotPasswordBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentForgotPasswordBinding.bind(view)
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        binding.forgotPasswordScreenResetPasswordButton.setOnClickListener {
            val email = binding.forgotPasswordScreenEmailEditText.text.toString()

            if (email.isNotEmpty()) {
                auth.sendPasswordResetEmail(email).addOnSuccessListener {
                    Toast.makeText(activity, "success", Toast.LENGTH_LONG).show()
                    val action =
                        ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToLoginFragment()
                    findNavController().navigate(action)
                }.addOnFailureListener {
                    Toast.makeText(activity, it.localizedMessage, Toast.LENGTH_LONG).show()
                    println(it.localizedMessage)
                }
            } else {
                binding.forgotPasswordScreenEmailEditText.error = "Email Required"
                binding.forgotPasswordScreenEmailEditText.requestFocus()
                return@setOnClickListener
            }
        }
    }
}