package com.example.waifunotificationapp.ui.character

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waifunotificationapp.model.Repository
import com.example.waifunotificationapp.model.User
import com.example.waifunotificationapp.model.Waifu
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.launch

class CharViewModel(val repository: Repository) : ViewModel() {

    val userId = repository.uid

    private val _waifus = MutableLiveData<List<Waifu>>()
    val waifus : LiveData<List<Waifu>> = _waifus

    private val _user = MutableLiveData<User>()
    val user : LiveData<User> = _user

    init{
        getHarem()
    }

    fun getHarem(){
        repository.getAllWaifus(
            onSuccess = { waifus ->
                _waifus.value = waifus.map{
                    it.toObject()!!
                }
            },
            onFailure = {
                //Do Nothing idc
            }
        )


    }

    fun getUser(){
        repository.getUserData(
            onSuccess = {
                _user.value = it.toObject()
            },
            onFailure = {
                //do nothing i guess
            }
        )
    }

    fun createWaifu(waifu: Waifu){
        repository.createWaifu(waifu)
    }

//    fun updateWaifu(waifu: Waifu){
//        repository.updateWaifu(waifu)
//    }
//
//    fun deleteWaifu(waifu: Waifu){
//        repository.deleteWaifu(waifu)
//    }

}