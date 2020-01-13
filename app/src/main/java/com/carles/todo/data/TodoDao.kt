package com.carles.todo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface TodoDao {

    @Insert
    fun saveTodo(todo: TodoVo): Single<Long>

    @Query("SELECT * from todo")
    fun getAllTodos(): Flowable<List<TodoVo>>

    @Delete
    fun deleteTodo(todo: TodoVo): Completable
}