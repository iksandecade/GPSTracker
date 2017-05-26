package com.example.iksandecade.gpstrackerv2

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * Created by
 * Name         : Ihksan Sukmawan
 * Email        : iksandecade@gmail.com
 * Company      : Meridian.Id
 * Date         : 18/05/17
 * Project      : GPSTrackerv2
 */

object SPGPSTracker {

    internal val AUTH_CODE = "AUTH_CODE"
    internal val IS_LOGIN = "IS_LOGIN"
    internal val USERNAME = "USERNAME"
    internal val EMAIL = "EMAIL"
    internal val TOKEN = "TOKEN"
    internal val NAMA = "NAMA"

    internal fun getSharedPreferences(context: Context?): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun setAuthCode(context: Context?, authCode: String) {
        getSharedPreferences(context).edit().putString(AUTH_CODE, authCode).commit()
    }

    fun getAuthCode(context: Context?): String {
        return getSharedPreferences(context).getString(AUTH_CODE, null)
    }

    fun setIsLogin(context: Context?, isLogin: Boolean?) {
        getSharedPreferences(context).edit().putBoolean(IS_LOGIN, isLogin!!).commit()
    }

    fun getIsLogin(context: Context?): Boolean {
        return getSharedPreferences(context).getBoolean(IS_LOGIN, false)
    }

    fun setUsername(context: Context?, username: String?) {
        getSharedPreferences(context).edit().putString(USERNAME, username).commit()
    }

    fun getUsername(context: Context?): String {
        return getSharedPreferences(context).getString(USERNAME, null)
    }

    fun setEmail(context: Context?, email: String?) {
        getSharedPreferences(context).edit().putString(EMAIL, email).commit()
    }

    fun getEmail(context: Context?): String {
        return getSharedPreferences(context).getString(EMAIL, null)
    }

    fun setToken(context: Context?, token: String?) {
        getSharedPreferences(context).edit().putString(TOKEN, token).commit()
    }

    fun getToken(context: Context?): String {
        return getSharedPreferences(context).getString(TOKEN, null)
    }

    fun setNama(context: Context?, nama: String?) {
        getSharedPreferences(context).edit().putString(NAMA, nama).commit()
    }

    fun getNama(context: Context?): String {
        return getSharedPreferences(context).getString(NAMA, null)
    }

}
