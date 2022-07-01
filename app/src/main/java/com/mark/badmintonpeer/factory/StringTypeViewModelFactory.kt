package com.mark.badmintonpeer.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mark.badmintonpeer.chatroom.ChatroomViewModel
import com.mark.badmintonpeer.data.source.BadmintonPeerRepository
import com.mark.badmintonpeer.group.GroupTypeViewModel
import java.lang.IllegalArgumentException

/**
 * Factory for group type item ViewModels.
 */
@Suppress("UNCHECKED_CAST")
class StringTypeViewModelFactory(
    private val type: String,
    private val repository: BadmintonPeerRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(GroupTypeViewModel::class.java) ->
                    GroupTypeViewModel(type, repository)
                isAssignableFrom(ChatroomViewModel::class.java) ->
                    ChatroomViewModel(type, repository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}