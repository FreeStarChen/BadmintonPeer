package com.mark.badmintonpeer.creategroup

import androidx.databinding.InverseMethod
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mark.badmintonpeer.MainApplication
import com.mark.badmintonpeer.R
import com.mark.badmintonpeer.data.Group
import com.mark.badmintonpeer.data.Result
import com.mark.badmintonpeer.data.source.BadmintonPeerRepository
import com.mark.badmintonpeer.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class CreateGroupViewModel(val repository: BadmintonPeerRepository) : ViewModel() {

    companion object {
        fun newInstance() = CreateGroupViewModel
    }

    private val _group = MutableLiveData<Group>().apply {
        value = Group()
    }
    val group: LiveData<Group>
        get() = _group

    // status: The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    // error: The internal MutableLiveData that stores the error of the most recent request
    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _leave = MutableLiveData<Boolean>()
    val leave: LiveData<Boolean>
        get() = _leave

    val selectedClassification = MutableLiveData<String>()

//    private val shippingTime: String
//        get() = when (selectedClassification.value) {
//            R.id.radioButton1 -> "零打"
//            R.id.radioButton2 -> "季打"
//            R.id.radioButton3 -> "課程"
//            R.id.radioButton3 -> "比賽"
//            else -> ""
//        }

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

    fun addGroupResult() {

        coroutineScope.launch {
            Timber.d("222")
            _status.value = LoadApiStatus.LOADING
            Timber.d("group.value=${group.value}")
            when (val result = repository.addGroup(group.value!!)) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    leave(true)
                    Timber.d("111")
                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                }
                else -> {
                    _error.value = MainApplication.instance.getString(R.string.you_know_nothing)
                    _status.value = LoadApiStatus.ERROR
                }
            }
        }
    }

    fun leave(needRefresh: Boolean = false) {
        _leave.value = needRefresh
    }

    fun onLeft() {
        _leave.value = null
    }


    @InverseMethod("convertIntToString")
    fun convertStringToInt(value: String): Int {
        return try {
            value.toInt().let {
                when (it) {
                    0 -> 1
                    else -> it
                }
            }
        } catch (e: NumberFormatException) {
            1
        }
    }

    fun convertIntToString(value: Int): String {
        return value.toString()
    }

    @InverseMethod("convertLongToString")
    fun convertStringToLong(value: String): Long {
        return try {
            value.toLong().let {
                when (it) {
                    0L -> 1
                    else -> it
                }
            }
        } catch (e: NumberFormatException) {
            1
        }
    }

    fun convertLongToString(value: Long): String {
        return value.toString()
    }

}