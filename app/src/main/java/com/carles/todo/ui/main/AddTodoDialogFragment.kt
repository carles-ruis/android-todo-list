package com.carles.todo.ui.main

import android.os.Bundle
import com.carles.todo.R
import com.carles.todo.model.Todo

class AddTodoDialogFragment : TodoDialogFragment() {

    companion object {
        val TAG = AddTodoDialogFragment::javaClass.name
        fun newInstance(todo: Todo) = AddTodoDialogFragment().apply {
            arguments = Bundle().apply { putSerializable(EXTRA_TODO, todo) }
        }
    }

    override fun getTitleTextRes() = R.string.main_add_title
    override fun getPositiveButtonTextRes() = R.string.main_add_confirm

    override fun onPositiveButtonClicked(todo: Todo) {
        listener?.onTodoAdded(todo)
    }
}
