package com.example.waifunotificationapp.ui.message

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.waifunotificationapp.databinding.ListMessageBinding
import com.example.waifunotificationapp.model.Message
import com.example.waifunotificationapp.model.WaifuMessage

class MessageAdapter(private val messages: List<WaifuMessage>) :  RecyclerView.Adapter<ViewHolderMessage>(){

    private lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMessage {
        val binding = ListMessageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolderMessage(binding)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: ViewHolderMessage, position: Int) {
        with(holder.binding){
            Glide.with(imageView).load(messages[position].waifu.waifuAvatar).circleCrop().into(imageView)
            tvListmessageContent.text = messages[position].message.message
            tvListmessageTitle.text = messages[position].message.title
        }
    }

    interface OnItemClickCallback{
        fun onItemClicked(data:Message)
    }
}

class ViewHolderMessage(
    val binding : ListMessageBinding
): RecyclerView.ViewHolder(binding.root)
