package com.example.jet_todo.viewmodel

import android.app.AlarmManager
import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jet_todo.model.TodoItem
import com.example.jet_todo.repositories.TodoRepository
import com.example.jet_todo.lib.TodoItemStatus
import com.example.jet_todo.repositories.SortBy
import com.example.jet_todo.repositories.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    @ApplicationContext appContext: Context,
    private val repository: TodoRepository,
    private val settingsRepository: UserPreferencesRepository,
) : ViewModel() {
    private var _todoListState = mutableStateOf<List<TodoItem>>(listOf())
    private var _preAddTodo = mutableStateOf<TodoItem?>(null)

    val todoListState: State<List<TodoItem>> = _todoListState
    val preAddTodo: State<TodoItem?> = _preAddTodo

    fun getAllTodoItems(orderBy: Int) {
        viewModelScope.launch {
            try {
                _todoListState.value = repository.getAllTodoItems()
            } catch (e: Exception) {
                // TODO: Error handling
                Log.e("TodoViewModel", "error: $e")
            }
        }
    }

    fun readSortOption(): Int {
        val code = settingsRepository.readSortBy()
        return code ?: SortBy.BY_TITLE.by
    }

    fun getInitialTodoItems(orderBy: Int) {
        viewModelScope.launch {
            try {
                _todoListState.value = repository.getInitialTodoItems()
            } catch (e: Exception) {
                // TODO: error handling
                Log.e("TodoViewModel", "error: $e")
            }
        }
    }

    fun preAddTodo() {
        if (_todoListState.value.isNullOrEmpty() && _preAddTodo.value == null) {
            val placeholder = TodoItem(-1, "", "", 0)
            _preAddTodo.value = placeholder
            _todoListState.value = listOf(placeholder)
        } else {
            _preAddTodo.value = null
            _todoListState.value =
                _todoListState.value.filter { it.id != -1 }
        }
    }

//    fun addTodo(title: String, desc: String) {
//        viewModelScope.launch {
//            repository.insert(
//                TodoItem(
//                    id = 0,
//                    title = title,
//                    description = desc,
//                    status = TodoItemStatus.INIT.status,
//                    createdAt = Date(),
//                    updatedAt = null
//                )
//            )
//        }
//    }

//    fun markAsDone(todoItem: TodoItem) {
//        viewModelScope.launch {
//            todoItem.status = TodoItemStatus.COMPLETED.status
//            todoItem.updatedAt = Date()
//            repository.update(todoItem)
//        }
//    }
//
//    fun delete(todoItem: TodoItem) {
//        viewModelScope.launch {
//            todoItem.status = TodoItemStatus.DELETED.status
//            todoItem.updatedAt = Date()
//            repository.update(todoItem)
//        }
//    }
//
//    private fun fromSortBy(sortBy: SortBy?): String {
//        if (sortBy == null) return "title"
//
//        val ret = when (sortBy) {
//            SortBy.BY_TITLE -> "title"
//            SortBy.BY_UPDATED_DATE -> "updated_at"
//            SortBy.BY_CREATED_DATE -> "created_at"
//            else -> "title"
//        }
//
//        return ret
//    }
}