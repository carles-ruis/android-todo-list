package com.carles.todo.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import com.carles.todo.R
import com.carles.todo.model.Todo
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity(), MainView, AddTodoDialogFragment.AddTodoListener {

    private val REQUEST_PERMISSION_FOR_ADD_LOCATION = 100
    private lateinit var adapter: TodoAdapter
    private val presenter: MainPresenter by inject { parametersOf(this) }

    private val showEditDialog: (Todo) -> Unit = { Toast.makeText(this, "EDIT", Toast.LENGTH_SHORT).show() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        presenter.onViewCreated()
    }

    private fun initViews() {
        adapter = TodoAdapter(showEditDialog, ::showDeleteConfirmationDialog)
        main_recyclerview.addItemDecoration(DividerItemDecoration(this, VERTICAL))
        main_recyclerview.adapter = adapter

        main_fab.setOnClickListener { onAddClicked() }
    }

    private fun onAddClicked() {
        showLoading()
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            presenter.onAddClicked()
        } else {
            requestLocationPermission(REQUEST_PERMISSION_FOR_ADD_LOCATION)
        }
    }

    private fun requestLocationPermission(requestCode: Int) {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), requestCode)
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

    override fun showAddDialog(date: Long, location: Location) {
        AddTodoDialogFragment.newInstance(date, location).show(supportFragmentManager, AddTodoDialogFragment.TAG)
    }

    private fun showDeleteConfirmationDialog(todo:Todo) {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.main_delete_confirmation_title)
            .setMessage(R.string.main_delete_confirmation_message)
            .setPositiveButton(R.string.main_delete_confirmation) { _,_ -> deleteTodo(todo) }
            .setNegativeButton(R.string.cancel) {_,_ -> }
            .show()
    }

    private fun deleteTodo(todo:Todo) {
        presenter.onTodoDeleted(todo)
        adapter.deleteItem(todo)
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun onDestroy() {
        presenter.onViewDestroyed()
        super.onDestroy()
    }

    override fun onTodoAdded(todo: Todo) {
        adapter.addItem(todo)
        presenter.onTodoAdded(todo)
    }

}