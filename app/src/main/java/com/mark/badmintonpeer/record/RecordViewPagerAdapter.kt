package com.mark.badmintonpeer.record

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class RecordViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    var fragments: ArrayList<Fragment> = arrayListOf(
        RecordTypeFragment.newInstance("過往揪團"),
        RecordTypeFragment.newInstance("過往參團"),

    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}
