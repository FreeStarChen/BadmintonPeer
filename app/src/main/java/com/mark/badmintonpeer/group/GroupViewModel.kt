package com.mark.badmintonpeer.group

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mark.badmintonpeer.data.source.BadmintonPeerRepository

class GroupViewModel(private val repository: BadmintonPeerRepository) : ViewModel() {

    // Handle image view visible
    var _addGroupImageViewVisible = MutableLiveData<Boolean>()
    val addGroupImageViewVisible: LiveData<Boolean>
        get() = _addGroupImageViewVisible

}