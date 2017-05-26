package com.example.iksandecade.gpstrackerv2

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.iksandecade.gpstrackerv2.retrofit.GPSClient
import com.example.iksandecade.gpstrackerv2.retrofit.ResultModel
import com.example.iksandecade.gpstrackerv2.retrofit.ServiceGenerator
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_add_friend.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddFriendActivity : AppCompatActivity() {

    var databaseReference: DatabaseReference? = null
    var context: Context? = null
    var gpsClient: GPSClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_friend)
        context = this

        databaseReference = FirebaseDatabase.getInstance().reference
        gpsClient = ServiceGenerator.createService(GPSClient::class.java)

        btnAdd.setOnClickListener { _ ->
            run {
                val username = etUsername.text.toString()
                databaseReference!!.child("token").child(username).addListenerForSingleValueEvent(
                        object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    val tokenModel: TokenModel = dataSnapshot.getValue(TokenModel::class.java)
                                    databaseReference!!.child("friends").child(SPGPSTracker.getAuthCode(context)).child(tokenModel.userId).addListenerForSingleValueEvent(object : ValueEventListener {
                                        override fun onCancelled(p0: DatabaseError?) {
                                            Log.w("Nggeeeenggg", "getUser:onCancelled", p0!!.toException())
                                        }

                                        override fun onDataChange(p0: DataSnapshot?) {
                                            if (!p0!!.exists()) {
                                                val sendNotif: Call<ResultModel> = gpsClient!!.sendNotif("123123", "You have friend request", tokenModel.token)
                                                sendNotif.enqueue(object : Callback<ResultModel> {
                                                    override fun onResponse(call: Call<ResultModel>?, response: Response<ResultModel>?) {
                                                        if (response!!.isSuccessful) {
                                                            val result = response.body().result
                                                            val message = response.body().message
                                                            if (result.equals("success")) {
                                                                val friendModel: FriendModel = FriendModel()
                                                                friendModel.userId = tokenModel.userId
                                                                friendModel.userName = username
                                                                friendModel.status = true
                                                                databaseReference!!.child("friends").child(SPGPSTracker.getAuthCode(context)).child(tokenModel.userId).setValue(friendModel)
                                                                friendModel.userId = SPGPSTracker.getAuthCode(context)
                                                                friendModel.userName = SPGPSTracker.getUsername(context)
                                                                friendModel.status = false
                                                                databaseReference!!.child("friends").child(tokenModel.userId).child(SPGPSTracker.getAuthCode(context)).setValue(friendModel)
                                                            } else {
                                                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                                            }
                                                        }
                                                    }

                                                    override fun onFailure(call: Call<ResultModel>?, t: Throwable?) {
                                                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                                    }

                                                })
                                            } else {
                                                Toast.makeText(context, "Sudah jadi teman", Toast.LENGTH_SHORT).show()
                                            }
                                        }

                                    })

                                } else {
                                    Toast.makeText(context, "User tidak ditemukan", Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                Log.w("Nggeeeenggg", "getUser:onCancelled", databaseError.toException())
                            }
                        })

            }
        }
    }

}
