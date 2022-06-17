package com.mark.badmintonpeer.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Group (
    val id: String = "",
    val classification: String = "",
    val ownerId: String = "",
        ) : Parcelable
