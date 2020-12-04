package com.ismin.projectapp

import java.io.Serializable

data class Station(var station_id: Long, val stationCode: String, val name: String, val lon: Double, val lat: Double, val mechanical: Int, val ebike: Int, val numDocksAvailable: Int, val last_reported: Long, val lastUpdatedOther: Long ) : Serializable