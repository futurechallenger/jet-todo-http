package com.example.jet_todo.model

import com.google.gson.annotations.SerializedName

data class TodoItem(
    @field:SerializedName("id")
    var id: Int? = null,

    var title: String? = null,

    var description: String? = null,

    var status: Int?
)
