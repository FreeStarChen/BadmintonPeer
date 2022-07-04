package com.mark.badmintonpeer.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (
    val id: String = "",
    val nickname: String = "",
    val email: String = "",
    val sex: String = "",
    val image: String = "",
    val degree: String = "",
    val friends: List<String> = listOf("")
        ) : Parcelable
