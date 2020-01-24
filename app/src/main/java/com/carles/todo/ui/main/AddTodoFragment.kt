package com.carles.todo.ui.main

import com.carles.todo.R

class AddTodoFragment : TodoFragment() {

    override fun getTitleTextRes() = R.string.todo_add_title
    override fun getConfirmButtonTextRes() = R.string.todo_add_button
    override fun onConfirmButtonClicked() {
        presenter.onConfirmAddClicked()
    }

}