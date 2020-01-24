package com.carles.todo.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.carles.todo.R
import com.carles.todo.ui.EXTRA_TODO
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_todo.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

abstract class TodoFragment : Fragment(), TodoView {

    abstract fun getTitleTextRes(): Int
    abstract fun getConfirmButtonTextRes(): Int
    abstract fun onConfirmButtonClicked()

    val presenter: TodoPresenter by inject { parametersOf(this) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.fragment_todo, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        todo_toolbar.setTitle(getTitleTextRes())
        (activity as AppCompatActivity).setSupportActionBar(todo_toolbar)
        (activity as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        todo_toolbar.setNavigationOnClickListener { onBackClicked() }
        todo_name_edittext.doAfterTextChanged { name -> presenter.onNameChanged(name.toString()) }
        todo_duedate_edittext.setOnClickListener { presenter.onDuedateClicked() }
        todo_confirm_button.setOnClickListener { onConfirmButtonClicked() }

        todo_confirm_button.setText(getConfirmButtonTextRes())
        presenter.onViewCreated(arguments!!.getParcelable(EXTRA_TODO)!!)
    }

    override fun displayTexts(name: String, date: String, location: String) {
        todo_name_edittext.setText(name)
        todo_duedate_edittext.setText(date)
        todo_location_edittext.setText(location)
    }

    private fun onBackClicked() {
        MaterialAlertDialogBuilder(context).setCancelable(true)
                .setMessage(R.string.todo_back_message)
                .setPositiveButton(R.string.todo_exit_button) { _, _ -> navigateBack() }
                .setNegativeButton(R.string.todo_stay_button) { _, _ -> }
                .show()
    }

    override fun setConfirmEnabled(enabled : Boolean) {
        todo_confirm_button.isEnabled = enabled
    }

    override fun showCalendarWithDate(date: Long) {
        val picker = MaterialDatePicker.Builder.datePicker().setSelection(date).setTitleText(R.string.todo_calendar_title).build()
        picker.addOnPositiveButtonClickListener { selectedDate ->
            presenter.onDateSelected(selectedDate)
        }
        picker.show(childFragmentManager, MaterialDatePicker<Long>::javaClass.name)
    }

    override fun navigateBack() {
        Navigation.findNavController(view!!).navigateUp()
    }

    override fun onDestroyView() {
        presenter.onViewDestroyed()
        super.onDestroyView()
    }
}

