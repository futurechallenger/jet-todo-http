package com.example.jet_todo.repositories

import android.content.Context
import android.provider.Settings.Global.putInt
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.sql.Types
import javax.inject.Inject
import kotlin.math.absoluteValue

private const val USER_PREFERENCES_NAME = "user_preferences"
private const val SORT_ORDER_KEY = "sort_by"

enum class SortBy(val by: Int) {
    BY_TITLE(2),
    BY_CREATED_DATE(3),
    BY_UPDATED_DATE(4);

    companion object {
        fun findByValue(value: Int): SortBy? {
            return when (value) {
                2 -> SortBy.BY_TITLE
                3 -> SortBy.BY_CREATED_DATE
                4 -> SortBy.BY_UPDATED_DATE
                else -> null
            }
        }
    }
}

class UserPreferencesRepository @Inject constructor(@ApplicationContext context: Context) {
    private val sharedPreferences =
        context.applicationContext.getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)

    private val _sortByFlow = MutableStateFlow<Int>(-1)
    val sortByFlow: StateFlow<Int?> = _sortByFlow

    private val sortOrder: Int
        get() {
            return sharedPreferences.getInt(SORT_ORDER_KEY, -1)
        }

    fun setSortByValue(sortByValue: SortBy) {
        val currentOrder = sortByFlow.value

        if (sortByValue.by == currentOrder) {
            return
        }

        updateSortBy(sortByValue.by)
        _sortByFlow.value = sortByValue.by
    }

    fun readSortBy(): Int? {
        val sortByCode = sharedPreferences.getInt(SORT_ORDER_KEY, -1)
        return if (sortByCode == -1) null else sortByCode
    }

    private fun updateSortBy(sortBy: Int) {
        sharedPreferences.edit {
            putInt(SORT_ORDER_KEY, sortBy)
        }
    }
}