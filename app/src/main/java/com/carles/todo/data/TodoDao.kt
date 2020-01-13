package com.carles.todo.data

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface TodoDao {

    @Insert
    fun insertTodo(todo: TodoVo): Single<Long>

    @Query("SELECT * from todo")
    fun getAllTodos(): Flowable<List<TodoVo>>

    @Delete
    fun deleteTodo(todo: TodoVo): Completable

    @Update
    fun updateTodo(todo: TodoVo) : Completable
}