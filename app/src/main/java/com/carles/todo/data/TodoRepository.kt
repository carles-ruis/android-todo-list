package com.carles.todo.data

import com.carles.todo.model.Todo

class TodoRepository(private val dao: TodoDao) {

    fun insertTodo(todo: Todo) = dao.insertTodo(todo)

    fun getAllTodos() = dao.getAllTodos()

    fun deleteTodo(todo:Todo) = dao.deleteTodo(todo)

    fun updateTodo(todo: Todo) = dao.updateTodo(todo)
}
