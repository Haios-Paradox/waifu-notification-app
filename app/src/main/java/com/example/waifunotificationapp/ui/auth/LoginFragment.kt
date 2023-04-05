package com.example.waifunotificationapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.waifunotificationapp.R
import com.example.waifunotificationapp.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private lateinit var binding : FragmentLoginBinding
    private lateinit var authViewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        authViewModel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]

        authViewModel.errorMessage.observe(viewLifecycleOwner){
            Toast.makeText(requireActivity(),it, Toast.LENGTH_SHORT).show()
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.editTextEmail.editText!!.text.toString()
            val password = binding.editTextPassword.editText!!.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireActivity(),"Please Input Password and Email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            authViewModel.login(email, password)
        } //Login Function

        binding.register.setOnClickListener {
            findNavController().navigate(R.id.registerFragment)
        }
        // Inflate the layout for this fragment
        return binding.root
    }
}