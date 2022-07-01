package com.mark.badmintonpeer.util

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.EditText
import com.mark.badmintonpeer.R
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

object TimeCalculator {

    @SuppressLint("SimpleDateFormat")
    fun getTime(time: Long): String {
        return if (android.os.Build.VERSION.SDK_INT >= 24) {
            SimpleDateFormat("HH:mm").format(time)
        } else {
            val tms = Calendar.getInstance()
            tms.get(Calendar.DAY_OF_MONTH).toString() + "/" +
                    tms.get(Calendar.MONTH).toString() + "/" +
                    tms.get(Calendar.YEAR).toString() + " " +
                    tms.get(Calendar.DAY_OF_MONTH).toString() + " " +
                    tms.get(Calendar.HOUR_OF_DAY).toString() + ":" +
                    tms.get(Calendar.MINUTE).toString() + ":" +
                    tms.get(Calendar.SECOND).toString()
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getDate(time: Long): String {
        return if (android.os.Build.VERSION.SDK_INT >= 24) {
            SimpleDateFormat("YYYY/MM/dd EE").format(time)
        } else {
            val tms = Calendar.getInstance()
            tms.get(Calendar.DAY_OF_MONTH).toString() + "/" +
                    tms.get(Calendar.MONTH).toString() + "/" +
                    tms.get(Calendar.YEAR).toString() + " " +
                    tms.get(Calendar.DAY_OF_MONTH).toString() + " " +
                    tms.get(Calendar.HOUR_OF_DAY).toString() + ":" +
                    tms.get(Calendar.MINUTE).toString() + ":" +
                    tms.get(Calendar.SECOND).toString()
        }
    }

    //extension function transform into DatePicker for edit tex
    fun EditText.transformIntoDatePicker(
        context: Context,
        format: String,
        maxDate: Date? = null
    ) {
        isFocusableInTouchMode = false
        isClickable = true
        isFocusable = false

        val calendar = Calendar.getInstance()
        val datePickerOnDataSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val sdf = SimpleDateFormat(format, Locale.CHINESE)
                setText(sdf.format(calendar.time))
            }

        setOnClickListener {
            DatePickerDialog(
                context,
                datePickerOnDataSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).run {
                maxDate?.time?.also { datePicker.maxDate = it }
                show()
            }
        }
    }

    //extension function transform into TimePicker for edit text
    fun EditText.transformIntoTimePicker(
        context: Context,
        format: String,
        maxDate: Date? = null
    ) {
        isFocusableInTouchMode = false
        isClickable = true
        isFocusable = false

        val calendar = Calendar.getInstance()
        val timePickerOnDataSetListener =
            TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)
                val sdf = SimpleDateFormat(format, Locale.CHINESE)
                setText(sdf.format(calendar.time))
            }

        setOnClickListener {
            TimePickerDialog(
                context,
                timePickerOnDataSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).run {
                show()
            }
        }
    }

    fun String.toDateLong(pattern: String = "MM/dd/yyyy") : Long {
        @SuppressLint("SimpleDateFormat")
        val dateFormat = SimpleDateFormat(pattern)
        var date: Date? = Date()
        try {
            date = dateFormat.parse(this)
        }catch (e: Exception) {
            e.printStackTrace()
        }
        return date?.time ?: 0
    }

    fun String.toTimeLong(pattern: String = "HH:mm") : Long {
        @SuppressLint("SimpleDateFormat")
        val dateFormat = SimpleDateFormat(pattern)
        var date: Date? = Date()
        try {
            date = dateFormat.parse(this)
        }catch (e: Exception) {
            e.printStackTrace()
        }
        return date?.time ?: 0
    }

}