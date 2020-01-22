package com.carles.todo.ui.main

import android.location.Location
import android.util.Log
import com.carles.todo.model.Todo
import com.carles.todo.repository.TodoRepository
import com.carles.todo.ui.addTo
import com.google.android.gms.location.FusedLocationProviderClient
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import java.util.*

class MainPresenter(
    val view: MainView,
    val locationClient: FusedLocationProviderClient,
    val uiScheduler: Scheduler,
    val processScheduler: Scheduler,
    val repository: TodoRepository
) {

    private val disposables = CompositeDisposable()

    fun onViewCreated() {
        view.showLoading()
        repository.getAllTodos().subscribeOn(processScheduler).observeOn(uiScheduler).subscribe(::onGetTodosSuccess,
                    ::onGetTodosError).addTo(disposables)
    }

    private fun onGetTodosSuccess(todos: List<Todo>) {
        view.hideLoading()
        view.showTodos(todos)
    }

    private fun onGetTodosError(throwable: Throwable) {
        view.hideLoading()
        Log.e(javaClass.name, "onGetTodosError:" + throwable.message)
    }

    fun onAddClicked() {
        view.showLoading()

        val date = Date().time
        locationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                showAddDialog(date, location)
            } else {
                showAddDialog(date, getDefaultLocation())
            }
        }.addOnFailureListener {
            showAddDialog(date, getDefaultLocation())
        }
    }

    private fun showAddDialog(date: Long, location: Location) {
        view.hideLoading()
        view.showAddDialog(Todo(name = "", date = date, latitude = location.latitude, longitude = location.longitude))
    }

    private fun getDefaultLocation() = Location("dummy_provider")

    fun onTodoAdded(todo: Todo) {
        repository.insertTodo(todo).subscribeOn(processScheduler).observeOn(uiScheduler).subscribe { rowId -> todo.id = rowId }
                .addTo(disposables)
    }

    fun onTodoEdited(todo:Todo) {
        repository.updateTodo(todo).subscribeOn(processScheduler).observeOn(uiScheduler).subscribe().addTo(disposables)
    }

    fun onTodoDeleted(todo:Todo) {
        repository.deleteTodo(todo).subscribeOn(processScheduler).observeOn(uiScheduler).subscribe().addTo(disposables)
    }

    fun onViewDestroyed() {
        if (!disposables.isDisposed) disposables.dispose()
    }
}