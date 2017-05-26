package com.example.iksandecade.gpstrackerv2

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_list_friend.*

class ListFriendActivity : AppCompatActivity() {

    internal var listFriendRecyclerAdapter: ListFriendRecyclerAdapter? = null
    internal var databaseReference: DatabaseReference? = null

    var friendModels: MutableList<FriendModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_friend)

        listFriendRecyclerAdapter = ListFriendRecyclerAdapter(friendModels)
        databaseReference = FirebaseDatabase.getInstance().reference
        val layoutManager: RecyclerView.LayoutManager? = LinearLayoutManager(applicationContext)

        rvListFriend.layoutManager = layoutManager
        rvListFriend.adapter = listFriendRecyclerAdapter


        val postListener: ValueEventListener?= object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {
                Toast.makeText(applicationContext, "DR", Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(p0: DataSnapshot?) {
                if(p0!!.exists()){
                    p0.children
                            .map { it.getValue(FriendModel::class.java) }
                            .forEach { friendModels.add(it) }

                    listFriendRecyclerAdapter!!.notifyDataSetChanged()
                }
            }
        }

        databaseReference!!.child("friends").child(SPGPSTracker.getAuthCode(this)).addValueEventListener(postListener)



    }
}
