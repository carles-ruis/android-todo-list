package com.carles.todo

import androidx.room.Room
import com.carles.todo.data.TodoDatabase
import com.carles.todo.repository.TodoRepository
import com.carles.todo.ui.main.TodoPresenter
import com.carles.todo.ui.main.TodoListPresenter
import com.carles.todo.ui.main.TodoListView
import com.carles.todo.ui.main.TodoView
import com.google.android.gms.location.LocationServices
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

private val uiScheduler = named("uiScheduler")
private val processScheduler = named("processScheduler")

val appModule = module {

    single { Room.databaseBuilder(androidContext(), TodoDatabase::class.java, "todo_database").build() }
    single { get<TodoDatabase>().dao() }
    single { TodoRepository(get()) }
    single(uiScheduler) { AndroidSchedulers.mainThread() }
    single(processScheduler) { Schedulers.io() }
    single { LocationServices.getFusedLocationProviderClient(androidApplication()) }

    factory { (view: TodoListView) -> TodoListPresenter(view, get(), get(uiScheduler), get(processScheduler), get()) }
    factory { (view: TodoView) -> TodoPresenter(view, get(uiScheduler), get(processScheduler), get(), androidContext()) }
}
