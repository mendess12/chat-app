package com.example.socialmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.socialmedia.databinding.ChatRecyclerRowBinding
import com.example.socialmedia.ui.home.MessageFragmentDirections
import com.example.socialmedia.model.User

class MessageRecyclerAdapter( var userList: ArrayList<User>) :
    RecyclerView.Adapter<MessageRecyclerAdapter.MessageViewHolder>() {

    class MessageViewHolder(val binding: ChatRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {

        val binding = ChatRecyclerRowBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val currentUser = userList[position]

       holder.binding.recyclerUserName.text = userList.get(position).name

        holder.itemView.setOnClickListener {
            val action = MessageFragmentDirections.actionMessageFragmentToChatFragment(currentUser.uid!!,currentUser.name!!)
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}