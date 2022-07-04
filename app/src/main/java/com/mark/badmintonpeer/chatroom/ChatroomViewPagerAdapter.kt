package com.mark.badmintonpeer.chatroom

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ChatroomViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    var fragments: ArrayList<Fragment> = arrayListOf(
        ChatroomTypeFragment.newInstance("全部"),
        ChatroomTypeFragment.newInstance("球友"),
        ChatroomTypeFragment.newInstance("揪團"),
        ChatroomTypeFragment.newInstance("群組")

    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}
