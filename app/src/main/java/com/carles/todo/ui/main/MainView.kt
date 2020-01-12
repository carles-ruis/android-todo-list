package com.carles.todo.ui.main

import java.util.*

interface MainView {

    fun showAddDialog(calendar: Calendar, location:String)
    fun showLoading()
    fun hideLoading()

}