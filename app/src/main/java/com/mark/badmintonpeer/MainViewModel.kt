package com.mark.badmintonpeer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mark.badmintonpeer.data.source.BadmintonPeerRepository
import com.mark.badmintonpeer.login.UserManager
import com.mark.badmintonpeer.util.CurrentFragmentType
import kotlinx.coroutines.Job
import timber.log.Timber

class MainViewModel(private val repository: BadmintonPeerRepository) : ViewModel() {

    // Record current fragment to support data binding
    val currentFragmentType = MutableLiveData<CurrentFragmentType>()

    private val _refresh = MutableLiveData<Boolean>()
    val refresh: LiveData<Boolean>
        get() = _refresh

    private val _spinnerReset = MutableLiveData<Boolean>()
    val spinnerReset: LiveData<Boolean>
        get() = _spinnerReset

    // check user login status
    val isLoggedIn
        get() = UserManager.isLoggedIn

    val switchStatus = MutableLiveData<Boolean>()

    val city = MutableLiveData<String>()

    val type = MutableLiveData<String>()

    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
        Timber.d("------------------------------------------")
        Timber.d("$this")
        Timber.d("------------------------------------------")
        switchStatus.value = false
    }

    fun refresh() {
        if (MainApplication.instance.isLiveDataDesign()) {
            _refresh.value = true
        }
    }

    fun onRefreshed() {
        if (MainApplication.instance.isLiveDataDesign()) {
            _refresh.value = null
        }
    }

    fun spinnerReset() {
            _spinnerReset.value = true

    }

    fun onSpinnerReset() {
            _spinnerReset.value = null

    }



}