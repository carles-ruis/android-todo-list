package com.carles.todo.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import com.carles.todo.R
import com.carles.todo.model.Todo
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_todo_list.*
import kotlinx.android.synthetic.main.view_progress.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class TodoListFragment : Fragment(), TodoListView {

    companion object {
        private const val REQUEST_PERMISSION_FOR_ADD_LOCATION = 100
    }

    private lateinit var adapter: TodoAdapter
    private val presenter: TodoListPresenter by inject { parametersOf(this) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)=
            inflater.inflate(R.layout.fragment_todo_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TodoAdapter(::navigateToEditTodo, ::showDeleteConfirmationDialog)
        todolist_recyclerview.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        todolist_recyclerview.adapter = adapter
        todolist_fab.setOnClickListener { checkPermissionsAndAddTodo() }

        presenter.onViewCreated()
    }

    private fun checkPermissionsAndAddTodo() {
        val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION
        activity?.let {
            if (ContextCompat.checkSelfPermission(it, locationPermission) == PackageManager.PERMISSION_GRANTED) {
                presenter.onAddClicked()
            } else {
                ActivityCompat.requestPermissions(it, arrayOf(locationPermission), REQUEST_PERMISSION_FOR_ADD_LOCATION)
            }
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
        adapter.items = todos.toMutableList()
    }

    override fun navigateToAddTodo(todo: Todo) {
        Navigation.findNavController(view!!).navigate(TodoListFragmentDirections.actionTodoListFragmentToAddTodoFragment(todo))
    }

    private fun navigateToEditTodo(todo: Todo) {
        Navigation.findNavController(view!!).navigate(TodoListFragmentDirections.actionTodoListFragmentToEditTodoFragment(todo))
    }

    private fun showDeleteConfirmationDialog(todo: Todo) {
        MaterialAlertDialogBuilder(context)
                .setTitle(R.string.main_delete_title)
                .setMessage(R.string.main_delete_message)
                .setPositiveButton(R.string.main_delete_confirm) { _, _ -> deleteTodo(todo) }
                .setNegativeButton(R.string.cancel) { _, _ -> }
                .show()
    }

    private fun deleteTodo(todo: Todo) {
        presenter.onTodoDeleted(todo)
        adapter.deleteItem(todo)
    }

    override fun showLoading() {
        progress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress.visibility = View.GONE
    }

    override fun onDestroy() {
        presenter.onViewDestroyed()
        super.onDestroy()
    }
}