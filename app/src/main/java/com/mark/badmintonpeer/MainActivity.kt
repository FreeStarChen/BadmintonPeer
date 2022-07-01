package com.mark.badmintonpeer

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mark.badmintonpeer.creategroup.CreateGroupFragment
import com.mark.badmintonpeer.databinding.ActivityMainBinding
import com.mark.badmintonpeer.ext.getVmFactory
import com.mark.badmintonpeer.group.GroupTypeViewModel
import com.mark.badmintonpeer.util.CurrentFragmentType
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    val viewModel by viewModels<MainViewModel> { getVmFactory() }

    lateinit var binding: ActivityMainBinding
//
//    private var locationPermissionGranted = false
//
//    private lateinit var mContext: Context
//    private var googleMap: GoogleMap? = null
//    private lateinit var mLocationProviderClient: FusedLocationProviderClient
//
//    companion object {
//        const val REQUEST_LOCATION_PERMISSION = 1
//        const val REQUEST_ENABLE_GPS = 2
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

//        mContext = this
//        mLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

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

        binding.buttonCancel.setOnClickListener {
            findNavController(R.id.nav_host_fragment).navigateUp()
        }

        binding.buttonCreateGroup.setOnClickListener {
            val fragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
            val createGroupFragment =
                fragment?.childFragmentManager?.primaryNavigationFragment as CreateGroupFragment
            createGroupFragment.callViewModelAddGroupResult()

//            childFragment.callViewModelAddGroupResult()
            Timber.d("childFragment=$createGroupFragment")

//            val createGroupViewModel = ViewModelProvider(
//                this,
//                ViewModelProvider.AndroidViewModelFactory.getInstance(application)
//            )[CreateGroupViewModel::class.java]
//
//            createGroupViewModel.addGroupResult()

            findNavController(R.id.nav_host_fragment).navigateUp()
//            findNavController(R.id.nav_host_fragment).navigate(NavigationDirections.navigateToGroupFragment())
        }

        binding.switchMap.setOnCheckedChangeListener { compoundButton, b ->
            compoundButton.isChecked.let {
                viewModel.switchStatus.value = it
//                getLocationPermission()

//                val groupTypeViewModel by viewModels<GroupTypeViewModel> { getVmFactory(viewModel.type.value!!) }
//                Timber.d("viewModel.type.value=${viewModel.type.value}")
//                groupTypeViewModel._recyclerViewVisible.value = it
//                val fragment =
//                    supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
//                val groupTypeFragment = fragment?.childFragmentManager?.primaryNavigationFragment as GroupTypeFragment
//                groupTypeFragment.setRecyclerViewVisible(it)
            }
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