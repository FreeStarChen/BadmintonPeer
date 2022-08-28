package com.mark.badmintonpeer.group

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mark.badmintonpeer.data.Filter
import com.mark.badmintonpeer.data.source.BadmintonPeerRepository

class GroupViewModel(
    val argument: Filter?,
    private val repository: BadmintonPeerRepository
) : ViewModel() {

    private val _filter = MutableLiveData<Filter?>().apply {
        value = argument
    }
    val filter: LiveData<Filter?>
        get() = _filter

    // Handle image view visible
    var _addGroupImageViewVisible = MutableLiveData<Boolean>()
    val addGroupImageViewVisible: LiveData<Boolean>
        get() = _addGroupImageViewVisible
}
