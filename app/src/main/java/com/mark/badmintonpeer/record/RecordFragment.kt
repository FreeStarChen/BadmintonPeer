package com.mark.badmintonpeer.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mark.badmintonpeer.R
import com.mark.badmintonpeer.databinding.RecordFragmentBinding
import com.mark.badmintonpeer.ext.getVmFactory

class RecordFragment : Fragment() {

    private val viewModel by viewModels<RecordViewModel> {
        getVmFactory(
            RecordFragmentArgs.fromBundle(
                requireArguments()
            ).typeKey
        )
    }
//
//    private val type by lazy {
//        RecordFragmentArgs.fromBundle(requireArguments()).typeKey
//    }

    private lateinit var viewPagerAdapter: RecordViewPagerAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var binding: RecordFragmentBinding

    companion object {
        fun newInstance() = RecordFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = RecordFragmentBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPagerAdapter = RecordViewPagerAdapter(this)
        viewPager = view.findViewById(R.id.view_pager_record)
        viewPager.adapter = viewPagerAdapter

        val tabLayoutArray = arrayOf(
            "過往揪團", "過往參團"
        )

        tabLayout = view.findViewById(R.id.tabs_record)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabLayoutArray[position]
        }.attach()
    }
}
