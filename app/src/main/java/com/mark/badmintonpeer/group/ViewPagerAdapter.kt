package com.mark.badmintonpeer.group

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    var fragments: ArrayList<Fragment> = arrayListOf(
        GroupTypeFragment.newInstance(),
        GroupTypeFragment.newInstance(),
        GroupTypeFragment.newInstance(),
        GroupTypeFragment.newInstance()

    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }


}
