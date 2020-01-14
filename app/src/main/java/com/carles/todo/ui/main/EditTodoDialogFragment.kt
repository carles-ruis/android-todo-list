package com.carles.todo.ui.main

import android.os.Bundle
import com.carles.todo.R
import com.carles.todo.model.Todo

class EditTodoDialogFragment : TodoDialogFragment() {

    companion object {
        val TAG = EditTodoDialogFragment::javaClass.name
        fun newInstance(todo: Todo) = EditTodoDialogFragment().apply {
            arguments = Bundle().apply { putParcelable(EXTRA_TODO, todo) }
        }
    }

    override fun getTitleTextRes() = R.string.main_edit_title
    override fun getPositiveButtonTextRes() = R.string.main_edit_confirm

    override fun onPositiveButtonClicked(todo: Todo) {
        listener?.onTodoEdited(todo)
    }
}