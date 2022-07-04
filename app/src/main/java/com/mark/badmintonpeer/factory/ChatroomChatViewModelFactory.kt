package com.mark.badmintonpeer.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mark.badmintonpeer.chatroom.ChatroomChatViewModel
import com.mark.badmintonpeer.data.Chatroom
import com.mark.badmintonpeer.data.source.BadmintonPeerRepository
import java.lang.IllegalArgumentException

class ChatroomChatViewModelFactory(
    private val chatroom: Chatroom,
    private val repository: BadmintonPeerRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(ChatroomChatViewModel::class.java) ->
                    ChatroomChatViewModel(chatroom, repository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}