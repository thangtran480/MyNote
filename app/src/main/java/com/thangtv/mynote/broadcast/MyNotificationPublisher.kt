package com.thangtv.mynote.broadcast

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log


@Suppress("DEPRECATION")
class MyNotificationPublisher : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return

        val title = if (intent.hasExtra("title")){
                    intent.getStringExtra("title")
                }else{
                    "Your alert"
                }
        val description = if(intent.hasExtra("description")){
                    intent.getStringExtra("description")
                }else{
                    "your notification"
                }

        Log.d("DEMO", "ALERT")
        val notificationHelper = MyNotificationHelper(context)
        val nb = notificationHelper.getChannelNotification(title!!, description!!)
        notificationHelper.getManager()?.notify(1, nb.build())
        val notification = nb.build()

        notification.defaults += Notification.DEFAULT_VIBRATE
        notification.defaults += Notification.DEFAULT_SOUND

    }

}