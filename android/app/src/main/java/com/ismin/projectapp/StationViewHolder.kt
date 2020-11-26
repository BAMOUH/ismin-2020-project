package com.ismin.projectapp

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var txvName = itemView.findViewById<TextView>(R.id.r_station_txv_name)
    var txvEbike: TextView = itemView.findViewById(R.id.r_station_txv_ebike)
    var txvMechanical: TextView = itemView.findViewById(R.id.r_station_txv_mechanical)
}