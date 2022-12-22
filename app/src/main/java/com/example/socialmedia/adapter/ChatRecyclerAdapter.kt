package com.example.socialmedia.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.socialmedia.R
import com.example.socialmedia.databinding.ReceiveBinding
import com.example.socialmedia.databinding.SentBinding
import com.example.socialmedia.util.Message
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.receive.view.*
import kotlinx.android.synthetic.main.sent.view.*

class ChatRecyclerAdapter(val context : Context,val chatList :ArrayList<Message>)  : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECEIVE = 1
    val ITEM_SENT = 2

    class SentViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val sentMessage = itemView.sentMessageTextView
    }
    class ReceiveViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val receiveMessage = itemView.receiveMessageTextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1){
            //inflate receive
            val view : View = LayoutInflater.from(context).inflate(R.layout.receive,parent,false)
            return ReceiveViewHolder(view)
        }else{
            //inflate sent
            val view : View = LayoutInflater.from(context).inflate(R.layout.sent,parent,false)
            return SentViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = chatList[position]
        if (holder.javaClass == SentViewHolder::class.java ){

            val viewHolder = holder as SentViewHolder
            holder.sentMessage.text = currentMessage.message

        }else{

            val viewHolder = holder as ReceiveViewHolder
            holder.receiveMessage.text = currentMessage.message
        }
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = chatList[position]

        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            return ITEM_SENT
        }else{
            return ITEM_RECEIVE
        }
    }

}