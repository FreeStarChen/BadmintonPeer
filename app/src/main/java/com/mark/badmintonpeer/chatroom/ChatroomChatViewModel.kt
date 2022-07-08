package com.mark.badmintonpeer.chatroom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mark.badmintonpeer.MainApplication
import com.mark.badmintonpeer.R
import com.mark.badmintonpeer.data.Chat
import com.mark.badmintonpeer.data.ChatItem
import com.mark.badmintonpeer.data.Chatroom
import com.mark.badmintonpeer.data.Result
import com.mark.badmintonpeer.data.source.BadmintonPeerRepository
import com.mark.badmintonpeer.login.UserManager
import com.mark.badmintonpeer.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class ChatroomChatViewModel(
    val argument: Chatroom,
    private val repository: BadmintonPeerRepository
) : ViewModel() {

    private val _chatroom = MutableLiveData<Chatroom>().apply {
        value = argument
    }
    val chatroom: LiveData<Chatroom>
        get() = _chatroom

    private val _chats = MutableLiveData<List<Chat>>()
    val chats: LiveData<List<Chat>>
        get() = _chats

    // status: The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    // error: The internal MutableLiveData that stores the error of the most recent request
    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _chatItem = MutableLiveData<List<ChatItem>>()
    val chatItem: LiveData<List<ChatItem>>
        get() = _chatItem


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

        getChatsResult()
    }

    fun getChatsResult() {
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = _chatroom.value?.let { repository.getChats(it.id) }
            Timber.d("getChatsResult result=$result")

            _chatItem.value = when (result) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    chatToChatItem(result.data)
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

    fun chatToChatItem(chats: List<Chat>): List<ChatItem> {
        val newItems = mutableListOf<ChatItem>()

        for (chat in chats) {
            if (chat.senderId == UserManager.userId) {
                newItems.add(ChatItem.UserSide(chat))
            }else {
                newItems.add(ChatItem.OtherSide(chat))
            }
        }
        return newItems
    }





}
