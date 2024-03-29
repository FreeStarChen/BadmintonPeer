package com.mark.badmintonpeer.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.sql.Timestamp
import java.util.*

@Parcelize
data class Chatroom (
    var id: String = "",
    val groupId: String = "",
    val member: List<String> = listOf(""),
    val image: String = "",
    val lastTalkMessage: String? = null,
    val lastTalkTime: Date? = null,
    val name: String = "",
    val type: String = "",
    val chats: List<Chat> = emptyList()
        ) : Parcelable
