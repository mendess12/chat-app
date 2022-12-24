package com.example.socialmedia.ui.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socialmedia.R
import com.example.socialmedia.adapter.ChatRecyclerAdapter
import com.example.socialmedia.databinding.FragmentChatBinding
import com.example.socialmedia.model.Post
import com.example.socialmedia.util.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_chat.*

class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding
    private lateinit var chatRecyclerView: RecyclerView
    private val args: ChatFragmentArgs by navArgs()
    private lateinit var chatAdapter: ChatRecyclerAdapter
    private lateinit var chatList: ArrayList<Message>
    private lateinit var database : DatabaseReference

    var receiverRoom: String? = null
    var senderRoom: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChatBinding.bind(view)
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        senderRoom = args.uid + senderUid
        receiverRoom = senderUid + args.uid

        binding.backToMessageFragment.toolbarTextView.setText("${args.userName}")

        database = FirebaseDatabase.getInstance().getReference()

        chatRecyclerView = binding.chatRecyclerView

        Toast.makeText(requireContext(), "Name : ${args.userName}", Toast.LENGTH_LONG).show()
        chatList = ArrayList()
        chatAdapter = ChatRecyclerAdapter(requireContext(), chatList)
        chatRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        chatRecyclerView.adapter = chatAdapter


        database.child("chats").child(senderRoom!!).child("messages").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               chatList.clear()

                for (postSnapshot in snapshot.children){
                    val message = postSnapshot.getValue(Message::class.java)
                    chatList.add(message!!)
                }
                chatAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })



        binding.sendMessageButton.setOnClickListener {
            val message = binding.messageBox.text.toString()
            val messageObject = Message(message, senderUid)

            database.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {

                    database.child("chats").child(receiverRoom!!).child("messages").push().setValue(messageObject)
                }

            binding.messageBox.setText("")
        }

        binding.backToMessageFragment.back.setOnClickListener {

            val action = ChatFragmentDirections.actionChatFragmentToMessageFragment()
            findNavController().navigate(action)
        }

    }
}