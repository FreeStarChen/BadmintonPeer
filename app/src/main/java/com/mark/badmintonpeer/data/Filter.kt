package com.mark.badmintonpeer.data

import android.os.Parcelable
import java.util.Date
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Filter(
    val city: String,
    val town: String,
    val date: Date,
    val wantPeriods: List<String>,
    val wantDegrees: List<String>,
    val priceLow: Int,
    val priceHigh: Int,
) : Parcelable
