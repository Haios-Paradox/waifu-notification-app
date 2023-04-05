package com.example.waifunotificationapp.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waifunotificationapp.model.Repository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: Repository): ViewModel() {

    private val _loggedInUser = MutableLiveData<FirebaseUser?>()
    val loggedInUser: MutableLiveData<FirebaseUser?> = _loggedInUser

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    init{
        autoLogin()
    }

    fun autoLogin(): FirebaseUser? {
        _loggedInUser.value = repository.auth.currentUser
        return _loggedInUser.value
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val user = repository.login(email, password)
                _loggedInUser.value = user
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            try {
                val user = repository.register(email, password)
                _loggedInUser.value = user
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                repository.logout()
                _loggedInUser.value = null
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

}