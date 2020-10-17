package com.thangtv.mynote.broadcast

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ClipDescription
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.thangtv.mynote.R

class MyNotificationHelper(base: Context) : ContextWrapper(base){
    companion object{
        const val channelID = "channelID"
        const val channelName = "channelName"

    }
    private var notificationManager: NotificationManager? = null

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createChannel()
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val channel = NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH)
        getManager()?.createNotificationChannel(channel)
    }

    fun getManager() : NotificationManager?{
        if(notificationManager == null){
            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }

        return notificationManager
    }

    fun getChannelNotification(title: String, description: String): NotificationCompat.Builder{
        return NotificationCompat.Builder(
            applicationContext, channelID
        )
            .setContentTitle(title)
            .setContentText(description)
            .setSmallIcon(R.mipmap.ic_launcher)
    }

}