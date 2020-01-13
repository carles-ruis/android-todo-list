package com.carles.todo.ui.main

import android.location.Location
import com.carles.todo.model.Todo

interface MainView {

    fun showTodos(todos: List<Todo>)
    fun showAddDialog(date: Long, location: Location)
    fun showLoading()
    fun hideLoading()

}