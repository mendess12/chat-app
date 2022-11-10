package com.example.socialmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.socialmedia.databinding.PhotoRecyclerRowBinding
import com.example.socialmedia.model.Post
import com.squareup.picasso.Picasso

class PostRecyclerAdapter (private val postList : ArrayList<Post>) : RecyclerView.Adapter<PostRecyclerAdapter.PostHolder>() {


    class PostHolder(val binding : PhotoRecyclerRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = PhotoRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostHolder(binding)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {

        holder.binding.recyclerEmailTextView.text = postList.get(position).email
        holder.binding.recyclerCommentTextView.text = postList.get(position).comment
        Picasso.get().load(postList.get(position).downloadUrl).into(holder.binding.recyclerImageView)
    }

    override fun getItemCount(): Int {
        return postList.size
    }
}