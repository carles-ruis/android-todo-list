package com.carles.todo.ui.main

interface TodoView {

    fun displayTexts(name: String, date: String, location: String)
    fun setConfirmEnabled(enabled: Boolean)
    fun showCalendarWithDate(date: Long)
    fun navigateBack()

}