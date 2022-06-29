package com.mark.badmintonpeer.ext

import android.app.Activity
import com.mark.badmintonpeer.MainApplication
import com.mark.badmintonpeer.factory.GroupTypeViewModelFactory
import com.mark.badmintonpeer.factory.ViewModelFactory

/**
 * Extension functions for Activity.
 */
fun Activity.getVmFactory(): ViewModelFactory {
    val repository = (applicationContext as MainApplication).repository
    return ViewModelFactory(repository)
}

