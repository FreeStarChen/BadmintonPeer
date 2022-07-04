package com.mark.badmintonpeer.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.sql.Timestamp
import java.util.*

@Parcelize
data class Chatroom (
    val id: String = "",
    val groupId: String = "",
    val member: List<String> = listOf(""),
    val image: String = "",
    val lastTalkMessage: String = "",
    val lastTalkTime: Date = Timestamp(0),
    val name: String = "",
    val type: String = ""
        ) : Parcelable
