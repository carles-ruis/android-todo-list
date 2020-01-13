package com.carles.todo.ui.main

import com.carles.todo.model.Todo

interface MainView {

    fun showTodos(todos: List<Todo>)
    fun showAddDialog(todo: Todo)
    fun showLoading()
    fun hideLoading()

}