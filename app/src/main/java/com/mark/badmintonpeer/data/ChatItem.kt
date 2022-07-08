package com.mark.badmintonpeer.data

import java.util.*

sealed class ChatItem {
    abstract val createdTime: Date

    data class UserSide(val chat: Chat) : ChatItem() {
        override val createdTime : Date
            get() = chat.createdTime
    }

    data class OtherSide(val chat: Chat) : ChatItem() {
        override val createdTime : Date
            get() = chat.createdTime
    }
}