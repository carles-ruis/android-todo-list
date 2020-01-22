package com.carles.todo.repository

import com.carles.todo.data.TodoDao
import com.carles.todo.model.Todo
import java.util.concurrent.TimeUnit

class TodoRepository(val dao: TodoDao) {

    fun insertTodo(todo: Todo) = dao.insertTodo(todo)

    fun getAllTodos() = dao.getAllTodos()

    fun deleteTodo(todo:Todo) = dao.deleteTodo(todo)

    fun updateTodo(todo: Todo) = dao.updateTodo(todo)
}
