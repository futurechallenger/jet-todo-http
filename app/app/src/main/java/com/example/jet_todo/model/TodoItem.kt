package com.example.jet_todo.model

import com.google.gson.annotations.SerializedName

data class TodoItem(
//    @field:SerializedName("id")
    var id: Int? = null,

//    @field:SerializedName("title")
    var title: String? = null,

//    @field:SerializedName("description")
    var description: String? = null,

//    @field:SerializedName("status")
    var status: Int?
)
