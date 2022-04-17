package com.example.todoapp.di

import android.app.Application
import android.content.Context
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.todoapp.data.TodoDao
import com.example.todoapp.data.TodoDatabase
import com.example.todoapp.todo.TodoRepository
import com.example.todoapp.todo.TodoViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesTodoDatabase(@ApplicationContext context: Context): TodoDatabase =
        Room.databaseBuilder(
            context,
            TodoDatabase::class.java,
            "todo_db"
        ).build()

    @Provides
    fun providesUserDao(todoDatabase: TodoDatabase): TodoDao = todoDatabase.dao

    @Provides
    @Singleton
    fun providesTodoRepo(todoDao: TodoDao): TodoRepository = TodoRepository(todoDao)

}
