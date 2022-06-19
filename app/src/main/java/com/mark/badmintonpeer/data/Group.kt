package com.mark.badmintonpeer.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Group (
    var id: String = "",
    val classification: String = "",
    val ownerId: String = "",
    val name: String = ""
        ) : Parcelable
