package com.mark.badmintonpeer.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.sql.Timestamp
import java.util.*

@Parcelize
data class Chat (
    val id: String = "",
    val senderId: String = "",
    val createdTime: Date = Timestamp(0),
    val content: String = "",
    val image: String = "",
    val name: String = ""
        ) : Parcelable
