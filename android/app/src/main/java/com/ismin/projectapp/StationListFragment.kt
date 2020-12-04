package com.ismin.projectapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ismin.projectapp.ui.home.HomeFragment
import kotlin.math.min

private const val ARG_STATIONS = "ARG_STATIONS"

class StationListFragment : Fragment(), OnStationListener{

    private lateinit  var stations: ArrayList<Station>
    private lateinit  var stationsToShow: ArrayList<Station>
    private lateinit var rcvStations: RecyclerView
    private var scrollListener: EndlessRecyclerViewScrollListener? = null
    private var offsetToScroll : Int = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            stations = it.getSerializable(ARG_STATIONS) as ArrayList<Station>
        }
    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if (stations.size > 1){
            stationsToShow = stations.slice(0..min(offsetToScroll ,stations.size -1)) as ArrayList<Station>

        }else {
            stationsToShow = stations
        }

        val rootView = inflater.inflate(R.layout.fragment_station_list, container, false)

        this.rcvStations = rootView.findViewById(R.id.f_station_list_rcv_stations)
        this.rcvStations.adapter = StationAdapter(stationsToShow, this)//this refers to set action listener
        val linearLayoutManager = LinearLayoutManager(context)
        this.rcvStations.layoutManager = linearLayoutManager

        val dividerItemDecoration = DividerItemDecoration(context, linearLayoutManager.orientation)
        this.rcvStations.addItemDecoration(dividerItemDecoration)

        scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                loadNextDataFromApi(page,rootView , rcvStations.adapter as StationAdapter)
            }
        }
        // Adds the scroll listener to RecyclerView
        this.rcvStations.addOnScrollListener(scrollListener as EndlessRecyclerViewScrollListener)
        return rootView;
    }


    companion object {
        @JvmStatic
        fun newInstance(stations: List<Station>) =
            StationListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_STATIONS, ArrayList(stations))
                }
            }
    }

    fun loadNextDataFromApi(offset: Int, root: View, adapter: StationAdapter) {
        offsetToScroll = (offset+1) * 20
        if (stations.size > 1){
            stationsToShow = stations.slice(0..min(offsetToScroll ,stations.size -1)) as ArrayList<Station>

        }else {
            stationsToShow = stations
        }
        adapter.updateItem(stationsToShow)
    }
    override fun onStationClick(item: Station, position: Int) {

        val intent = Intent(this.context, DetailsActivity::class.java)
        intent.putExtra("STATIONNAME", item.name)
        intent.putExtra("STATIONDESC", "ID de station: " + item.station_id.toString()  + "\nCode de station: " + item.stationCode + "\nLongitude: " + item.lon + "\nLatitude: " + item.lat)
        startActivity(intent)
    }

    override fun likeClick(item: Station, favBtn: Button) {
        context?.let {

            if (HomeFragment.dbHandler.isStatationInFav(item.stationCode) ){
                HomeFragment.dbHandler.addFavoriteOnDB(it, item)

            }else {
                HomeFragment.dbHandler.deleteFavorite(item.stationCode)

                val stationListFragment = StationListFragment.newInstance(stationsToShow)

                activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.a_main_lyt_container, stationListFragment)
                        ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        ?.commit()

            }



        }

    }

}
