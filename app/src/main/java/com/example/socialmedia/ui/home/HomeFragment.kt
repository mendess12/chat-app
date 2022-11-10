package com.example.socialmedia.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.socialmedia.R
import com.example.socialmedia.adapter.PostRecyclerAdapter
import com.example.socialmedia.databinding.FragmentHomeBinding
import com.example.socialmedia.model.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var postArrayList : ArrayList<Post>
    private lateinit var postRecyclerAdapter : PostRecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        auth = Firebase.auth
        firestore = Firebase.firestore

        postArrayList = ArrayList<Post>()

        getData()

        binding.postRecyclerView.layoutManager = LinearLayoutManager(activity)
        postRecyclerAdapter = PostRecyclerAdapter(postArrayList)
        binding.postRecyclerView.adapter = postRecyclerAdapter

    }

    private fun getData() {
        firestore.collection("Posts").addSnapshotListener { value, error ->
            if (error != null) {
                Toast.makeText(requireContext(), error.localizedMessage, Toast.LENGTH_LONG).show()
            } else {
                if (value != null) {
                    if (!value.isEmpty) {

                        val documents = value.documents
                        for (document in documents) {
                            //casting
                            val userEmail = document.get("userEmail") as String
                            val comment = document.get("comment") as String
                            val downloadUrl = document.get("downloadUrl") as String

                            val post = Post(userEmail,comment,downloadUrl)
                            postArrayList.add(post)
                        }
                        postRecyclerAdapter.notifyDataSetChanged()

                    }
                }
            }
        }
    }

}