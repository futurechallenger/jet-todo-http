package com.example.jet_todo.repositories

import com.example.jet_todo.api.TodoApi
import com.example.jet_todo.model.TodoItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TodoRepository @Inject constructor(private val todoApi: TodoApi) {
    suspend fun getAllTodoItems(): List<TodoItem> {
        return todoApi.getItemsByStatus(status = "all")
    }

    suspend fun getInitialTodoItems(): List<TodoItem> {
        return todoApi.getItemsByStatus(status = "initial")
    }
}