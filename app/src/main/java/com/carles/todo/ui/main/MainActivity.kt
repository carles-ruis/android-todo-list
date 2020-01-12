package com.carles.todo.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import com.carles.todo.R
import com.carles.todo.model.ToDo
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.util.*

class MainActivity : AppCompatActivity(), MainView {

    private lateinit var adapter : TodoAdapter
    private val presenter : MainPresenter by inject { parametersOf(this) }
    private val showEditDialog : (ToDo) -> Unit = { Toast.makeText(this, "EDIT", Toast.LENGTH_SHORT).show() }
    private val showDeleteDialog : (ToDo) -> Unit = { Toast.makeText(this, "DELETE", Toast.LENGTH_SHORT).show() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        adapter = TodoAdapter(showEditDialog, showDeleteDialog)
        main_recyclerview.addItemDecoration(DividerItemDecoration(this, VERTICAL))
        main_recyclerview.adapter = adapter

        populateToDoList()

        main_fab.setOnClickListener { presenter.onAddClicked() }
    }

    private fun populateToDoList() {
        adapter.addItem(ToDo("Cocaina liquida","12-1-2020", "Sant antoni"))
        adapter.addItem(ToDo("Body pump","12-1-2020", "Sant antoni"))
        adapter.addItem(ToDo("Prova de codi", "12-1-2020", "Casa"))
        adapter.addItem(ToDo("Robar menjar", "12-1-2020", "Casa la mama"))
        adapter.addItem(ToDo("Concert", "18-1-2020", "Razzmatazz"))
        adapter.addItem(ToDo("Rentadora","12-1-2020", "Casa"))
        adapter.addItem(ToDo("Besitos a Tati", "12-1-2020", "Casa"))

    }

    override fun showAddDialog(calendar: Calendar, location:String) {
        AddTodoDialogFragment.newInstance(calendar, location).show(supportFragmentManager, AddTodoDialogFragment.TAG)
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun onDestroy() {
        presenter.onViewDestroyed()
        super.onDestroy()
    }

}