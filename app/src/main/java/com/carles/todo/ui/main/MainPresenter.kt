package com.carles.todo.ui.main

import android.location.Location
import android.util.Log
import com.carles.todo.model.Todo
import com.carles.todo.repository.TodoRepository
import com.google.android.gms.location.FusedLocationProviderClient
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.sql.RowId
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
        addDisposable(
            repository.getAllTodos().subscribeOn(processScheduler).observeOn(uiScheduler).subscribe(::onGetTodosSuccess, ::onGetTodosError)
        )
    }

    private fun onGetTodosSuccess(todos: List<Todo>) {
        view.hideLoading()
        view.showTodos(todos)
    }

    private fun onGetTodosError(throwable: Throwable) {
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
        view.showAddDialog(date, location)
    }

    private fun getDefaultLocation() = Location("dummy_provider")

    fun onTodoAdded(todo: Todo) {
        addDisposable(repository.saveTodo(todo).subscribeOn(processScheduler).observeOn(uiScheduler).subscribe { rowId -> todo.id = rowId })
    }

    fun onTodoDeleted(todo:Todo) {
        addDisposable(repository.deleteTodo(todo).subscribeOn(processScheduler).observeOn(uiScheduler).subscribe())
    }

    fun onViewDestroyed() {
        if (!disposables.isDisposed) disposables.dispose()
    }

    private fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }
}