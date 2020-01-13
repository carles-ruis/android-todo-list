package com.carles.todo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.carles.todo.data.TodoDao
import com.carles.todo.data.TodoVo

@Database(entities = arrayOf(TodoVo::class), version = 1)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun dao() : TodoDao
}