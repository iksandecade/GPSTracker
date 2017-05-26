package com.example.iksandecade.gpstrackerv2

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by
 * Name         : Ihksan Sukmawan
 * Email        : iksandecade@gmail.com
 * Company      : Meridian.Id
 * Date         : 24/05/17
 * Project      : GPSTrackerv2
 */

class ListFriendRecyclerAdapter(internal var friendModels: List<FriendModel>) : RecyclerView.Adapter<ListFriendRecyclerAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_friend, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val nama = friendModels[position].userName
        holder.tvName.text = nama
    }

    override fun getItemCount(): Int {
        return friendModels.size
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var tvName: TextView

        init {
            tvName = itemView.findViewById(R.id.tvName) as TextView
        }
    }
}
