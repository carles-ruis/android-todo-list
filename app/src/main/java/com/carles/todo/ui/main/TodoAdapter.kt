package com.carles.todo.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.carles.todo.R
import com.carles.todo.model.ToDo
import com.carles.todo.ui.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_todo.*

class TodoAdapter(val onEditClicked: (ToDo) -> Unit, val onDeleteClicked: (ToDo) -> Unit) :
        RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    private val items = mutableListOf<ToDo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(parent.inflate(R.layout.item_todo))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindView(items.get(position))
    }

    fun addItem(item: ToDo) {
        items.add(item)
        notifyItemChanged(itemCount - 1)
    }

    fun editItem(oldItem: ToDo, newItem: ToDo) {
        items.indexOf(oldItem).let { position ->
            items.set(position, newItem)
            notifyItemChanged(position)
        }
    }

    fun deleteItem(item: ToDo) {
        items.indexOf(item).let { position ->
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    inner class ViewHolder(override val containerView: View) :
            RecyclerView.ViewHolder(containerView), LayoutContainer {

        private val resources = containerView.resources

        init {
            todo_edit_button.setOnClickListener { onEditClicked(items.get(adapterPosition)) }
            todo_delete_button.setOnClickListener { onDeleteClicked(items.get(adapterPosition)) }
        }

        fun onBindView(item: ToDo) {
            todo_name_textview.text = item.name
            todo_duedate_textview.text = String.format(resources.getString(R.string.main_todo_duedate), item.dueDate)
            todo_location_textview.text = String.format(resources.getString(R.string.main_todo_location), item.geolocation
            )
        }
    }

}