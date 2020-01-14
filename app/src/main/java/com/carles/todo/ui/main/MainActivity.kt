package com.carles.todo.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import com.carles.todo.R
import com.carles.todo.model.Todo
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_progress.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity(), MainView, TodoDialogFragment.TodoDialogListener {

    private val REQUEST_PERMISSION_FOR_ADD_LOCATION = 100
    private lateinit var adapter: TodoAdapter
    private val presenter: MainPresenter by inject { parametersOf(this) }

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        presenter.onViewCreated()
    }

    private fun initViews() {
        adapter = TodoAdapter(::showEditDialog, ::showDeleteConfirmationDialog)
        main_recyclerview.addItemDecoration(DividerItemDecoration(this, VERTICAL))
        main_recyclerview.adapter = adapter

        main_fab.setOnClickListener { checkPermissionsAndAddTodo() }
    }

    private fun checkPermissionsAndAddTodo() {
        val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION
        if (ContextCompat.checkSelfPermission(this, locationPermission) == PackageManager.PERMISSION_GRANTED) {
            presenter.onAddClicked()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(locationPermission), REQUEST_PERMISSION_FOR_ADD_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_PERMISSION_FOR_ADD_LOCATION) {
            presenter.onAddClicked()
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun showTodos(todos: List<Todo>) {
        adapter.setItems(todos)
    }

    override fun showAddDialog(todo:Todo) {
        AddTodoDialogFragment.newInstance(todo).show(supportFragmentManager, AddTodoDialogFragment.TAG)
    }

    private fun showEditDialog(todo: Todo) {
        EditTodoDialogFragment.newInstance(todo).show(supportFragmentManager, EditTodoDialogFragment.TAG)
    }

    private fun showDeleteConfirmationDialog(todo:Todo) {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.main_delete_title)
            .setMessage(R.string.main_delete_message)
            .setPositiveButton(R.string.main_delete_confirm) { _, _ -> deleteTodo(todo) }
            .setNegativeButton(R.string.cancel) {_,_ -> }
            .show()
    }

    private fun deleteTodo(todo:Todo) {
        presenter.onTodoDeleted(todo)
        adapter.deleteItem(todo)
    }

    override fun showLoading() {
        progress.visibility = VISIBLE
    }

    override fun hideLoading() {
        progress.visibility = GONE
    }

    override fun onDestroy() {
        presenter.onViewDestroyed()
        super.onDestroy()
    }

    override fun onTodoAdded(todo: Todo) {
        adapter.addItem(todo)
        presenter.onTodoAdded(todo)
    }

    override fun onTodoEdited(todo : Todo) {
        adapter.editItem(todo)
        presenter.onTodoEdited(todo)
    }

}