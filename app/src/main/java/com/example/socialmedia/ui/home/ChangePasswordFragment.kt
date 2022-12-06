package com.example.socialmedia.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.socialmedia.R
import com.example.socialmedia.databinding.FragmentChangePasswordBinding
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ChangePasswordFragment : Fragment() {

    private lateinit var binding: FragmentChangePasswordBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentChangePasswordBinding.bind(view)
        auth = FirebaseAuth.getInstance()

        var password: String
        var newPassword: String
        var retypeNewPassword: String

        password = binding.passwordChangePasswordScreenEditText.text.toString().trim()
        newPassword = binding.passwordChangeNewPasswordScreenEditText.text.toString().trim()
        retypeNewPassword =
            binding.passwordChangeRetypeNewPasswordScreenEditText.text.toString().trim()

        binding.changePasswordScreenSaveButton.setOnClickListener {

            if (password.isNotEmpty() && newPassword.isNotEmpty() && retypeNewPassword.isNotEmpty()) {

                if (newPassword == retypeNewPassword) {
                    val user: FirebaseUser = auth.currentUser!!
                    if (user != null && user.email != null) {

                        val credential = EmailAuthProvider
                            .getCredential(user.email!!, password)
                        user.reauthenticate(credential)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    Toast.makeText(
                                        requireContext(),
                                        "Re-Authentication success.",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    user!!.updatePassword(newPassword)
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                Toast.makeText(
                                                    requireContext(),
                                                    "Password changed successfully.",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            } else {
                                                Toast.makeText(
                                                    requireContext(),
                                                    "Password changed failed.",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                                auth.signOut()
                                            }
                                        }
                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        "Re-Authentication failed.",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                    }
                } else {
                    Toast.makeText(requireContext(), "Password mismatching", Toast.LENGTH_LONG)
                        .show()
                }
            } else {
                Toast.makeText(requireContext(), "Please enter all the fields.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}