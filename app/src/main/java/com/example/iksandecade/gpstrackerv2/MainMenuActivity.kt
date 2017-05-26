package com.example.iksandecade.gpstrackerv2

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main_menu.*

class MainMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        var token = FirebaseInstanceId.getInstance().token

        Log.d("TOKEN", token)

        btnAddFriend.setOnClickListener { _ -> startActivity(Intent(this, AddFriendActivity::class.java)) }
        btnListFriend.setOnClickListener { _ -> startActivity(Intent(this, ListFriendActivity::class.java)) }
    }
}
