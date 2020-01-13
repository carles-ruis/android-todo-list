package com.carles.todo.model

import android.location.Location
import java.io.Serializable

data class Todo(var name: String, var date: Long, var location: Location, var id: Long? = null) : Serializable {

    override fun equals(other: Any?): Boolean {
        return other is Todo && id == other.id
    }
}