package com.example.socialmedia.ui.user

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.socialmedia.R
import com.example.socialmedia.databinding.FragmentLoginBinding
import com.example.socialmedia.util.BaseCurrent
import com.example.socialmedia.viewmodel.LoginViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth
    var baseCurrent = BaseCurrent()
    var loginViewModel = LoginViewModel()

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
        loginViewModel = ViewModelProviders.of(requireActivity()).get(loginViewModel::class.java)
        auth = FirebaseAuth.getInstance()

        baseCurrent.currentUser = auth.currentUser
        if (baseCurrent.currentUser != null) {
            val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
            findNavController().navigate(action)
        }

        binding.loginScreenLoginButton.setOnClickListener {

            loginViewModel.email = binding.loginScreenEmailEditText.text.toString().trim()
            loginViewModel.password = binding.loginScreenPasswordEditText.text.toString().trim()

            if (loginViewModel.email.isNotEmpty() && loginViewModel.password.isNotEmpty()) {
                obserLiveData()
            } else {

                if (loginViewModel.email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(
                        loginViewModel.email
                    ).matches()
                ) {
                    binding.loginScreenEmailEditText.error = "Check your email"
                    binding.loginScreenEmailEditText.requestFocus()
                    return@setOnClickListener
                }
                if (loginViewModel.password.length < 6) {
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

    fun obserLiveData() {
        loginViewModel.loginData!!.observe(viewLifecycleOwner) {
            if (it != null) {
                val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                findNavController().navigate(action)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Check your email and password!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        loginViewModel.login()
    }
}