package com.example.waifunotificationapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.waifunotificationapp.R
import com.example.waifunotificationapp.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var binding : FragmentRegisterBinding
    private lateinit var authViewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater,container,false)

        authViewModel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]
        authViewModel.errorMessage.observe(viewLifecycleOwner){
            //Error handling disini, buat toast aja
        }

        binding.buttonRegister.setOnClickListener {//Register Function
            val email = binding.editTextEmail.editText!!.text.toString()
            val password = binding.editTextPassword.editText!!.text.toString()
            val confpass = binding.editTextConfirmPassword.editText!!.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireActivity(),"Please input Password and Email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(password!=confpass){
                Toast.makeText(requireActivity(),"Password does not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            authViewModel.register(email, password)
        }
        return inflater.inflate(R.layout.fragment_register, container, false)
    }
}