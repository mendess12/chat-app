package com.example.socialmedia.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.socialmedia.R
import com.example.socialmedia.databinding.FragmentProfileBinding
import com.example.socialmedia.ui.user.LoginFragment
import com.google.firebase.auth.FirebaseAuth


class ProfileFragment : Fragment() {

    private lateinit var auth : FirebaseAuth
    private lateinit var binding: FragmentProfileBinding

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

        binding.profileScreenSignOutTextView.setOnClickListener {


                auth.signOut()
                val intent = Intent(requireContext(),MainActivity2::class.java)
                startActivity(intent)


        }

    }

}