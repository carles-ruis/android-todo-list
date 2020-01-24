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

class TodoListPresenter(
    val view: TodoListView,
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
                navigateToAddTodo(date, location)
            } else {
                navigateToAddTodo(date, getDefaultLocation())
            }
        }.addOnFailureListener {
            navigateToAddTodo(date, getDefaultLocation())
        }
    }

    private fun navigateToAddTodo(date: Long, location: Location) {
        view.hideLoading()
        view.navigateToAddTodo(Todo(name = "", date = date, latitude = location.latitude, longitude = location.longitude))
    }

    private fun getDefaultLocation() = Location("dummy_provider")

    fun onTodoDeleted(todo:Todo) {
        repository.deleteTodo(todo).subscribeOn(processScheduler).observeOn(uiScheduler).subscribe().addTo(disposables)
    }

    fun onViewDestroyed() {
        if (!disposables.isDisposed) disposables.dispose()
    }
}