package com.ismin.projectapp.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.ismin.projectapp.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class HomeFragment : Fragment() {

    //for database
    companion object{
        lateinit var dbHandler: DatabaseHandler
    }

    private val stationshelf = StationShelf()
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var stationService: StationService;

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)


        getNewStationsFromAPI(root)
        root.findViewById<FloatingActionButton>(R.id.refresh).setOnClickListener { view ->
            getNewStationsFromAPI(root)
            Snackbar.make(view, "Stations Refreshed", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()

        }
        return root
    }

    private fun getNewStationsFromAPI (root : View){
        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://app-5b336f60-7eb3-47be-aad4-06682834c6a6.cleverapps.io")//"http://10.0.2.2:3000/"
                .build()

        stationService = retrofit.create(StationService::class.java)

        stationService.getAllStations().enqueue(object : Callback<ArrayList<Station>> {
            override fun onResponse(
                    call: Call<ArrayList<Station>>,
                    response: Response<ArrayList<Station>>
            ) {
                val allStations = response.body()
                allStations?.forEach {
                    stationshelf.addStation(it)
                }


                displayListOnLine(root)
                addStationToDb(root, stationshelf.getAllStationsForDb())

            }

            override fun onFailure(call: Call<ArrayList<Station>>, t: Throwable) {
                displayErrorToast(t,root)

                //for data base to display off line
                displayListOffLine(root)

            }
        })
    }

    //for database
    private fun getStationsFromDb(root: View): ArrayList<Station>{
        val stationsList = dbHandler.getStationOnDB(root.context)
        return stationsList
    }

    //for database
    private fun addStationToDb(root: View, stationsList: ArrayList<Station>){
        stationsList.forEach {
            dbHandler.deleteStation(it.stationCode)
            dbHandler.addStationOnDB(root.context, it)
        }

    }



    fun displayListOnLine(root:View) {
        //for database
        dbHandler = DatabaseHandler(root.context, null, null, 1)


        val stationListFragment = StationListFragment.newInstance(stationshelf.getAllStations())

            activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.a_main_lyt_container, stationListFragment)
            ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ?.commit()
    }

    fun displayListOffLine(root:View){
        //for database
        dbHandler = DatabaseHandler(root.context, null, null, 1)
        //dbHandler.deleteStation("42027") //test remove
        var dbStations: ArrayList<Station> = getStationsFromDb(root)

        val stationListFragment = StationListFragment.newInstance(dbStations)

        activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.a_main_lyt_container, stationListFragment)
                ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                ?.commit()

    }

    fun displayErrorToast(t: Throwable,root:View){
        Snackbar.make(root, "Network error ${t.localizedMessage}", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }
}