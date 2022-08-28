package com.mark.badmintonpeer.chatroom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mark.badmintonpeer.R
import com.mark.badmintonpeer.databinding.ChatroomFragmentBinding

class ChatroomFragment : Fragment() {

    private lateinit var viewPagerAdapter: ChatroomViewPagerAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    companion object {
        fun newInstance() = ChatroomFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ChatroomFragmentBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPagerAdapter = ChatroomViewPagerAdapter(this)
        viewPager = view.findViewById(R.id.view_pager_chatroom)
        viewPager.adapter = viewPagerAdapter

        val tabLayoutArray = arrayOf(
            "全部",
            "球友",
            "揪團",
            "群組"
        )

        tabLayout = view.findViewById(R.id.tabs_chatroom)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabLayoutArray[position]
        }.attach()
    }
}
