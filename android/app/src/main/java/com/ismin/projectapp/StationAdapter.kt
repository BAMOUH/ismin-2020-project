package com.ismin.projectapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class StationAdapter(private val stations: ArrayList<Station>) : RecyclerView.Adapter<StationViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        val row = LayoutInflater.from(parent.context).inflate(R.layout.row_station, parent, false)
        return StationViewHolder(row)
    }

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        val (station_id, stationCode, name, lon, lat, mechanical, ebike, numDocksAvailable, last_reported, lastUpdatedOther)= this.stations[position]

        holder.txvName.text = name
        holder.txvEbike.text = ebike.toString()
        holder.txvMechanical.text = mechanical.toString()
    }

    override fun getItemCount(): Int {
        return this.stations.size
    }

    fun updateItem(stationsToDisplay: List<Station>) {
        stations.clear();
        stations.addAll(stationsToDisplay)
        notifyDataSetChanged();
    }

}