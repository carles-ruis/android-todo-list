package com.carles.todo.ui

import android.location.Location
import android.location.Location.FORMAT_DEGREES
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import java.util.*

const val EXTRA_TODO = "extra_todo"

fun ViewGroup.inflate(@LayoutRes layoutRes: Int) = LayoutInflater.from(context).inflate(layoutRes, this, false)

fun DialogFragment.inflate(@LayoutRes layoutRes: Int) = LayoutInflater.from(context).inflate(layoutRes, null)

fun Location.toFormattedString() = String.format("%s, %s",
        Location.convert(latitude, FORMAT_DEGREES),
        Location.convert(longitude, FORMAT_DEGREES))

fun Long.toFormattedDateString(): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return String.format("%02d/%02d/%02d", calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.YEAR))
}
