package com.example.waifunotificationapp.ui.character

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.waifunotificationapp.databinding.ListCharacterBinding
import com.example.waifunotificationapp.databinding.ListMessageBinding
import com.example.waifunotificationapp.model.Message
import com.example.waifunotificationapp.model.Waifu
import com.example.waifunotificationapp.ui.message.MessageAdapter
import com.example.waifunotificationapp.ui.message.ViewHolderMessage

class CharacterAdapter (private val character:List<Waifu>) : RecyclerView.Adapter<ViewHolderCharacter>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCharacter {
        val binding = ListCharacterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolderCharacter(binding)
    }

    override fun getItemCount(): Int {
        return character.size
    }

    override fun onBindViewHolder(holder: ViewHolderCharacter, position: Int) {
        with(holder.binding){
            tvListcharDescription.text = character[position].background
            tvListcharName.text = character[position].name
            Glide.with(ivListChar).load(character[position].waifuAvatar).into(ivListChar)
        }
    }
    interface OnItemClickCallback{
        fun onItemClicked(data: Waifu)
    }
}

class ViewHolderCharacter(
    val binding : ListCharacterBinding
): RecyclerView.ViewHolder(binding.root)
