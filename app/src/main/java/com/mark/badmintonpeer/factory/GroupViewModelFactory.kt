package com.mark.badmintonpeer.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mark.badmintonpeer.chatroom.ChatroomGroupChatViewModel
import com.mark.badmintonpeer.data.Group
import com.mark.badmintonpeer.data.source.BadmintonPeerRepository
import com.mark.badmintonpeer.groupdetail.GroupDetailViewModel
import java.lang.IllegalArgumentException

class GroupViewModelFactory(
    private val group: Group,
    private val repository: BadmintonPeerRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(GroupDetailViewModel::class.java) ->
                    GroupDetailViewModel(group, repository)

                isAssignableFrom(ChatroomGroupChatViewModel::class.java) ->
                    ChatroomGroupChatViewModel(group, repository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}