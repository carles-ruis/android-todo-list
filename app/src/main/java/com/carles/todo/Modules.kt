package com.carles.todo

import com.carles.todo.data.ToDoLocalDatasource
import com.carles.todo.repository.ToDoRepository
import com.carles.todo.ui.main.MainPresenter
import com.carles.todo.ui.main.MainView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.qualifier.named
import org.koin.dsl.module

private val uiScheduler = named("uiScheduler")
private val processScheduler = named("processScheduler")

val appModule = module {

    single { ToDoLocalDatasource() }
    single { ToDoRepository(get()) }
    single(uiScheduler) { AndroidSchedulers.mainThread() }
    single(processScheduler) { Schedulers.io()}

    factory { (view: MainView) -> MainPresenter(view, get(uiScheduler), get(processScheduler), get()) }
}