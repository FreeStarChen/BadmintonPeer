package com.mark.badmintonpeer.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class News (
    val id: String = "",
    val title: String = ""
        ) : Parcelable