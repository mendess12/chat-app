package com.example.socialmedia.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.socialmedia.databinding.ChatRecyclerRowBinding
import com.example.socialmedia.util.User

class MessageRecyclerAdapter( var userList: ArrayList<User>) :
    RecyclerView.Adapter<MessageRecyclerAdapter.MessageViewHolder>() {

    class MessageViewHolder(val binding: ChatRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {

        val binding = ChatRecyclerRowBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
       holder.binding.recyclerUserName.text = userList.get(position).name
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}