package com.example.jet_todo.api

import com.example.jet_todo.model.TodoItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface TodoApi {
    @Headers("Accept: application/json")
    @GET("items/{status}/status")
    suspend fun getItemsByStatus(@Path("status") status: String): List<TodoItem>

    @Headers("Accept: application/json")
    @GET("items/{itemId}")
    suspend fun getItem(@Path("itemId") itemId: Int): TodoItem?
}