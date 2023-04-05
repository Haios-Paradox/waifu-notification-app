package com.example.waifunotificationapp.model.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.example.waifunotificationapp.R
import com.example.waifunotificationapp.model.*
import java.time.Duration
import java.time.LocalTime
import java.util.concurrent.TimeUnit

class PromptWorker(val context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        // Select a random message from the "messages" collection
        val repository = Repository()
        repository.promptRandomCharacterWithTask()
        return Result.success()
    }
}
