package com.example.jet_todo.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.jet_todo.repositories.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddTodoViewModel @Inject constructor(private val repository: TodoRepository) : ViewModel() {
    private var _text = mutableStateOf("")
    private var _desc = mutableStateOf("")

    val text = _text
    val desc = _desc
}