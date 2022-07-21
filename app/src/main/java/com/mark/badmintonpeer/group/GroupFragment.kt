package com.mark.badmintonpeer.group

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mark.badmintonpeer.NavigationDirections
import com.mark.badmintonpeer.R
import com.mark.badmintonpeer.databinding.GroupFragmentBinding
import com.mark.badmintonpeer.ext.getVmFactory
import com.mark.badmintonpeer.filter.FilterViewModel
import com.mark.badmintonpeer.login.UserManager

class GroupFragment : Fragment() {

    private val viewModel by viewModels<GroupViewModel> {
        getVmFactory(
//            arguments?.let {
                GroupFragmentArgs.fromBundle(
                    requireArguments()
                ).filterKey
//            }
        )
    }

    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    companion object {
        fun newInstance() = GroupFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = GroupFragmentBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.imageAddGroup.setOnClickListener {
            if (UserManager.isLoggedIn) {
                findNavController().navigate(NavigationDirections.navigateToCreateGroupFragment())
            } else {
                findNavController().navigate(NavigationDirections.navigateToLoginDialog())
            }
        }



        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPagerAdapter = ViewPagerAdapter(this)
        viewPager = view.findViewById(R.id.view_pager_group)
        viewPager.adapter = viewPagerAdapter

        val tabLayoutArray = arrayOf(
            "零打", "季打", "課程", "比賽"
        )

        tabLayout = view.findViewById(R.id.tabs_group)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabLayoutArray[position]
        }.attach()
    }

}