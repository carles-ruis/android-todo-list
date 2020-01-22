package com.carles.todo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.carles.todo.model.Todo

@Database(entities = arrayOf(Todo::class), version = 1, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun dao() : TodoDao
}