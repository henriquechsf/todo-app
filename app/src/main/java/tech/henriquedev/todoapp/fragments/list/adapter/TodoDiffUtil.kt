package tech.henriquedev.todoapp.fragments.list.adapter

import androidx.recyclerview.widget.DiffUtil
import tech.henriquedev.todoapp.data.model.TodoData

class TodoDiffUtil(
    private val oldList: List<TodoData>,
    private val newList: List<TodoData>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
                && oldList[oldItemPosition].title == newList[newItemPosition].title
                && oldList[oldItemPosition].priority == newList[newItemPosition].priority
                && oldList[oldItemPosition].description == newList[newItemPosition].description
    }
}