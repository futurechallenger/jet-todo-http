package com.example.jet_todo.di

import android.content.Context
//import com.example.jet_todo.data.TodoDao
//import com.example.jet_todo.data.TodoDatabase
//import com.example.jet_todo.data.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {
//    @Provides
//    fun provideRoomDB(@ApplicationContext context: Context): TodoDatabase {
//        return TodoDatabase.getInstance(context)
//    }

//    @Provides
//    fun provideTodoDao(db: TodoDatabase): TodoDao {
//        return db.todoDao
//    }

//    @Provides
//    fun provideRepository(todoDao: TodoDao): TodoRepository {
//        return TodoRepository(todoDao)
//    }
}