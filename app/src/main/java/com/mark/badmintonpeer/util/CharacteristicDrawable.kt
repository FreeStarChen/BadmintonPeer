package com.mark.badmintonpeer.util

import com.mark.badmintonpeer.R

object CharacteristicDrawable {

    fun getDrawable(characteristic: String): Int? {
        val characteristicMap = mapOf(
            "冷氣" to R.drawable.ic_air_condition,
            "飲水機" to R.drawable.ic_water_dispenser,
            "PU地面" to R.drawable.ic_pu_ground,
            "側面燈光" to R.drawable.ic_spotlight,
            "淋浴間" to R.drawable.ic_shower,
            "停車場" to R.drawable.ic_parking,
            "餐飲販售" to R.drawable.ic_cutlery,
            "吹風機" to R.drawable.ic_hair_dryer
        )
        return characteristicMap[characteristic]
    }
}
