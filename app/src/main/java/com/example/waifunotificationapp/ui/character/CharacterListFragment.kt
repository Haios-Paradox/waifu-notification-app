package com.example.waifunotificationapp.ui.character

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.waifunotificationapp.R
import com.example.waifunotificationapp.databinding.FragmentCharacterListBinding
import com.example.waifunotificationapp.databinding.ListCharacterBinding
import com.example.waifunotificationapp.model.Message
import com.example.waifunotificationapp.model.Waifu
import com.example.waifunotificationapp.ui.message.MessageAdapter

class CharacterListFragment : Fragment() {
    lateinit var binding: FragmentCharacterListBinding
    lateinit var charViewModel: CharViewModel
    lateinit var adapter: CharacterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCharacterListBinding.inflate(layoutInflater, container, false)
        charViewModel = ViewModelProvider(requireActivity())[CharViewModel::class.java]
        // Inflate the layout for this fragment
        charViewModel.waifus.observe(requireActivity()){
            adapter = CharacterAdapter(it)
            adapter.setOnItemClickCallback(object: CharacterAdapter.OnItemClickCallback{
                override fun onItemClicked(data: Waifu) {
                    //just navigate
                }
            })
            binding.rvCharacter.layoutManager = GridLayoutManager(requireContext(),2)
            binding.rvCharacter.adapter = adapter

            //HUMU!!!
        }
        return binding.root
    }

}