package com.mark.badmintonpeer.record

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mark.badmintonpeer.MainApplication
import com.mark.badmintonpeer.R
import com.mark.badmintonpeer.data.Group
import com.mark.badmintonpeer.data.Result
import com.mark.badmintonpeer.data.source.BadmintonPeerRepository
import com.mark.badmintonpeer.login.UserManager
import com.mark.badmintonpeer.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class RecordTypeViewModel(val type: String, val repository: BadmintonPeerRepository) : ViewModel() {

    // status: The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    private var _groups = MutableLiveData<List<Group>>()
    val groups: LiveData<List<Group>>
        get() = _groups

    // error: The internal MutableLiveData that stores the error of the most recent request
    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    // Handle navigation to group detail
    private val _navigateToGroupDetail = MutableLiveData<Group>()
    val navigateToGroupDetail: LiveData<Group>
        get() = _navigateToGroupDetail

    val userId
        get() = UserManager.userId

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
        Timber.d("------------------------------------------")
        Timber.d("$this")
        Timber.d("------------------------------------------")
    }

    fun getRecordOfCreatedGroupResult(type: String) {

        coroutineScope.launch {
            Timber.d("getRecordOfCreatedGroupResult is start")
            _status.value = LoadApiStatus.LOADING

            val result = userId?.let { repository.getRecordOfCreatedGroup(type, it) }
            Timber.d("type=$type")

            _groups.value = when (result) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    result.data
                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                else -> {
                    _error.value = MainApplication.instance.getString(R.string.you_know_nothing)
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
        }
    }

    fun getRecordOfJoinGroupResult(type: String) {

        coroutineScope.launch {
            Timber.d("getRecordOfJoinGroupResult is start")
            _status.value = LoadApiStatus.LOADING

            val result = userId?.let { repository.getRecordOfJoinGroup(type, it) }
            Timber.d("type=$type")

            _groups.value = when (result) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    result.data
                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                else -> {
                    _error.value = MainApplication.instance.getString(R.string.you_know_nothing)
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
        }
    }

    fun navigateToGroupDetail(group: Group) {
        _navigateToGroupDetail.value = group
    }

    fun onGroupDetailNavigated() {
        _navigateToGroupDetail.value = null
    }
}
