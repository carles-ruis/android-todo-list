package com.carles.todo.ui.main

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.carles.todo.R
import java.util.*

class AddTodoDialogFragment : DialogFragment() {

    private lateinit var customView: View

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        customView = LayoutInflater.from(context).inflate(R.layout.dialog_add_todo, null)

        return AlertDialog.Builder(context!!)
                .setTitle(R.string.main_add_title)
                .setView(customView)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.main_add, null)
                .create()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = customView


    companion object {
        val TAG = AddTodoDialogFragment::javaClass.name

        fun newInstance(calendar: Calendar, location: String) = AddTodoDialogFragment()
        /*    ExampleDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY, param1)
                }
            }*/
    }
}
