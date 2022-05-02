package tech.henriquedev.todoapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import tech.henriquedev.todoapp.data.model.Priority

@Entity(tableName = "todo_table")
data class TodoData(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var priority: Priority,
    var description: String
)
