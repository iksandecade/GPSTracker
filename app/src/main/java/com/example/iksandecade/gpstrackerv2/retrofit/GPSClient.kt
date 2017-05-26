package com.example.iksandecade.gpstrackerv2.retrofit

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by
 * Name         : Ihksan Sukmawan
 * Email        : iksandecade@gmail.com
 * Company      : Meridian.Id
 * Date         : 23/05/17
 * Project      : GPSTrackerv2
 */

interface GPSClient {

    @FormUrlEncoded
    @POST("/")
    fun sendNotif(
            @Field("id") id: String?,
            @Field("message") message: String?,
            @Field("token") token: String?
    ): Call<ResultModel>
}
