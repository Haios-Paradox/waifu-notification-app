package com.example.waifunotificationapp.ui.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.waifunotificationapp.model.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class TaskViewModel(private val repository: Repository): ViewModel() {

//    private val userId = repository.uid
//
//    private val _tasks = MutableLiveData<List<Task>>()
//    val tasks : LiveData<List<Task>> = _tasks
//
//    fun getAllTasks(){
//        viewModelScope.launch {
//            if(userId!=null) {
//                _tasks.value = repository.getAllTask(userId)
//            }
//        }
//    }
//
//    fun createTask(task:Task){
//        repository.sendTask(task)
//    }
//
//    fun updateTask(task:Task){
//        repository.updateTask(task)
//    }
//
//    fun deleteTask(task:Task){
//        repository.deleteTask(task)
//    }



}