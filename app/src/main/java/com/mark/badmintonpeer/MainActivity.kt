package com.mark.badmintonpeer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mark.badmintonpeer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navController = Navigation.findNavController(this,R.id.nav_host_fragment)
        val bottomNavigationView: BottomNavigationView = binding.bottomNavigationView

        NavigationUI.setupWithNavController(bottomNavigationView,navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.groupFragment -> {

                }
                R.id.chatroomFragment -> {

                }
                R.id.newsFragment -> {

                }
                R.id.profileFragment -> {

                }
            }
        }

        binding.imageToolbarFilter.setOnClickListener {
            findNavController(R.id.nav_host_fragment).navigate(NavigationDirections.navigateToFilterFragment())
        }

    }
}