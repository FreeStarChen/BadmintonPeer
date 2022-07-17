package com.mark.badmintonpeer.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.sql.Timestamp
import java.util.*

@Parcelize
data class  Filter (
    val city: String,
    val town: String,
    val date: Date,
    val wantTimes: List<Date>,
    val wantDegrees: List<String>,
    val priceLow: Int,
    val priceHigh: Int
        ) : Parcelable