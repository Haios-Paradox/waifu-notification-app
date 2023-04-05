package com.example.waifunotificationapp.ui.character

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.waifunotificationapp.R
import com.example.waifunotificationapp.databinding.FragmentCharacterListBinding

class CharacterDetailFragment : Fragment() {

    lateinit var binding: FragmentCharacterListBinding
    lateinit var charViewModel: CharViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCharacterListBinding.inflate(layoutInflater,container,false)
        // Inflate the layout for this fragment
        charViewModel = ViewModelProvider(requireActivity())[CharViewModel::class.java]
        charViewModel.waifus.observe(requireActivity()){

        }
        return binding.root
    }
}