package com.example.iksandecade.gpstrackerv2

import android.util.Log

import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

/**
 * Created by
 * Name         : Ihksan Sukmawan
 * Email        : iksandecade@gmail.com
 * Company      : Meridian.Id
 * Date         : 22/05/17
 * Project      : GPSTrackerv2
 */

class FCMInstanceIdService : FirebaseInstanceIdService() {

    override fun onTokenRefresh() {
        val token = FirebaseInstanceId.getInstance().token
        Log.d("TOKEN", token)

        sendFcmTokenToServer(token)
    }

    private fun sendFcmTokenToServer(token: String?) {

    }
}
