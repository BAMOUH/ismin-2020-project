package com.ismin.projectapp
import java.io.Serializable

class StationShelf : Serializable{
    private val storage = HashMap< Int, Station>()

    fun addStation(station: Station){
        this.storage[station.station_id] = station
    }

    fun getStation(station_id: Int): Station? {
        return this.storage[station_id]
    }

    fun getAllStations(): List<Station> {
        return ArrayList(this.storage.values).sortedBy { station -> station.station_id }
    }


    fun getTotalNumberOfStations(): Int {
        return this.storage.size
    }

}

