package com.example.waifunotificationapp.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.waifunotificationapp.R
import com.example.waifunotificationapp.databinding.ActivityAuthBinding
import com.example.waifunotificationapp.model.Repository
import com.example.waifunotificationapp.ui.MainActivity
import com.example.waifunotificationapp.ui.ViewModelFactory

class AuthActivity : AppCompatActivity() {

    private lateinit var authViewModel: AuthViewModel
    private lateinit var binding : ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)

        val repository = Repository()

        authViewModel = ViewModelProvider(this,
            ViewModelFactory(repository))[AuthViewModel::class.java]

        authViewModel.loggedInUser.observe(this){
            if(it==null)
                Navigation.findNavController(
                    this, binding.fragmentContainerView2.id
                ).navigate(R.id.loginFragment)
            else
                startActivity(
                    Intent(this, MainActivity::class.java).apply {
                        addFlags(
                            Intent.FLAG_ACTIVITY_CLEAR_TOP or
                                Intent.FLAG_ACTIVITY_NEW_TASK or
                                Intent.FLAG_ACTIVITY_CLEAR_TASK
                        )
                    }
                )
        }

        setContentView(binding.root)
    }
}