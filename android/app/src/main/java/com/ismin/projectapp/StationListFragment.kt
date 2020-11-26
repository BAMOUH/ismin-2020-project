package com.ismin.projectapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val ARG_STATIONS = "ARG_STATIONS"

class StationListFragment : Fragment(){

    private lateinit  var stations: ArrayList<Station>
    private lateinit var rcvStations: RecyclerView


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
        val rootView = inflater.inflate(R.layout.fragment_station_list, container, false)

        this.rcvStations = rootView.findViewById(R.id.f_station_list_rcv_stations)
        this.rcvStations.adapter = StationAdapter(stations)
        val linearLayoutManager = LinearLayoutManager(context)
        this.rcvStations.layoutManager = linearLayoutManager

        val dividerItemDecoration = DividerItemDecoration(context, linearLayoutManager.orientation)
        this.rcvStations.addItemDecoration(dividerItemDecoration)

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


}
