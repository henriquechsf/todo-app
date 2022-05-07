package tech.henriquedev.todoapp.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tech.henriquedev.todoapp.data.TodoDatabase
import tech.henriquedev.todoapp.data.model.TodoData
import tech.henriquedev.todoapp.data.repository.TodoRepository

class TodoViewModel(application: Application) : AndroidViewModel(application) {

    private val todoDao = TodoDatabase.getDatabase(application).todoDao()
    private val repository: TodoRepository

    val getAllData: LiveData<List<TodoData>>

    init {
        repository = TodoRepository(todoDao)
        getAllData = repository.getAllData
    }

    fun insertData(todoData: TodoData) {
        viewModelScope.launch(Dispatchers.IO) {
           repository.insertData(todoData)
        }
    }

    fun updateData(todoData: TodoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(todoData)
        }
    }

    fun deleteItem(todoData: TodoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(todoData)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }
}