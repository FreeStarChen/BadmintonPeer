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
    var needPeopleNumber: Int = 0,
    var totalPeopleNumber: Int = 0,
    var ball: String = "",
    var price: Int = 0,
    var degree: List<String> = listOf(""),
    var contactNumber: Long = 0L,
    var courtNumber: Int = 0,
    var other: String = ""
        ) : Parcelable
