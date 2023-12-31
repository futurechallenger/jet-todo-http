package com.example.jet_todo.di

import android.content.Context
import com.example.jet_todo.api.RetrofitInstance
import com.example.jet_todo.api.TodoApi
//import com.example.jet_todo.data.TodoDao
//import com.example.jet_todo.data.TodoDatabase
//import com.example.jet_todo.data.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit


@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    fun providesTodoApi(): TodoApi {
        return RetrofitInstance.todoApi
    }
}