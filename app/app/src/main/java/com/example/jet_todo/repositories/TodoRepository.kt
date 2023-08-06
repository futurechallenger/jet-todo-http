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
//
//    suspend fun insert(todoItem: TodoItem): Long {
//        return dao.insert(todoItem)
//    }
//
//    suspend fun update(todoItem: TodoItem): Int {
//        return dao.update(todoItem)
//    }
//
//    suspend fun delete(todoItem: TodoItem): Int {
//        return dao.delete(todoItem)
//    }
//
//    suspend fun deleteAll() {
//        dao.deleteAll()
//    }
//
//    fun getTodoById(todoId: Long): TodoItem {
//        return dao.getTodoById(todoId)
//    }
//
//    fun getTodoByStatus(status: Int): Flow<List<TodoItem>> {
//        return dao.getTodosByStatus(status)
//    }
}