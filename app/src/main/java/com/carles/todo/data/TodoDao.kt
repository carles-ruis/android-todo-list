package com.carles.todo.data

import androidx.room.*
import com.carles.todo.model.Todo
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface TodoDao {

    @Insert
    fun insertTodo(todo: Todo): Single<Long>

    @Query("SELECT * from todo")
    fun getAllTodos(): Single<List<Todo>>

    @Delete
    fun deleteTodo(todo: Todo): Completable

    @Update
    fun updateTodo(todo: Todo) : Completable
}