package com.example.iksandecade.gpstrackerv2

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.util.Log

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.media.RingtoneManager



/**
 * Created by
 * Name         : Ihksan Sukmawan
 * Email        : iksandecade@gmail.com
 * Company      : Meridian.Id
 * Date         : 22/05/17
 * Project      : GPSTrackerv2
 */

class FcmMessageService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        Log.d("FCM", "From: " + remoteMessage!!.from)

        if (remoteMessage.notification != null) {
            Log.d("FCM", "Notif msg body:" + remoteMessage.notification.body!!)
        }

        if (remoteMessage.data.containsKey("post_id") && remoteMessage.data.containsKey("post_title")) {
            Log.d("Post_Id", remoteMessage.data["post_id"].toString())
            Log.d("Post_title", remoteMessage.data["post_title"].toString())
            val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val builder: NotificationCompat.Builder? =
                    NotificationCompat.Builder(this)
                            .setSmallIcon(R.mipmap.ic_launcher_round)
                            .setContentTitle(remoteMessage.data["post_title"].toString())
                            .setContentText("CUK")
                            .setSound(alarmSound)

            val notificationIntent: Intent = Intent(this, MainMenuActivity::class.java)
            val contentIntent: PendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            builder!!.setContentIntent(contentIntent)
            val manager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(0, builder.build())


        }
    }
}
