package com.example.todoapp.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo : Todo)

    @Query("Select * from Todo")
    suspend fun getTodos() : List<Todo>

    @Delete
    suspend fun deleteTodo(todo: Todo)
}