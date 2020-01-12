package com.carles.todo.ui.main

import com.carles.todo.repository.ToDoRepository
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable

class MainPresenter(val view: MainView, val uiScheduler: Scheduler, val processScheduler: Scheduler, val repository: ToDoRepository) {


    private val disposables = CompositeDisposable()

    fun onAddClicked() {

    }

    fun onViewDestroyed() {
        if (!disposables.isDisposed) disposables.dispose()
    }

    private fun addDisposable(disposable: CompositeDisposable) {
        disposables.add(disposable)
    }
}