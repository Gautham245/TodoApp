package com.example.todoapp.todo

import android.util.Log
import androidx.lifecycle.*
import com.example.todoapp.data.Todo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {

    private val _todo = MutableLiveData<List<Todo>>()
    val todos: LiveData<List<Todo>>
        get() = _todo

    fun addTodo(todo: Todo) {
        todo.let { t: Todo ->
            viewModelScope.launch(Dispatchers.IO) {
                repository.insertTodo(t)
            }
        }
    }

    fun getTodos() {
        viewModelScope.launch(Dispatchers.IO) {
            val todo = repository.getTodos()
            withContext(Dispatchers.Main){
                _todo.value = todo
            }

        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTodo(todo)
        }
    }

}