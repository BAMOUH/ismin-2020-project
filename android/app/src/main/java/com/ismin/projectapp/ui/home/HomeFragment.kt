package com.ismin.projectapp.ui.home

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
import com.google.android.material.snackbar.Snackbar
import com.ismin.projectapp.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeFragment : Fragment() {

    private val stationshelf = StationShelf()
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var stationService: StationService;

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val stationToAdd : Station =  Station(12121231, "132323", "Hooya", 344.23, 2333.2323, 2, 1, 32, 1321223232, 24343434);
        stationshelf.addStation(stationToAdd);

        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://bookshelf-gme.cleverapps.io")
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
                    Snackbar.make(root, "Hello", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                }
                displayList()

            }

            override fun onFailure(call: Call<ArrayList<Station>>, t: Throwable) {
                displayErrorToast(t,root)

            }
        })
        return root
    }

    fun displayList() {
        val stationListFragment = StationListFragment.newInstance(stationshelf.getAllStations())

            activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.a_main_lyt_container, stationListFragment)
            ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ?.commit()
    }

    fun displayErrorToast(t: Throwable,root:View){
//        Toast.makeText(
//            activity?.applicationContext,
//            "Network error ${t.localizedMessage}",
//            Toast.LENGTH_LONG
//        ).show()
        Snackbar.make(root, "Network error ${t.localizedMessage}", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }
}