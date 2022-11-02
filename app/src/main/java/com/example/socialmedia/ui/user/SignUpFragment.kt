package com.example.socialmedia.ui.user

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.socialmedia.R
import com.example.socialmedia.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpBinding.bind(view)
        // Initialize Firebase Auth
        auth = Firebase.auth

        binding.signUpScreenSignUpButton.setOnClickListener {

            val userName = binding.signUpScreenNameEditText.text.toString().trim()
            val email = binding.signUpScreenEmailEditText.text.toString().trim()
            val password = binding.signUpScreenPasswordEditText.text.toString().trim()


            if (userName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {

                auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                    Toast.makeText(activity, "Sucess", Toast.LENGTH_LONG).show()
                    val action = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
                    findNavController().navigate(action)
                }
                    .addOnFailureListener {
                        Toast.makeText(activity, it.localizedMessage, Toast.LENGTH_LONG).show()
                    }

            } else {

                binding.signUpScreenNameEditText.error = "This field cannot be empty"
                binding.signUpScreenNameEditText.requestFocus()
                return@setOnClickListener

                binding.signUpScreenEmailEditText.error = "Email Required"
                binding.signUpScreenEmailEditText.requestFocus()
                return@setOnClickListener

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.signUpScreenEmailEditText.error = "use @ "
                    binding.signUpScreenEmailEditText.requestFocus()
                    return@setOnClickListener
                }
                if (password.length < 6) {
                    binding.signUpScreenPasswordEditText.error = "6 char password required"
                    binding.signUpScreenPasswordEditText.requestFocus()
                    return@setOnClickListener
                }
            }

        }

        binding.signUpScreenSignInTextView.setOnClickListener {
            val action = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
            findNavController().navigate(action)
        }

        binding.signUpScreenForgotPasswordTextView.setOnClickListener {
            val action = SignUpFragmentDirections.actionSignUpFragmentToForgotPasswordFragment()
            findNavController().navigate(action)
        }
    }
}