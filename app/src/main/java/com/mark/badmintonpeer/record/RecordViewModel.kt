package com.mark.badmintonpeer.record

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mark.badmintonpeer.data.source.BadmintonPeerRepository

class RecordViewModel(val argument: String, private val repository: BadmintonPeerRepository) : ViewModel() {

    private val _type = MutableLiveData<String>().apply {
        value = argument
    }

    val type: LiveData<String>
        get() = _type
}