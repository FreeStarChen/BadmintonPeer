package com.mark.badmintonpeer.ext

import androidx.fragment.app.Fragment
import com.mark.badmintonpeer.MainApplication
import com.mark.badmintonpeer.data.Chatroom
import com.mark.badmintonpeer.data.Group
import com.mark.badmintonpeer.factory.*

/**
 * Extension functions for Fragment.
 */
fun Fragment.getVmFactory(type: String): StringTypeViewModelFactory {
    val repository = (requireContext().applicationContext as MainApplication).repository
    return StringTypeViewModelFactory(type, repository)
}

fun Fragment.getVmFactory(group: Group): GroupViewModelFactory {
    val repository = (requireContext().applicationContext as MainApplication).repository
    return GroupViewModelFactory(group, repository)
}

fun Fragment.getVmFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as MainApplication).repository
    return ViewModelFactory(repository)
}

fun Fragment.getVmFactory(chatroom: Chatroom): ChatroomChatViewModelFactory {
    val repository = (requireContext().applicationContext as MainApplication).repository
    return ChatroomChatViewModelFactory(chatroom, repository)
}
