package com.ismin.projectapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ismin.projectapp.R
import com.ismin.projectapp.Station
import com.ismin.projectapp.StationListFragment
import com.ismin.projectapp.StationShelf


class HomeFragment : Fragment() {

    private val stationshelf = StationShelf()
    private lateinit var homeViewModel: HomeViewModel

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
        displayList();
        return root
    }

    //hey bachir script
    fun displayList() {
        val stationListFragment = StationListFragment.newInstance(stationshelf.getAllStations())

            activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.a_main_lyt_container, stationListFragment)
            ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            ?.commit()
    }
}