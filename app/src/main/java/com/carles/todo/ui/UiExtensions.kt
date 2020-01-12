package com.carles.todo.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflate(@LayoutRes layoutRes: Int) = LayoutInflater.from(context).inflate(layoutRes, this, false)
