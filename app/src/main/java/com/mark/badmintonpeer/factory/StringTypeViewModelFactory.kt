package com.mark.badmintonpeer.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mark.badmintonpeer.chatroom.ChatroomTypeViewModel
import com.mark.badmintonpeer.data.source.BadmintonPeerRepository
import com.mark.badmintonpeer.group.GroupTypeViewModel
import com.mark.badmintonpeer.record.RecordTypeViewModel
import com.mark.badmintonpeer.record.RecordViewModel
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

                isAssignableFrom(ChatroomTypeViewModel::class.java) ->
                    ChatroomTypeViewModel(type, repository)

                isAssignableFrom(RecordTypeViewModel::class.java) ->
                    RecordTypeViewModel(type, repository)

                isAssignableFrom(RecordViewModel::class.java) ->
                    RecordViewModel(type, repository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}