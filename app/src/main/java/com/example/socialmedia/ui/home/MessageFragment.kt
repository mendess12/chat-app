package com.example.socialmedia.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.socialmedia.R
import com.example.socialmedia.adapter.MessageRecyclerAdapter
import com.example.socialmedia.databinding.FragmentMessageBinding
import com.example.socialmedia.util.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MessageFragment : Fragment() {

    private lateinit var messageRecyclerAdapter: MessageRecyclerAdapter
    private lateinit var userList: ArrayList<User>
    private lateinit var binding: FragmentMessageBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMessageBinding.bind(view)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        userList = ArrayList<User>()
        getUsersData()

        binding.messageRecyclerView.layoutManager = LinearLayoutManager(activity)
        messageRecyclerAdapter = MessageRecyclerAdapter(userList)
        binding.messageRecyclerView.adapter = messageRecyclerAdapter


        binding.backToHomeFragment.back.setOnClickListener {

            val action = MessageFragmentDirections.actionMessageFragmentToHomeFragment()
            findNavController().navigate(action)
        }

    }

    fun getUsersData() {

        firestore.collection("Users").get().addOnSuccessListener { documents ->

            if (documents != null) {
                for (document in documents) {
                    var userName = document.get("userName") as String
                    var userEmail = document.get("userEmail") as String
                    var uid = document.get("uid") as String

                    val user = User(userName, userEmail, uid)
                    userList.add(user)
                }
                messageRecyclerAdapter.notifyDataSetChanged()
            }else{
                Toast.makeText(activity,"Documents are empty",Toast.LENGTH_LONG).show()
            }
        }


    }


}