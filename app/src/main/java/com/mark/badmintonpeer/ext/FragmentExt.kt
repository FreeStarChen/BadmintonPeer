package com.mark.badmintonpeer.ext

import androidx.fragment.app.Fragment
import com.mark.badmintonpeer.MainApplication
import com.mark.badmintonpeer.factory.GroupTypeViewModelFactory

/**
 * Extension functions for Fragment.
 */
fun  Fragment.getVmFactory(type: String): GroupTypeViewModelFactory {
    val repository =(requireContext().applicationContext as MainApplication).repository
    return GroupTypeViewModelFactory(type, repository)
}