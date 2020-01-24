package com.carles.todo.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.carles.todo.R
import com.carles.todo.model.Todo
import com.carles.todo.ui.inflate
import com.carles.todo.ui.toFormattedDateString
import com.carles.todo.ui.toFormattedString
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_todo.*

class TodoAdapter(private val onEditClicked: (Todo) -> Unit, private val onDeleteClicked: (Todo) -> Unit) :
        RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    var items = mutableListOf<Todo>()
        set(value) {
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(parent.inflate(R.layout.item_todo))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindView(items.get(position))
    }

    fun deleteItem(item: Todo) {
        items.indexOf(item).let { position ->
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        private val resources = containerView.resources

        init {
            todo_edit_button.setOnClickListener { onEditClicked(items.get(adapterPosition)) }
            todo_delete_button.setOnClickListener { onDeleteClicked(items.get(adapterPosition)) }
        }

        fun onBindView(item: Todo) {
            todo_name_textview.text = item.name
            todo_duedate_textview.text = String.format(resources.getString(R.string.main_todo_duedate), item.date.toFormattedDateString())
            todo_location_textview.text = String.format(resources.getString(R.string.main_todo_location), item.latitude, item.longitude)
        }
    }

}