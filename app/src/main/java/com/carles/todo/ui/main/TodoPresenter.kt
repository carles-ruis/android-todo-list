package com.carles.todo.ui.main

import android.content.Context
import com.carles.todo.R
import com.carles.todo.model.Todo
import com.carles.todo.repository.TodoRepository
import com.carles.todo.ui.addTo
import com.carles.todo.ui.toFormattedDateString
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable

class TodoPresenter(val view: TodoView, val uiScheduler: Scheduler, val processScheduler: Scheduler,
                    val repository: TodoRepository, val context: Context) {

    private val disposables = CompositeDisposable()
    private lateinit var todo: Todo

    fun onViewCreated(todo: Todo) {
        this.todo = todo
        displayTexts()
    }

    private fun displayTexts() {
        todo.apply {
            view.displayTexts(
                    name = name,
                    date = date.toFormattedDateString(),
                    location = context.getString(R.string.main_todo_location_formatted, latitude, longitude))
        }
    }

    fun onNameChanged(name:String) {
        todo.name = name
        view.setConfirmEnabled(name.length > 0)
    }

    fun onDuedateClicked() {
        view.showCalendarWithDate(todo.date)
    }

    fun onDateSelected(selectedDate : Long) {
        todo.date = selectedDate
        displayTexts()
    }

    fun onConfirmAddClicked() {
        repository.insertTodo(todo).subscribeOn(processScheduler).observeOn(uiScheduler).subscribe { rowId ->
            todo.id = rowId
            view.navigateBack()
        }.addTo(disposables)
    }

    fun onConfirmEditClicked() {
        repository.updateTodo(todo).subscribeOn(processScheduler).observeOn(uiScheduler).subscribe {
            view.navigateBack()
        }.addTo(disposables)
    }

    fun onViewDestroyed() {
        if (!disposables.isDisposed) disposables.dispose()
    }
}