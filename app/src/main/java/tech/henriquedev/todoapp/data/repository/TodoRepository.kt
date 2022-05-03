package tech.henriquedev.todoapp.data.repository

import androidx.lifecycle.LiveData
import tech.henriquedev.todoapp.data.TodoDao
import tech.henriquedev.todoapp.data.model.TodoData

class TodoRepository(private val todoDao: TodoDao) {

    val getAllData: LiveData<List<TodoData>> = todoDao.getAllData()

    suspend fun insertData(todoData: TodoData) {
        todoDao.insertData(todoData)
    }
}