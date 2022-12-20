package com.example.socialmedia.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
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

        binding.changePasswordScreenSaveButton.setOnClickListener {
            changePassword()
        }

        binding.backToProfileFragment.back.setOnClickListener {

            val action =
                ChangePasswordFragmentDirections.actionChangePasswordFragmentToProfileFragment()
            findNavController().navigate(action)
        }
    }

    fun changePassword() {


        if (binding.passwordChangePasswordScreenEditText.text!!.isNotEmpty() &&
            binding.passwordChangeNewPasswordScreenEditText.text!!.isNotEmpty() &&
            binding.passwordChangeRetypeNewPasswordScreenEditText.text!!.isNotEmpty()
        ) {

            if (binding.passwordChangeNewPasswordScreenEditText.text.toString()
                == binding.passwordChangeRetypeNewPasswordScreenEditText.text.toString()
            ) {

                val user = auth.currentUser
                if (user != null && user.email != null) {

                    val credential = EmailAuthProvider.getCredential(
                        user.email!!,
                        binding.passwordChangePasswordScreenEditText.text.toString()
                    )

                    user?.reauthenticate(credential)?.addOnCompleteListener {

                        if (it.isSuccessful) {
                            Toast.makeText(
                                activity,
                                "Re-Authentication success",
                                Toast.LENGTH_LONG
                            ).show()

                            user?.updatePassword(binding.passwordChangeNewPasswordScreenEditText.text.toString())
                                ?.addOnCompleteListener {

                                    if (it.isSuccessful) {
                                        Toast.makeText(
                                            activity,
                                            "Password changed successfully",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        auth.signOut()
                                        val action =
                                            ChangePasswordFragmentDirections.actionChangePasswordFragmentToLoginFragment()
                                        findNavController().navigate(action)
                                    }
                                }

                        } else {
                            Toast.makeText(
                                activity,
                                "Re-Authentication failed",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    }

                } else {
                    val action =
                        ChangePasswordFragmentDirections.actionChangePasswordFragmentToLoginFragment()
                    findNavController().navigate(action)
                }

            } else {
                Toast.makeText(activity, "Password mismatching.", Toast.LENGTH_LONG).show()
            }

        } else {
            Toast.makeText(activity, "Please enter all the fields.", Toast.LENGTH_LONG).show()
        }
    }
}