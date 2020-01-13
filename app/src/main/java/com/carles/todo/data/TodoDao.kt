package com.carles.todo.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.carles.todo.model.Todo
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface TodoDao {

    @Insert
    fun saveTodo(todo: TodoVo) : Completable

    @Query("SELECT * from todo")
    fun getAllTodos() : Flowable<List<TodoVo>>
}