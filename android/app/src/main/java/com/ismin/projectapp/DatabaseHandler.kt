package com.ismin.projectapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.icu.util.UniversalTimeScale.toLong
import android.util.Log
import android.widget.Toast
import com.ismin.projectapp.Station

class DatabaseHandler(context: Context, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int): SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    companion object{
        private val DATABASE_NAME = "MyData.db"
        private val DATABASE_VERSION = 1

        val FAVORITES_TABLE_NAME = "Favorites"
        val STATIONS_TABLE_NAME = "Stations"
        val COLUMN_STATION_ID = "station_id"
        val COLUMN_STATIONCODE = "stationCode"
        val COLUMN_NAME = "name"
        val COLUMN_LON = "lon"
        val COLUMN_LAT = "lat"
        val COLUMN_MECHANICAL = "mechanical"
        val COLUMN_EBIKE = "ebike"
        val COLUMN_NUMDOCKSAVAILABLE = "numDocksAvailable"
        val COLUMN_LAST_REPORTED = "last_reported"
        val COLUMN_LASTUPDATEDOTHER = "lastUpdatedOther"

    }


    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_STATIONS_TABLE = ("CREATE TABLE $STATIONS_TABLE_NAME (" +
                "$COLUMN_STATION_ID INTEGER PRIMARY KEY," +
                "$COLUMN_STATIONCODE TEXT," +
                "$COLUMN_NAME TEXT," +
                "$COLUMN_LON DOUBLE," +
                "$COLUMN_LAT DOUBLE," +
                "$COLUMN_MECHANICAL INTEGER," +
                "$COLUMN_EBIKE INTEGER," +
                "$COLUMN_NUMDOCKSAVAILABLE INTEGER," +
                "$COLUMN_LAST_REPORTED INTEGER," +
                "$COLUMN_LASTUPDATEDOTHER INTEGER )" )

        val CREATE_FAVORITES_TABLE = ("CREATE TABLE $FAVORITES_TABLE_NAME (" +
                "$COLUMN_STATION_ID INTEGER PRIMARY KEY," +
                "$COLUMN_STATIONCODE TEXT," +
                "$COLUMN_NAME TEXT," +
                "$COLUMN_LON DOUBLE," +
                "$COLUMN_LAT DOUBLE," +
                "$COLUMN_MECHANICAL INTEGER," +
                "$COLUMN_EBIKE INTEGER," +
                "$COLUMN_NUMDOCKSAVAILABLE INTEGER," +
                "$COLUMN_LAST_REPORTED INTEGER," +
                "$COLUMN_LASTUPDATEDOTHER INTEGER )" )

        db?.execSQL(CREATE_FAVORITES_TABLE)
        db?.execSQL(CREATE_STATIONS_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun getStationOnDB(mCtx: Context) : ArrayList<Station>{
        val qry = "Select * From $STATIONS_TABLE_NAME"
        val db = this.readableDatabase
        val cursor = db.rawQuery(qry, null)
        val stations = ArrayList<Station>()

        if(cursor.count == 0)
           Toast.makeText(mCtx, "NO RECORD fOUND", Toast.LENGTH_SHORT).show()
        else{
            while (cursor.moveToNext()){
                val station = Station(cursor.getInt(cursor.getColumnIndex(COLUMN_STATION_ID)).toLong(),
                                        cursor.getString(cursor.getColumnIndex(COLUMN_STATIONCODE)),
                                        cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                                        cursor.getDouble(cursor.getColumnIndex(COLUMN_LON)),
                                        cursor.getDouble(cursor.getColumnIndex(COLUMN_LAT)),
                                        cursor.getInt(cursor.getColumnIndex(COLUMN_MECHANICAL)),
                                        cursor.getInt(cursor.getColumnIndex(COLUMN_EBIKE)),
                                        cursor.getInt(cursor.getColumnIndex(COLUMN_NUMDOCKSAVAILABLE)),
                                        cursor.getInt(cursor.getColumnIndex(COLUMN_LAST_REPORTED)).toLong(),
                                        cursor.getInt(cursor.getColumnIndex(COLUMN_LASTUPDATEDOTHER)).toLong() )
                stations.add(station)
            }

            Toast.makeText(mCtx, "${ cursor.count.toString() }RECORDs fOUND", Toast.LENGTH_SHORT).show()
        }
            cursor.close()
            db.close()
            return stations

    }

    fun addStationOnDB(mCtx: Context, station: Station){
        val values = ContentValues()
        values.put(COLUMN_STATION_ID, station.station_id.toInt())
        values.put(COLUMN_STATIONCODE, station.stationCode)
        values.put(COLUMN_NAME, station.name)
        values.put(COLUMN_LON, station.lon)
        values.put(COLUMN_LAT, station.lat)
        values.put(COLUMN_MECHANICAL, station.mechanical)
        values.put(COLUMN_EBIKE, station.ebike)
        values.put(COLUMN_NUMDOCKSAVAILABLE, station.numDocksAvailable)
        values.put(COLUMN_LAST_REPORTED, station.last_reported.toInt())
        values.put(COLUMN_LASTUPDATEDOTHER, station.lastUpdatedOther.toInt())
        
        val db = this.writableDatabase
        try {
            db.insert(STATIONS_TABLE_NAME, null, values)
            //db.rawQuery()
            Toast.makeText(mCtx, "station added", Toast.LENGTH_SHORT ).show()
        } catch(e: Exception){
            Toast.makeText(mCtx, e.message, Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

        fun isStatationInFav (stationCode : String) : Boolean{
            val qry = "Select * From $FAVORITES_TABLE_NAME" +
                        " WHERE " + COLUMN_STATIONCODE + " = " + stationCode
            val db = this.readableDatabase
            val cursor = db.rawQuery(qry, null)

            val result: Boolean = cursor.count == 0
            Log.d("data base query2222:", result.toString())
            cursor.close()
            db.close()
            return result
        }
    fun getFavoriteOnDB(mCtx: Context) : ArrayList<Station>{
        val qry = "Select * From $FAVORITES_TABLE_NAME"
        val db = this.readableDatabase
        val cursor = db.rawQuery(qry, null)
        val stations = ArrayList<Station>()

        if(cursor.count == 0)
            Toast.makeText(mCtx, "NO RECORD fOUND", Toast.LENGTH_SHORT).show()
        else{
            while (cursor.moveToNext()){
                val station = Station(cursor.getInt(cursor.getColumnIndex(COLUMN_STATION_ID)).toLong(),
                        cursor.getString(cursor.getColumnIndex(COLUMN_STATIONCODE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                        cursor.getDouble(cursor.getColumnIndex(COLUMN_LON)),
                        cursor.getDouble(cursor.getColumnIndex(COLUMN_LAT)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_MECHANICAL)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_EBIKE)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_NUMDOCKSAVAILABLE)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_LAST_REPORTED)).toLong(),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_LASTUPDATEDOTHER)).toLong() )
                stations.add(station)
            }

            Toast.makeText(mCtx, "${ cursor.count.toString() }RECORDS fOUND", Toast.LENGTH_SHORT).show()
        }
        cursor.close()
        db.close()
        return stations

    }


    fun addFavoriteOnDB(mCtx: Context, station: Station){
        val values = ContentValues()
        values.put(COLUMN_STATION_ID, station.station_id.toInt())
        values.put(COLUMN_STATIONCODE, station.stationCode)
        values.put(COLUMN_NAME, station.name)
        values.put(COLUMN_LON, station.lon)
        values.put(COLUMN_LAT, station.lat)
        values.put(COLUMN_MECHANICAL, station.mechanical)
        values.put(COLUMN_EBIKE, station.ebike)
        values.put(COLUMN_NUMDOCKSAVAILABLE, station.numDocksAvailable)
        values.put(COLUMN_LAST_REPORTED, station.last_reported.toInt())
        values.put(COLUMN_LASTUPDATEDOTHER, station.lastUpdatedOther.toInt())

        val db = this.writableDatabase
        try {
            db.insert(FAVORITES_TABLE_NAME, null, values)
            Toast.makeText(mCtx, "station added", Toast.LENGTH_SHORT ).show()
        } catch(e: Exception){
            Toast.makeText(mCtx, e.message, Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

    fun deleteFavorite(code: String){
        val db = this.writableDatabase
        val qry = "DELETE FROM " + FAVORITES_TABLE_NAME +
                    " WHERE " + COLUMN_STATIONCODE + " = " + code
        db.execSQL(qry)
        db.close()
    }

    fun deleteStation(code: String){
        val db = this.writableDatabase
        val qry = "DELETE FROM " + STATIONS_TABLE_NAME +
                " WHERE " + COLUMN_STATIONCODE + " = " + code
        db.execSQL(qry)
        db.close()
    }




//Station(val station_id: Long, val stationCode: String, val name: String, val lon: Double, val lat: Double, val mechanical: Int, val ebike: Int, val numDocksAvailable: Int, val last_reported: Long, val lastUpdatedOther: Long )
}