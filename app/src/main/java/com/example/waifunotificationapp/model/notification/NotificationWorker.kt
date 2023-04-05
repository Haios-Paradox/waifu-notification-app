package com.example.waifunotificationapp.model.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.*
import com.example.waifunotificationapp.R
import com.example.waifunotificationapp.model.Repository
import com.example.waifunotificationapp.model.WaifuMessage
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NotificationWorker(val context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        // Select a random message from the "messages" collection
        val repository = Repository()
        repository.getAllMessages{ messages ->
            // Show a notification with the random message
            for(message in messages){
                if(!message.notified) {
                    buildNotification(context, message)
                    message.notified = true
                }
            }
        }
        return Result.success()
    }



    fun buildNotification(context: Context, message: WaifuMessage) {
        val channelId = "my_channel_id"
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_message)
            .setContentTitle(message.waifu.name)
            .setContentText(message.message.title)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since Android Oreo (API 26), notification channels are required.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Waifu_Letter", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        // Show the notification
        notificationManager.notify(0, notificationBuilder.build())
    }
}
