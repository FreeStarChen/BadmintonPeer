package com.mark.badmintonpeer.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Chat (
    val id: String = ""
        ) : Parcelable
