package com.carles.todo.model

import android.location.Location

data class Todo(var name: String, var date: Long, var location: Location, var id: Long? = null)