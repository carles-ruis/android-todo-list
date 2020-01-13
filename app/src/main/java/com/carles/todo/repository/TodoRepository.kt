package com.carles.todo.repository

import com.carles.todo.data.TodoDao
import com.carles.todo.data.toModel
import com.carles.todo.data.toVo
import com.carles.todo.model.Todo
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

class TodoRepository(val dao: TodoDao) {

    fun saveTodo(todo: Todo) : Single<Long> = dao.saveTodo(todo.toVo())

    fun getAllTodos() : Flowable<List<Todo>> = dao.getAllTodos().map { list -> list.map { vo -> vo.toModel() } }

    fun deleteTodo(todo:Todo)  : Completable = dao.deleteTodo(todo.toVo())
}
