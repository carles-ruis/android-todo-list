package com.carles.todo.repository

import com.carles.todo.data.TodoDao
import com.carles.todo.data.toModel
import com.carles.todo.data.toVo
import com.carles.todo.model.Todo
import io.reactivex.Completable
import io.reactivex.Flowable

class TodoRepository(val dao: TodoDao) {

    fun saveTodo(todo: Todo) : Completable = dao.saveTodo(todo.toVo())

    fun getAllTodos() : Flowable<List<Todo>> = dao.getAllTodos().map { list -> list.map { vo -> vo.toModel() } }
}
