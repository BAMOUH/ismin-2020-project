package com.ismin.projectapp

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface StationService {

    @GET("stations")
    fun getAllStations(): Call<ArrayList<Station>>

    @GET("stations/:stationId")
    fun getStationById(@Body() stationId: Int): Call<Station>
}