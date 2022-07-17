package com.mark.badmintonpeer.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.sql.Timestamp
import java.util.*

@Parcelize
data class Group (
    var id: String = "",
    var classification: String = "",
    var ownerId: String = "",
    var images: List<String> = listOf(""),
    var name: String = "",
    var date: Date = Timestamp(0),
    var startTime: Date = Timestamp(0),
    var endTime: Date = Timestamp(0),
    var member: List<String> = listOf(""),
    var place: String = "",
    var address: String = "",
    var characteristic: List<String> = listOf(""),
    var needPeopleNumber: Int? = null,
    var totalPeopleNumber: Int? = null,
    var ball: String = "",
    var price: Int? = null,
    var degree: List<String> = listOf(""),
    var contactNumber: String = "",
    var courtNumber: Int? = null,
    var other: String = ""
        ) : Parcelable
