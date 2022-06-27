package com.mark.badmintonpeer.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mark.badmintonpeer.MainViewModel
import com.mark.badmintonpeer.chatroom.ChatroomViewModel
import com.mark.badmintonpeer.creategroup.CreateGroupViewModel
import com.mark.badmintonpeer.data.source.BadmintonPeerRepository
import com.mark.badmintonpeer.filter.FilterViewModel
import com.mark.badmintonpeer.group.GroupTypeViewModel
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

                isAssignableFrom(CreateGroupViewModel::class.java) ->
                    CreateGroupViewModel(repository)

                isAssignableFrom(FilterViewModel::class.java) ->
                    FilterViewModel(repository)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}