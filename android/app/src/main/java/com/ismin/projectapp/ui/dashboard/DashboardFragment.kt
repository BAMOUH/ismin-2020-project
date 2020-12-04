package com.ismin.projectapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.ismin.projectapp.*
import com.ismin.projectapp.ui.home.HomeFragment

class DashboardFragment : Fragment() {
    companion object{
        lateinit var dbHandler: DatabaseHandler
    }
    private lateinit var dashboardViewModel: DashboardViewModel


    private val stationshelf = StationShelf()
    private lateinit var stationService: StationService;
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)


        getFavListFromDB(root,dashboardViewModel)
        return root
    }

    private fun getFavListFromDB(root: View,dashboardViewModel : DashboardViewModel) {
        //for database
        dbHandler = DatabaseHandler(root.context, null, null, 1)
        //dbHandler.deleteStation("42027") //test remove
        var dbStations: ArrayList<Station> = getStationsFromDb(root)

        if (dbStations.size > 0){
            val stationListFragment = StationListFragment.newInstance(dbStations)
            val textView: TextView = root.findViewById(R.id.text_notifications)
            dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
                textView.text = ""
            })
            activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.a_main_lyt_container, stationListFragment)
                    ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    ?.commit()
        }else{
            val textView: TextView = root.findViewById(R.id.text_notifications)
            dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
                textView.text = "No favorite stations found!"
            })

        }


    }
    private fun getStationsFromDb(root: View): ArrayList<Station>{
        val stationsList = dbHandler.getFavoriteOnDB(root.context)
        return stationsList
    }
}