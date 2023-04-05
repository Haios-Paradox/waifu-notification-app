package com.example.waifunotificationapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.waifunotificationapp.model.Repository
import com.example.waifunotificationapp.ui.auth.AuthViewModel
import com.example.waifunotificationapp.ui.character.CharViewModel
import com.example.waifunotificationapp.ui.message.MessageViewModel
import com.example.waifunotificationapp.ui.todo.TaskViewModel

//If this project ever takes off...
/*
To the poor soul who will ever work on this code

I apologize

Blame Firebase for being too easy to use
 */

class ViewModelFactory(
    private val repository: Repository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(TaskViewModel::class.java) -> {
                TaskViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MessageViewModel::class.java) -> {
                MessageViewModel(repository) as T
            }
            modelClass.isAssignableFrom(CharViewModel::class.java) -> {
                CharViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                AuthViewModel(repository) as T
            }

            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}