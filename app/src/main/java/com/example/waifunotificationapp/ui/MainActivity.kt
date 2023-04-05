package com.example.waifunotificationapp.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.work.*
import com.example.waifunotificationapp.databinding.ActivityMainBinding
import com.example.waifunotificationapp.model.*
import com.example.waifunotificationapp.model.notification.NotificationWorker
import com.example.waifunotificationapp.ui.character.CharViewModel
import com.example.waifunotificationapp.ui.message.MessageViewModel
import java.time.Duration
import java.time.LocalTime
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var charViewModel: CharViewModel
    private lateinit var messageViewModel: MessageViewModel
    private var waifus:List<Waifu>? = null
    private var messages:List<WaifuMessage>?=null
     private var user:User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = Repository()

        binding = ActivityMainBinding.inflate(layoutInflater)
        charViewModel = ViewModelProvider(this,
            ViewModelFactory(repository))[CharViewModel::class.java]
        messageViewModel = ViewModelProvider(this,
            ViewModelFactory(repository))[MessageViewModel::class.java]


        setContentView(binding.root)
    }

    private fun scheduleNotification(){
        val workManager = WorkManager.getInstance(this)
        val now = Calendar.getInstance()
        val currentHour = now.get(Calendar.HOUR_OF_DAY)
        val random1 = currentHour + (1..11).random()
        val random2 = currentHour + (13..23).random()
        val request1 = PeriodicWorkRequestBuilder<NotificationWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(random1.toLong(), TimeUnit.HOURS)
            .build()

        val request2 = PeriodicWorkRequestBuilder<NotificationWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(random2.toLong(), TimeUnit.HOURS)
            .build()
        val workInfos = workManager.getWorkInfosForUniqueWork("dayrandomnotification").get()
        val hasDayWorker = workInfos.isNotEmpty() && workInfos.first().state == WorkInfo.State.RUNNING
        if(!hasDayWorker)
            workManager.enqueueUniquePeriodicWork("dayrandomnotification", ExistingPeriodicWorkPolicy.UPDATE, request1)

        val workInfos2 = workManager.getWorkInfosForUniqueWork("nightrandomnotification").get()
        val hasNightWorker = workInfos.isNotEmpty() && workInfos.first().state == WorkInfo.State.RUNNING
        if(!hasNightWorker)
            workManager.enqueueUniquePeriodicWork("nightrandomnotification", ExistingPeriodicWorkPolicy.UPDATE, request2)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun scheduleRequest() {
        val workManager = WorkManager.getInstance(this)

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        // Check if there is any existing "request" periodic work
        val workInfos = workManager.getWorkInfosForUniqueWork("request").get()
        val hasExistingWork = workInfos.isNotEmpty() && workInfos.first().state == WorkInfo.State.RUNNING

        if (!hasExistingWork) {
            val request = PeriodicWorkRequestBuilder<NotificationWorker>(12, TimeUnit.HOURS)
                .setConstraints(constraints)
                .setInitialDelay(getDelayUntilNext12AM(), TimeUnit.MILLISECONDS)
                .build()

            workManager.enqueueUniquePeriodicWork(
                "request",
                ExistingPeriodicWorkPolicy.UPDATE,
                request
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDelayUntilNext12AM(): Long {
        val midnight = LocalTime.MIDNIGHT
        val now = LocalTime.now()
        val delay = Duration.between(now, midnight).toMillis()

        return if (now.isAfter(midnight)) {
            // delay until next midnight
            delay + Duration.ofDays(1).toMillis()
        } else {
            delay
        }
    }


}