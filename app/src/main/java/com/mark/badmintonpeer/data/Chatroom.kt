package com.mark.badmintonpeer.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Chatroom (
    val id: String = ""
        ) : Parcelable
