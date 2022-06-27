package com.mark.badmintonpeer.ext

import androidx.fragment.app.Fragment
import com.mark.badmintonpeer.MainApplication
import com.mark.badmintonpeer.creategroup.CreateGroupFragment
import com.mark.badmintonpeer.data.Group
import com.mark.badmintonpeer.factory.*

/**
 * Extension functions for Fragment.
 */
fun Fragment.getVmFactory(type: String): GroupTypeViewModelFactory {
    val repository = (requireContext().applicationContext as MainApplication).repository
    return GroupTypeViewModelFactory(type, repository)
}

fun Fragment.getVmFactory(group: Group): GroupDetailViewModelFactory {
    val repository = (requireContext().applicationContext as MainApplication).repository
    return GroupDetailViewModelFactory(group, repository)
}

fun Fragment.getVmFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as MainApplication).repository
    return ViewModelFactory(repository)
}
