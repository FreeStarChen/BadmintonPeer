package com.mark.badmintonpeer.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Invitation (
    val id: String = ""
        ) : Parcelable
