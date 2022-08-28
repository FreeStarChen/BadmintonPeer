package com.mark.badmintonpeer.group

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.mark.badmintonpeer.MainViewModel
import com.mark.badmintonpeer.NavigationDirections
import com.mark.badmintonpeer.R
import com.mark.badmintonpeer.databinding.GroupTypeFragmentBinding
import com.mark.badmintonpeer.ext.getVmFactory
import java.util.Locale
import timber.log.Timber

class GroupTypeFragment :
    Fragment(),
    GoogleMap.OnInfoWindowClickListener,
    GoogleMap.OnMarkerClickListener,
    OnMapReadyCallback {

    private lateinit var binding: GroupTypeFragmentBinding

    private var locationPermissionGranted = false

    private lateinit var mContext: Context
    private var googleMap: GoogleMap? = null
    private lateinit var mLocationProviderClient: FusedLocationProviderClient
    lateinit var addressLocation: List<Address>

    lateinit var mainViewModel: MainViewModel
    lateinit var groupViewModel: GroupViewModel

    // 台北101
    private val defaultLocation = LatLng(25.0338483, 121.5645283)

    /**
     * Lazily initialize our [GroupTypeViewModel].
     */
    private val viewModel by viewModels<GroupTypeViewModel> { getVmFactory(getType()) }

    companion object {
        fun newInstance(type: String): GroupTypeFragment {
            val fragment = GroupTypeFragment()
            val args = Bundle()
            args.putString("type", type)
            fragment.arguments = args
            return fragment
        }

        const val REQUEST_LOCATION_PERMISSION = 1
        const val REQUEST_ENABLE_GPS = 2
    }

    fun getType(): String {
        return requireArguments().getString("type", "")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mContext = requireActivity()
        mLocationProviderClient =
            activity?.let { LocationServices.getFusedLocationProviderClient(it) }!!

        binding = GroupTypeFragmentBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.recyclerViewGroupType.adapter = GroupTypeAdapter(
            GroupTypeAdapter.OnClickListener {
                viewModel.navigateToGroupDetail(it)
            }
        )

        binding.recyclerViewGroupTypeForMap.adapter = GroupTypeAdapter(
            GroupTypeAdapter.OnClickListener {
                viewModel.navigateToGroupDetail(it)
            }
        )

        viewModel.navigateToGroupDetail.observe(viewLifecycleOwner) {
            it?.let {
                findNavController().navigate(NavigationDirections.navigateToGroupDetailFragment(it))
                viewModel.onGroupDetailNavigated()
            }
        }

        viewModel.groups.observe(viewLifecycleOwner) {
            (binding.recyclerViewGroupType.adapter as GroupTypeAdapter).submitList(it)
//            (binding.recyclerViewGroupType.adapter as GroupTypeAdapter).notifyDataSetChanged()
            Timber.d("groups=${viewModel.groups.value}")
            googleMap?.clear()
            // Add groups address Marker at google map
            viewModel.groups.value?.let { listGroups ->
                val geoCoder: Geocoder? = Geocoder(context, Locale.getDefault())
//                val groupAddressList = mutableListOf<List<Address>>()
                for (group in listGroups) {
                    addressLocation = (geoCoder!!.getFromLocationName(group.address, 1))
//                    groupAddressList.add(addressLocation)
                    Timber.d("addressLocation=$addressLocation")
                    val stopLatitude = addressLocation[0].latitude
                    val stopLongitude = addressLocation[0].longitude
                    val groupLocation = LatLng(stopLatitude, stopLongitude)
                    googleMap?.addMarker(
                        MarkerOptions().position(groupLocation).title(group.name)
                            .icon(
                                BitmapDescriptorFactory.fromResource(
                                    R.drawable.ic_badminton_app_icon
                                )
                            )
                    )
                }
            }
        }

        viewModel.noFilterGroupToast.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(context, "無符合篩選的揪團", Toast.LENGTH_SHORT).show()
            }
        }

        binding.layoutSwipeRefreshGroupType.setOnRefreshListener {
            viewModel.refresh()
            mainViewModel.spinnerReset()

            Timber.d("layoutSwipeRefreshGroupType refresh")
        }

        viewModel.refreshStatus.observe(viewLifecycleOwner) {
            it?.let {
                binding.layoutSwipeRefreshGroupType.isRefreshing = it
                mainViewModel.onSpinnerReset()
            }
        }

        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        mainViewModel.refresh.observe(viewLifecycleOwner) {
            it?.let {
                viewModel.refresh()
                mainViewModel.onRefreshed()
            }
        }

        mainViewModel.switchStatus.observe(viewLifecycleOwner) {
            Timber.i("type=" + getType() + ", switchStatus=" + it)
            viewModel._recyclerViewVisible.value = it
        }

        mainViewModel.city.observe(viewLifecycleOwner) {
            viewModel.getSearchCityGroupResult(it)
        }

        viewModel.recyclerViewVisible.observe(viewLifecycleOwner) {

            ViewModelProvider(requireParentFragment())
                .get(GroupViewModel::class.java)
                ._addGroupImageViewVisible.value =
                it
        }

        groupViewModel = ViewModelProvider(requireParentFragment()).get(GroupViewModel::class.java)
        groupViewModel.filter.observe(viewLifecycleOwner) {
            Timber.d("groupViewModel.filter = ${groupViewModel.filter.value}")
            it?.let {
                viewModel.getFilterGroupResult(it)
            }
        }

        return binding.root
    }

    private fun getLocationPermission() {
        // 檢查權限
        if (activity?.let {
            ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
        } == PackageManager.PERMISSION_GRANTED
        ) {
            // 已獲取到權限
//            Toast.makeText(this, "已獲取到位置權限，可以準備開始獲取經緯度", Toast.LENGTH_SHORT).show()
            locationPermissionGranted = true
            checkGPSState()
        } else {
            // 詢問要求獲取權限
            requestLocationPermission()
        }
    }

    private fun checkGPSState() {
        val locationManager = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder(mContext)
                .setTitle("GPS 尚未開啟")
                .setMessage("使用此功能需要開啟 GSP 定位功能")
                .setPositiveButton(
                    "前往開啟",
                    DialogInterface.OnClickListener { _, _ ->
                        startActivityForResult(
                            Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),
                            REQUEST_ENABLE_GPS
                        )
                    }
                )
                .setNegativeButton("取消", null)
                .show()
        } else {
//            Toast.makeText(activity, "已獲取到位置權限且GPS已開啟，可以準備開始獲取經緯度", Toast.LENGTH_SHORT).show()
            getDeviceLocation()
        }
    }

    private fun getDeviceLocation() {
        try {
            if (locationPermissionGranted
            ) {
                val locationRequest = LocationRequest()
                locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                // 更新頻率
                locationRequest.interval = 1000
                // 更新次數，若沒設定，會持續更新
                locationRequest.numUpdates = 1
                mLocationProviderClient.requestLocationUpdates(
                    locationRequest,
                    object : LocationCallback() {
                        override fun onLocationResult(locationResult: LocationResult?) {
                            locationResult ?: return
                            Timber.d(
                                "緯度:" + locationResult.lastLocation.latitude +
                                    " , 經度:" + locationResult.lastLocation.longitude + " "
                            )

                            val currentLocation =
                                LatLng(
                                    locationResult.lastLocation.latitude,
                                    locationResult.lastLocation.longitude
                                )

//                            清除所有標記
//                            googleMap?.clear()
//
//                            清除上一次位置標記
//                            mCurrLocationMarker?.remove()
//
//                            當下位置存到一個 Marker 變數中，好讓下一次可以清除
//                            mCurrLocationMarker =googleMap?.addMarker(
//                                MarkerOptions().position(currentLocation).title("現在位置")
//                            )

                            googleMap?.isMyLocationEnabled = true

//                            googleMap?.addMarker(
//                                MarkerOptions().position(currentLocation).title("現在位置")
//                            )?.showInfoWindow()
                            googleMap?.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    currentLocation, 13f
                                )
                            )
                        }
                    },
                    null
                )
            } else {
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Timber.e(e, e.message)
        }
    }

    private fun requestLocationPermission() {
        if (activity?.let {
            ActivityCompat.shouldShowRequestPermissionRationale(
                    it, Manifest.permission.ACCESS_FINE_LOCATION
                )
        } == true
        ) {
            AlertDialog.Builder(requireActivity())
                .setMessage("此應用程式，需要位置權限才能正常使用")
                .setPositiveButton("確定") { _, _ ->
                    ActivityCompat.requestPermissions(
                        requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        REQUEST_LOCATION_PERMISSION
                    )
                }
                .setNegativeButton("取消") { _, _ -> requestLocationPermission() }
                .show()
        } else {
            activity?.let {
                ActivityCompat.requestPermissions(
                    it, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_LOCATION_PERMISSION
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_LOCATION_PERMISSION -> {
                if (grantResults.isNotEmpty()) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // 已獲取到權限
                        locationPermissionGranted = true
                        checkGPSState()
                    } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        if (!activity?.let {
                            ActivityCompat.shouldShowRequestPermissionRationale(
                                    it,
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                )
                        }!!
                        ) {
                            // 權限被永久拒絕
                            Toast.makeText(activity, "位置權限已被關閉，功能將會無法正常使用", Toast.LENGTH_SHORT)
                                .show()

                            AlertDialog.Builder(requireActivity())
                                .setTitle("開啟位置權限")
                                .setMessage("此應用程式，位置權限已被關閉，需開啟才能正常使用")
                                .setPositiveButton("確定") { _, _ ->
                                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                                    startActivityForResult(
                                        intent,
                                        REQUEST_LOCATION_PERMISSION
                                    )
                                }
                                .setNegativeButton("取消") { _, _ -> requestLocationPermission() }
                                .show()
                        } else {
                            // 權限被拒絕
                            Toast.makeText(activity, "位置權限被拒絕，功能將會無法正常使用", Toast.LENGTH_SHORT)
                                .show()
                            requestLocationPermission()
                        }
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_LOCATION_PERMISSION -> {
                getLocationPermission()
            }
            REQUEST_ENABLE_GPS -> {
                checkGPSState()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        Timber.d("fun onMapReady")
        getLocationPermission()

        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                defaultLocation, 6f
            )
        )

//        googleMap.uiSettings.setAllGesturesEnabled(true)
//        googleMap.setOnInfoWindowClickListener(this)
        googleMap.setOnMarkerClickListener(this)
    }

    override fun onInfoWindowClick(p0: Marker) {
//        Timber.d("fun onInfoWindowClick")
//        viewModel.groups.value?.let {
//            Timber.d("viewModel.groups.value=${viewModel.groups.value}")
//            for (group in it) {
//                if (group.name == p0.title) {
//                    (binding.recyclerViewGroupTypeForMap.adapter as GroupTypeAdapter).submitList(
//                        listOf(group)
//                    )
//                }
//            }
//        }
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        Timber.d("fun onMarkerClick")
        viewModel.groups.value?.let {
            for (group in it) {
                if (group.name == p0.title) {
                    Timber.d("group.name=${group.name}")
                    (binding.recyclerViewGroupTypeForMap.adapter as GroupTypeAdapter).submitList(
                        listOf(group)
                    )
                }
            }
        }
        return false
    }
}
