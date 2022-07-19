package com.mark.badmintonpeer.record

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mark.badmintonpeer.chatroom.ChatroomTypeFragment

class RecordViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    var fragments: ArrayList<Fragment> = arrayListOf(
        RecordTypeFragment.newInstance("過往揪團紀錄"),
        RecordTypeFragment.newInstance("過往參團紀錄"),

        )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}
