package com.mark.badmintonpeer.groupdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mark.badmintonpeer.data.Group
import com.mark.badmintonpeer.data.source.BadmintonPeerRepository

class GroupDetailViewModel(val argument: Group,private val repository: BadmintonPeerRepository) : ViewModel() {


    private val _group = MutableLiveData<Group>().apply {
        value = argument
    }
    val group: LiveData<Group>
    get() = _group
}