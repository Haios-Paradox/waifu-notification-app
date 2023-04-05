package com.example.waifunotificationapp.ui.message

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.waifunotificationapp.R
import com.example.waifunotificationapp.databinding.FragmentMessageListBinding
import com.example.waifunotificationapp.databinding.ListMessageBinding
import com.example.waifunotificationapp.model.Message

class MessageListFragment : Fragment() {

    private lateinit var binding : FragmentMessageListBinding
    lateinit var messageViewModel: MessageViewModel
    lateinit var adapter: MessageAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMessageListBinding.inflate(layoutInflater, container, false)
        messageViewModel = ViewModelProvider(requireActivity())[MessageViewModel::class.java]
        messageViewModel.messages.observe(requireActivity()){
            adapter = MessageAdapter(it)
            adapter.setOnItemClickCallback(object: MessageAdapter.OnItemClickCallback{
                override fun onItemClicked(data: Message) {
                    //just navigate
                }
            })
            binding.rvMessage.adapter = adapter
            binding.rvMessage.layoutManager = LinearLayoutManager(requireContext())
        }
        // Inflate the layout for this fragment
        return binding.root
    }
}