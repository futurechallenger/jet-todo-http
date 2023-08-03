package com.example.jet_todo.model

import com.google.gson.annotations.SerializedName

data class TodoItem(
    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("completed")
    val completed: Boolean = false
)
