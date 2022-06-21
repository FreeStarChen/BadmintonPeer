package com.mark.badmintonpeer.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.sql.Timestamp
import java.util.*

@Parcelize
data class Group (
    var id: String = "",
    val classification: String = "",
    val ownerId: String = "",
    val images: List<String> = listOf(""),
    val name: String = "",
    val date: Date = Timestamp(0),
    val startTime: Date = Timestamp(0),
    val endTime: Date = Timestamp(0),
    val member: List<String> = listOf(""),
    val place: String = "",
    val address: String = "",
    val characteristic: List<String> = listOf(""),
    val needPeopleNumber: Int = 0,
    val totalPeopleNumber: Int = 0,
    val ball: String = "",
    val price: Int = 0,
    val degree: List<String> = listOf(""),
    val contactNumber: Long = 0L,
    val courtNumber: Int = 0,
    val other: String = ""
        ) : Parcelable
