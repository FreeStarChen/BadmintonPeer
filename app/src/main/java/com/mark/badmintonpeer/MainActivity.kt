package com.mark.badmintonpeer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mark.badmintonpeer.creategroup.CreateGroupFragment
import com.mark.badmintonpeer.databinding.ActivityMainBinding
import com.mark.badmintonpeer.ext.getVmFactory
import com.mark.badmintonpeer.util.CurrentFragmentType
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    val viewModel by viewModels<MainViewModel> { getVmFactory() }

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

//        mContext = this
//        mLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

//        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
//        val bottomNavigationView: BottomNavigationView = binding.bottomNavigationView

//        NavigationUI.setupWithNavController(bottomNavigationView, navController)


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
            R.layout.spinner_item_white
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            binding.spinnerCities.adapter = adapter

        }

        binding.spinnerCities.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 != 0) {
                    val selectedCity = binding.spinnerCities.selectedItem.toString()
                    Timber.d("selectedCity=$selectedCity")

                    viewModel.city.value = selectedCity
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        viewModel.spinnerReset.observe(this) {
           it?.let {
               if (it) {
                   Timber.d("viewModel.spinnerReset start")
                   binding.spinnerCities.setSelection(0)
               }
           }

        }



        binding.imageToolbarFilter.setOnClickListener {
            findNavController(R.id.nav_host_fragment).navigate(NavigationDirections.navigateToFilterFragment())
        }

        binding.buttonCancel.setOnClickListener {

            AlertDialog.Builder(this)
                .setTitle("確定取消?")
                .setIcon(R.drawable.ic_warning)
                .setMessage("您所輸入的資料將不會被保留")
                .setPositiveButton("確定") { dialog, _ ->

                    findNavController(R.id.nav_host_fragment).navigateUp()

                    Toast.makeText(this, "已取消", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                .setNeutralButton("取消") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()

        }

        binding.buttonCreateGroup.setOnClickListener {

            AlertDialog.Builder(this)
                .setTitle("確定儲存?")
                .setPositiveButton("確定") { dialog, _ ->

                    val fragment =
                        supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                    val createGroupFragment =
                        fragment?.childFragmentManager?.primaryNavigationFragment as CreateGroupFragment
                    createGroupFragment.callViewModelAddGroupResult()

                    findNavController(R.id.nav_host_fragment).navigate(NavigationDirections.navigateToGroupFragment(null))

                    Toast.makeText(this, "已成功創建揪團", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                .setNeutralButton("取消") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()


//            childFragment.callViewModelAddGroupResult()

//            val createGroupViewModel = ViewModelProvider(
//                this,
//                ViewModelProvider.AndroidViewModelFactory.getInstance(application)
//            )[CreateGroupViewModel::class.java]
//
//            createGroupViewModel.addGroupResult()

        }

        binding.buttonMap.setOnClickListener {
            viewModel.switchStatus.value = viewModel.switchStatus.value == false
        }

//        binding.switchMap.setOnCheckedChangeListener { compoundButton, b ->
//            compoundButton.isChecked.let {
//                viewModel.switchStatus.value = it
//                getLocationPermission()

//                val groupTypeViewModel by viewModels<GroupTypeViewModel> { getVmFactory(viewModel.type.value!!) }
//                Timber.d("viewModel.type.value=${viewModel.type.value}")
//                groupTypeViewModel._recyclerViewVisible.value = it
//                val fragment =
//                    supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
//                val groupTypeFragment = fragment?.childFragmentManager?.primaryNavigationFragment as GroupTypeFragment
//                groupTypeFragment.setRecyclerViewVisible(it)
//            }
//        }

        setupNavController()
        setupBottomNav()
//        UserManager.clear()

    }

    /**
     * Set up [BottomNavigationView], add badge view through [BottomNavigationMenuView] and [BottomNavigationItemView]
     * to display the count of Cart
     */
    private fun setupBottomNav() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {

                R.id.navigation_group -> {
                    findNavController(R.id.nav_host_fragment).navigate(NavigationDirections.navigateToGroupFragment(null))
                    return@setOnItemSelectedListener true
                }

                R.id.navigation_chatroom -> {

                    when (viewModel.isLoggedIn) {
                        true -> {
                            findNavController(R.id.nav_host_fragment).navigate(NavigationDirections.navigateToChatroomFragment())
                            return@setOnItemSelectedListener true
                        }
                        false -> {
                            findNavController(R.id.nav_host_fragment).navigate(NavigationDirections.navigateToLoginDialog())
                            return@setOnItemSelectedListener false
                        }
                    }
                    return@setOnItemSelectedListener true
                }

                R.id.navigation_news -> {
                    findNavController(R.id.nav_host_fragment).navigate(NavigationDirections.navigateToNewsFragment())
                    return@setOnItemSelectedListener true
                }

                R.id.navigation_profile -> {

                    when (viewModel.isLoggedIn) {
                        true -> {
                            findNavController(R.id.nav_host_fragment).navigate(NavigationDirections.navigateToProfileFragment())
                            return@setOnItemSelectedListener true
                        }
                        false -> {
                            findNavController(R.id.nav_host_fragment).navigate(NavigationDirections.navigateToLoginDialog())
                            return@setOnItemSelectedListener false
                        }
                    }
                    return@setOnItemSelectedListener true
                }

            }
            false
        }
    }


    /**
     * Set up [NavController.addOnDestinationChangedListener] to record the current fragment, it better than another design
     * which is change the [CurrentFragmentType] enum value by [MainViewModel] at [onCreateView]
     */
    private fun setupNavController() {
        findNavController(R.id.nav_host_fragment).addOnDestinationChangedListener { navController: NavController, _: NavDestination, _: Bundle? ->
            viewModel.currentFragmentType.value = when (navController.currentDestination?.id) {
                R.id.navigation_group -> CurrentFragmentType.GROUP
                R.id.navigation_chatroom -> CurrentFragmentType.CHATROOM
                R.id.navigation_news -> CurrentFragmentType.NEWS
                R.id.navigation_profile -> CurrentFragmentType.PROFILE
                R.id.filterDialog -> CurrentFragmentType.FILTER
                R.id.createGroupFragment -> CurrentFragmentType.CREATE
                R.id.groupDetailFragment -> CurrentFragmentType.DETAIL
                R.id.chatroomChatFragment -> CurrentFragmentType.CHAT
                R.id.newsDetailFragment -> CurrentFragmentType.DETAIL
                R.id.recordFragment -> CurrentFragmentType.RECORD
                else -> viewModel.currentFragmentType.value
            }
        }
    }


}