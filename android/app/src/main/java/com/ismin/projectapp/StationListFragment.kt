package com.ismin.projectapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

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
        stationsToShow = stations.slice(0..offsetToScroll) as ArrayList<Station>
        val rootView = inflater.inflate(R.layout.fragment_station_list, container, false)

        this.rcvStations = rootView.findViewById(R.id.f_station_list_rcv_stations)
        this.rcvStations.adapter = StationAdapter(stationsToShow, this)//this refers to set action listener
        val linearLayoutManager = LinearLayoutManager(context)
        this.rcvStations.layoutManager = linearLayoutManager

        val dividerItemDecoration = DividerItemDecoration(context, linearLayoutManager.orientation)
        this.rcvStations.addItemDecoration(dividerItemDecoration)

        // Retain an instance so that you can call `resetState()` for fresh searches
        // Retain an instance so that you can call `resetState()` for fresh searches
        scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
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
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
        offsetToScroll = (offset+1) * 20
        stationsToShow = stations.slice(0..offsetToScroll) as ArrayList<Station>
        adapter.updateItem(stationsToShow)
//        Snackbar.make(root, "we did load more"+offsetToScroll.toString(), Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
    }
    override fun onStationClick(item: Station, position: Int) {
        Toast.makeText(this.context, "you clicked on item # ${position + 1}", Toast.LENGTH_SHORT).show()

        val intent = Intent(this.context, DetailsActivity::class.java)
        intent.putExtra("STATIONNAME", item.name)
        intent.putExtra("STATIONDESC", "Code de station: " + item.stationCode + "\nLongitude: " + item.lon + "\nLatitude: " + item.lat)
        startActivity(intent)
    }

}
