package com.mark.badmintonpeer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mark.badmintonpeer.databinding.ActivityMainBinding
import com.mark.badmintonpeer.ext.getVmFactory
import com.mark.badmintonpeer.util.CurrentFragmentType

class MainActivity : AppCompatActivity() {

    val viewModel by viewModels<MainViewModel> { getVmFactory() }

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        val bottomNavigationView: BottomNavigationView = binding.bottomNavigationView

        NavigationUI.setupWithNavController(bottomNavigationView, navController)

//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            when (destination.id) {
//                R.id.groupFragment -> {
//
//                }
//                R.id.chatroomFragment -> {
//
//                }
//                R.id.newsFragment -> {
//
//                }
//                R.id.profileFragment -> {
//
//                }
//            }
//        }

//        val list = listOf("1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1")
//
//        val adapter: ArrayAdapter<String> =
//        object : ArrayAdapter<String>(
//            this,android.R.layout.simple_spinner_item,list) {
//            override fun getDropDownView(
//                position: Int,
//                convertView: View?,
//                parent: ViewGroup
//            ): View? {
//
//                super.getDropDownView(position, convertView, parent)
//                convertView?.visibility = View.VISIBLE
//                convertView?.layoutParams?.height = 200
//                return convertView
//            }
//        }

        ArrayAdapter.createFromResource(
            this,
            R.array.cities,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            binding.spinnerCities.adapter = adapter

        }

        binding.spinnerCities.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 != 0) {
                    var selectedCity = binding.spinnerCities.selectedItem.toString()
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        binding.imageToolbarFilter.setOnClickListener {
            findNavController(R.id.nav_host_fragment).navigate(NavigationDirections.navigateToFilterFragment())
        }

        setupNavController()

    }

    /**
     * Set up [NavController.addOnDestinationChangedListener] to record the current fragment, it better than another design
     * which is change the [CurrentFragmentType] enum value by [MainViewModel] at [onCreateView]
     */
    private fun setupNavController() {
        findNavController(R.id.nav_host_fragment).addOnDestinationChangedListener { navController: NavController, _: NavDestination, _: Bundle? ->
            viewModel.currentFragmentType.value = when (navController.currentDestination?.id) {
                R.id.groupFragment -> CurrentFragmentType.GROUP
                R.id.chatroomFragment -> CurrentFragmentType.CHATROOM
                R.id.newsFragment -> CurrentFragmentType.NEWS
                R.id.profileFragment -> CurrentFragmentType.PROFILE
                R.id.filterFragment -> CurrentFragmentType.FILTER
                R.id.createGroupFragment -> CurrentFragmentType.CREATE
                R.id.groupDetailFragment -> CurrentFragmentType.DETAIL
                else -> viewModel.currentFragmentType.value
            }
        }
    }
}