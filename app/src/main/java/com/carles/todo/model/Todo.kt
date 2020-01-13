package com.carles.todo.model

import android.location.Location
import java.io.Serializable

data class Todo(var name: String, var date: Long, var location: Location, var id: Long? = null) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Todo
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}