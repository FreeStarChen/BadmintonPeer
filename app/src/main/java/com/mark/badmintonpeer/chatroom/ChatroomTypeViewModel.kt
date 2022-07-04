package com.mark.badmintonpeer.chatroom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mark.badmintonpeer.MainApplication
import com.mark.badmintonpeer.R
import com.mark.badmintonpeer.data.Chatroom
import com.mark.badmintonpeer.data.Group
import com.mark.badmintonpeer.data.Result
import com.mark.badmintonpeer.data.source.BadmintonPeerRepository
import com.mark.badmintonpeer.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class ChatroomTypeViewModel(val type: String, val repository: BadmintonPeerRepository) :
    ViewModel() {

    // status: The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    // error: The internal MutableLiveData that stores the error of the most recent request
    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    // status for the loading icon of swl
    private val _refreshStatus = MutableLiveData<Boolean>()
    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus

    private var _chatroom = MutableLiveData<List<Chatroom>>()
    val chatroom: LiveData<List<Chatroom>>
        get() = _chatroom

    // Handle navigation to group detail
    private val _navigateToChatroomDetail = MutableLiveData<Chatroom>()
    val navigateToChatroomDetail: LiveData<Chatroom>
        get() = _navigateToChatroomDetail

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

        getChatroomResult()
    }

    fun getChatroomResult() {
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result: Result<List<Chatroom>> = if (type == "全部") {
                repository.getAllChatroom()
            } else {
                repository.getTypeChatroom(type)
            }

            Timber.d("type=$type")
            _chatroom.value = when (result) {
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
            _refreshStatus.value = false
        }
    }

    fun refresh() {
        if (MainApplication.instance.isLiveDataDesign()) {
            _status.value = LoadApiStatus.DONE
            _refreshStatus.value = false

        } else {
            if (status.value != LoadApiStatus.LOADING) {
                getChatroomResult()
                Timber.d("ChatroomTypeViewModel refresh()")
            }
        }
    }

    fun navigateToChatroomDetail(chatroom: Chatroom) {
        _navigateToChatroomDetail.value = chatroom
    }

    fun onChatroomDetailNavigated() {
        _navigateToChatroomDetail.value = null
    }
}