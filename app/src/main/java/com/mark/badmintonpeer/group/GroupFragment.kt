package com.mark.badmintonpeer.group

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mark.badmintonpeer.NavigationDirections
import com.mark.badmintonpeer.R
import com.mark.badmintonpeer.databinding.GroupFragmentBinding

class GroupFragment : Fragment() {

    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    companion object {
        fun newInstance() = GroupFragment()
    }

    private lateinit var viewModel: GroupViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = GroupFragmentBinding.inflate(inflater)

        binding.imageAddGroup.setOnClickListener {
            this.findNavController().navigate(NavigationDirections.navigateToCreateGroupFragment())
        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPagerAdapter = ViewPagerAdapter(this)
        viewPager = view.findViewById(R.id.view_pager_group)
        viewPager.adapter = viewPagerAdapter

        val tabLayoutArray = arrayOf(
            "零打","季打","課程","比賽"
        )

        tabLayout = view.findViewById(R.id.tabs_group)
        TabLayoutMediator(tabLayout, viewPager) {tab,position ->
            tab.text = tabLayoutArray[position]
        }.attach()
    }

}