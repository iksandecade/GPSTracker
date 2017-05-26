package com.example.iksandecade.gpstrackerv2.retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by
 * Name         : Ihksan Sukmawan
 * Email        : iksandecade@gmail.com
 * Company      : Meridian.Id
 * Date         : 23/05/17
 * Project      : GPSTrackerv2
 */

class ResultModel {
    @SerializedName("result")
    @Expose
    var result: String? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
}
