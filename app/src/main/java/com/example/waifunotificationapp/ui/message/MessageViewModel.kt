package com.example.waifunotificationapp.ui.message

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waifunotificationapp.model.Message
import com.example.waifunotificationapp.model.Repository
import com.example.waifunotificationapp.model.Task
import com.example.waifunotificationapp.model.WaifuMessage
import kotlinx.coroutines.launch

class MessageViewModel(val repository: Repository): ViewModel() {

    private val _messages = MutableLiveData<List<WaifuMessage>>()
    val messages : LiveData<List<WaifuMessage>> = _messages

    fun getMessage(){
        viewModelScope.launch {
            repository.getAllMessages{
                _messages.value = it
            }

        }
    }

    fun sendMessage(task: Task?=null){
        repository.promptRandomCharacterWithTask()
    }


}