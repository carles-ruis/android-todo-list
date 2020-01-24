package com.carles.todo.ui.main

import com.carles.todo.model.Todo

interface TodoListView {

    fun showTodos(todos: List<Todo>)
    fun showLoading()
    fun hideLoading()
    fun navigateToAddTodo(todo: Todo)

}