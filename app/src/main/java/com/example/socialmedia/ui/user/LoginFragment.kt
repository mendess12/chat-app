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
import com.example.socialmedia.databinding.FragmentLoginBinding
import com.example.socialmedia.util.BaseCurrent
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth
    var baseCurrent = BaseCurrent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        baseCurrent.currentUser = auth.currentUser
        if (baseCurrent.currentUser != null) {
            val action = LoginFragmentDirections.actionLoginFragmentToMainActivity2()
            findNavController().navigate(action)
        }

        binding.loginScreenLoginButton.setOnClickListener {

            val email = binding.loginScreenEmailEditText.text.toString().trim()
            val password = binding.loginScreenPasswordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {

                auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                    Toast.makeText(activity, "Sucess", Toast.LENGTH_LONG).show()
                    val action = LoginFragmentDirections.actionLoginFragmentToMainActivity2()
                    findNavController().navigate(action)
                }.addOnFailureListener {
                    Toast.makeText(activity, it.localizedMessage, Toast.LENGTH_LONG).show()
                }
            } else {

                if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.loginScreenEmailEditText.error = "Check your email"
                    binding.loginScreenEmailEditText.requestFocus()
                    return@setOnClickListener
                }
                if (password.length < 6) {
                    binding.loginScreenPasswordEditText.error = "6 char password required"
                    binding.loginScreenPasswordEditText.requestFocus()
                    return@setOnClickListener
                }
            }
        }

        binding.loginScreenSignUpTextView.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
            findNavController().navigate(action)
        }

        binding.loginScreenForgotPasswordTextView.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment()
            findNavController().navigate(action)
        }
    }
}