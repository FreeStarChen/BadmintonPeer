package com.mark.badmintonpeer.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mark.badmintonpeer.MainViewModel
import com.mark.badmintonpeer.chatroom.ChatroomViewModel
import com.mark.badmintonpeer.data.source.BadmintonPeerRepository
import com.mark.badmintonpeer.group.GroupViewModel
import com.mark.badmintonpeer.news.NewsViewModel
import com.mark.badmintonpeer.profile.ProfileViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
    private val repository: BadmintonPeerRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {

            when {
                isAssignableFrom(MainViewModel::class.java) ->
                    MainViewModel(repository)

                isAssignableFrom(GroupViewModel::class.java) ->
                    GroupViewModel(repository)

                isAssignableFrom(ChatroomViewModel::class.java) ->
                    ChatroomViewModel(repository)

                isAssignableFrom(NewsViewModel::class.java) ->
                    NewsViewModel(repository)

                isAssignableFrom(ProfileViewModel::class.java) ->
                    ProfileViewModel(repository)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}