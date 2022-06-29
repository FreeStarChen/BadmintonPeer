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

//    private fun getLocationPermission() {
//        //檢查權限
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            //已獲取到權限
////            Toast.makeText(this, "已獲取到位置權限，可以準備開始獲取經緯度", Toast.LENGTH_SHORT).show()
//            locationPermissionGranted = true
//            checkGPSState()
//        } else {
//            //詢問要求獲取權限
//            requestLocationPermission()
//        }
//    }
//
//    private fun checkGPSState() {
//        val locationManager = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            AlertDialog.Builder(mContext)
//                .setTitle("GPS 尚未開啟")
//                .setMessage("使用此功能需要開啟 GSP 定位功能")
//                .setPositiveButton("前往開啟",
//                    DialogInterface.OnClickListener { _, _ ->
//                        startActivityForResult(
//                            Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), REQUEST_ENABLE_GPS
//                        )
//                    })
//                .setNegativeButton("取消", null)
//                .show()
//        } else {
//            Toast.makeText(this, "已獲取到位置權限且GPS已開啟，可以準備開始獲取經緯度", Toast.LENGTH_SHORT).show()
//            getDeviceLocation()
//        }
//    }
//
//    private fun getDeviceLocation() {
//        try {
//            if (locationPermissionGranted
//            ) {
//                val locationRequest = LocationRequest()
//                locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//                //更新頻率
//                locationRequest.interval = 1000
//                //更新次數，若沒設定，會持續更新
//                locationRequest.numUpdates = 1
//                mLocationProviderClient.requestLocationUpdates(
//                    locationRequest,
//                    object : LocationCallback() {
//                        override fun onLocationResult(locationResult: LocationResult?) {
//                            locationResult ?: return
//                            Timber.d("緯度:" + locationResult.lastLocation.latitude + " , 經度:" + locationResult.lastLocation.longitude + " ")
//
//                            val currentLocation =
//                                LatLng(
//                                    locationResult.lastLocation.latitude,
//                                    locationResult.lastLocation.longitude
//                                )
//                            googleMap?.addMarker(
//                                MarkerOptions().position(currentLocation).title("現在位置")
//                            )
//                            googleMap?.moveCamera(
//                                CameraUpdateFactory.newLatLngZoom(
//                                    currentLocation, 15f
//                                )
//                            )
//                        }
//                    },
//                    null
//                )
//
//            } else {
//                getLocationPermission()
//            }
//        } catch (e: SecurityException) {
//            Timber.e(e, e.message)
//        }
//    }
//
//    private fun requestLocationPermission() {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(
//                this, Manifest.permission.ACCESS_FINE_LOCATION
//            )
//        ) {
//            AlertDialog.Builder(this)
//                .setMessage("此應用程式，需要位置權限才能正常使用")
//                .setPositiveButton("確定") { _, _ ->
//                    ActivityCompat.requestPermissions(
//                        this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                        REQUEST_LOCATION_PERMISSION
//                    )
//                }
//                .setNegativeButton("取消") { _, _ -> requestLocationPermission() }
//                .show()
//        } else {
//            ActivityCompat.requestPermissions(
//                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION
//            )
//        }
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//
//        when (requestCode) {
//            REQUEST_LOCATION_PERMISSION -> {
//                if (grantResults.isNotEmpty()) {
//                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                        //已獲取到權限
//                        locationPermissionGranted = true
//                        checkGPSState()
//                    } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
//                        if (!ActivityCompat.shouldShowRequestPermissionRationale(
//                                this,
//                                Manifest.permission.ACCESS_FINE_LOCATION
//                            )
//                        ) {
//                            //權限被永久拒絕
//                            Toast.makeText(this, "位置權限已被關閉，功能將會無法正常使用", Toast.LENGTH_SHORT).show()
//
//                            AlertDialog.Builder(this)
//                                .setTitle("開啟位置權限")
//                                .setMessage("此應用程式，位置權限已被關閉，需開啟才能正常使用")
//                                .setPositiveButton("確定") { _, _ ->
//                                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//                                    startActivityForResult(
//                                        intent,
//                                        REQUEST_LOCATION_PERMISSION
//                                    )
//                                }
//                                .setNegativeButton("取消") { _, _ -> requestLocationPermission() }
//                                .show()
//                        } else {
//                            //權限被拒絕
//                            Toast.makeText(this, "位置權限被拒絕，功能將會無法正常使用", Toast.LENGTH_SHORT).show()
//                            requestLocationPermission()
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        when (requestCode) {
//            REQUEST_LOCATION_PERMISSION -> {
//                getLocationPermission()
//            }
//            REQUEST_ENABLE_GPS -> {
//                checkGPSState()
//            }
//        }
//    }



}