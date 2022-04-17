package com.example.todoapp.todo

import androidx.lifecycle.LiveData
import com.example.todoapp.data.Todo
import com.example.todoapp.data.TodoDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TodoRepository @Inject constructor(private val todoDao: TodoDao) {

    suspend fun insertTodo(todo : Todo){
        todoDao.insertTodo(todo)
    }

    suspend fun getTodos() : List<Todo> = withContext(Dispatchers.Main){
        todoDao.getTodos()
    }

    suspend fun deleteTodo(todo: Todo){
        todoDao.deleteTodo(todo)
    }
}