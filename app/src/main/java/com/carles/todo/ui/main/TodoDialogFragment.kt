package com.carles.todo.ui.main

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import com.carles.todo.R
import com.carles.todo.model.Todo
import com.carles.todo.ui.toFormattedDateString
import com.carles.todo.ui.toFormattedString
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.dialog_todo.*
import kotlinx.android.synthetic.main.dialog_todo.view.*

abstract class TodoDialogFragment : DialogFragment() {

    abstract fun getTitleTextRes() : Int
    abstract fun getPositiveButtonTextRes() : Int
    abstract fun onPositiveButtonClicked(todo: Todo)
    protected val EXTRA_TODO = "extra_todo"
    protected var listener: TodoDialogListener? = null

    private lateinit var todo: Todo
    private lateinit var name: String
    private var date: Long = 0
    private lateinit var location: Location
    private lateinit var customView: View
    private lateinit var positiveButton: Button

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? TodoDialogListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        todo = arguments!!.getSerializable(EXTRA_TODO) as Todo
        name = todo.name
        date = todo.date
        location = todo.location
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        customView = LayoutInflater.from(context).inflate(R.layout.dialog_todo, null)

        val dialog = AlertDialog.Builder(context!!)
            .setTitle(getTitleTextRes())
            .setView(customView)
            .setNegativeButton(R.string.cancel, null)
            .setPositiveButton(getPositiveButtonTextRes(), null)
            .create()
        dialog.setOnShowListener { initViews() }
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = customView

    private fun initViews() {
        positiveButton = (dialog as AlertDialog).getButton(DialogInterface.BUTTON_POSITIVE)
        positiveButton.setOnClickListener { onPositiveButtonClicked() }

        customView.add_todo_name_edittext.doAfterTextChanged { text ->
            positiveButton.isEnabled = if (text == null) false else text.length > 0
        }

        customView.add_todo_duedate_edittext.setOnClickListener {
            val picker = MaterialDatePicker.Builder.datePicker().setSelection(date).setTitleText(R.string.main_calendar_title).build()
            picker.addOnPositiveButtonClickListener { selectedDate ->
                date = selectedDate
                customView.add_todo_duedate_edittext.setText(date.toFormattedDateString())
            }
            picker.show(childFragmentManager, MaterialDatePicker<Long>::javaClass.name)
        }

        customView.add_todo_name_edittext.setText(name)
        customView.add_todo_duedate_edittext.setText(date.toFormattedDateString())
        customView.add_todo_location_edittext.setText(location.toFormattedString())
    }

    private fun onPositiveButtonClicked() {
        todo.name = add_todo_name_edittext.text.toString()
        todo.date = date
        todo.location = location
        onPositiveButtonClicked(todo)
        dismiss()
    }

    interface TodoDialogListener {
        fun onTodoAdded(todo: Todo)
        fun onTodoEdited(todo: Todo)
    }
}
