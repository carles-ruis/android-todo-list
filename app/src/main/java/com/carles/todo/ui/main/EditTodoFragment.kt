package com.carles.todo.ui.main

import com.carles.todo.R

class EditTodoFragment : TodoFragment() {

    override fun getTitleTextRes() = R.string.todo_edit_title
    override fun getConfirmButtonTextRes() = R.string.todo_edit_button
    override fun onConfirmButtonClicked() {
        presenter.onConfirmEditClicked()
    }

}