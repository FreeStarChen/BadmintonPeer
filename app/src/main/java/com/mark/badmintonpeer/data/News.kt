package com.mark.badmintonpeer.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.sql.Timestamp
import java.util.*

@Parcelize
data class News (
    val id: String = "",
    val title: String = "",
    val author: String = "",
    val postTime: Date = Timestamp(0),
    val image: String = "",
    val content: String = ""
        ) : Parcelable