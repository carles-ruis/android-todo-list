package com.carles.todo.data

import android.location.Location
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.carles.todo.model.Todo

@Entity(tableName = "todo")
data class TodoVo(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    var name: String,
    var date: Long,
    var latitude: Double,
    var longitude: Double
)

fun Todo.toVo() = TodoVo(name = name, date = date, latitude = location.latitude, longitude = location.longitude)

fun TodoVo.toModel() = Todo(name = name, date = date,
    location = Location("dummy_provider").also { location ->
        location.latitude = latitude
        location.longitude = longitude
    })
