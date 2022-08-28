package com.mark.badmintonpeer.data

import android.os.Parcelable
import java.sql.Timestamp
import java.util.Date
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Chat(
    var id: String = "",
    var senderId: String = "",
    var createdTime: Date = Timestamp(0),
    var content: String = "",
    var image: String = "",
    var name: String = ""
) : Parcelable
