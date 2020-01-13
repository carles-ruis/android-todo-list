package com.carles.todo.ui.main

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface.BUTTON_POSITIVE
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
import kotlinx.android.synthetic.main.dialog_add_todo.*
import kotlinx.android.synthetic.main.dialog_add_todo.view.*

class AddTodoDialogFragment : DialogFragment() {

    private var date: Long = 0
    private lateinit var location: Location
    private lateinit var customView: View
    private lateinit var positiveButton: Button
    private var listener: AddTodoListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? AddTodoListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        date = arguments!!.getLong(EXTRA_DATE)
        location = arguments!!.getParcelable(EXTRA_LOCATION)!!
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        customView = LayoutInflater.from(context).inflate(R.layout.dialog_add_todo, null)

        val dialog = AlertDialog.Builder(context!!)
                .setTitle(R.string.main_add_title)
                .setView(customView)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.main_add, null)
                .create()
        dialog.setOnShowListener { initViews() }
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = customView

    private fun initViews() {
        positiveButton = (dialog as AlertDialog).getButton(BUTTON_POSITIVE)
        positiveButton.setOnClickListener { onPositiveButtonClicked() }

        customView.add_todo_name_edittext.doAfterTextChanged { text ->
            positiveButton.isEnabled = if (text == null) false else text.length > 0
        }

        customView.add_todo_duedate_edittext.setOnClickListener {
            val picker = MaterialDatePicker.Builder.datePicker().setSelection(date).setTitleText(R.string.main_title_duedate).build()
            picker.addOnPositiveButtonClickListener { selectedDate ->
                date = selectedDate
                customView.add_todo_duedate_edittext.setText(date.toFormattedDateString())
            }
            picker.show(childFragmentManager, MaterialDatePicker<Long>::javaClass.name)
        }

        customView.add_todo_name_edittext.setText("")
        customView.add_todo_duedate_edittext.setText(date.toFormattedDateString())
        customView.add_todo_location_edittext.setText(location.toFormattedString())
    }

    private fun onPositiveButtonClicked() {
        listener?.onTodoAdded(Todo(add_todo_name_edittext.text.toString(), date, location))
        dismiss()
    }

    companion object {
        val TAG = AddTodoDialogFragment::javaClass.name
        private val EXTRA_DATE = "extra_date"
        private val EXTRA_LOCATION = "extra_location"

        fun newInstance(date:Long, location: Location) = AddTodoDialogFragment().apply {
            arguments = Bundle().apply {
                putLong(EXTRA_DATE, date)
                putParcelable(EXTRA_LOCATION, location)
            }
        }
    }

    interface AddTodoListener {
        fun onTodoAdded(todo: Todo)
    }
}
