package com.example.jet_todo.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jet_todo.model.ConfigOption
import com.example.jet_todo.repositories.SortBy
import com.example.jet_todo.repositories.TodoRepository
import com.example.jet_todo.repositories.UserPreferencesRepository
//import com.example.jettodo.data.ConfigOption
//import com.example.jettodo.data.SortBy
//import com.example.jettodo.data.TodoRepository
//import com.example.jettodo.data.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * TODO: Make this a expandable list
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: TodoRepository,
    private val settingsRepository: UserPreferencesRepository,
    @ApplicationContext appContext: Context
) : ViewModel() {
    private var _selectedOption = mutableStateOf<ConfigOption?>(null)
    private var _sortByOptions = mutableStateOf(
        ConfigOption(
            name = "Sort by",
            code = 1,
            subConfigs = listOf(
                // TODO: Add manual as an option
                ConfigOption(name = "title", code = 2, subConfigs = null, parentCode = 1),
                ConfigOption(
                    name = "Created date",
                    code = 3,
                    subConfigs = null,
                    parentCode = 1
                ),
                ConfigOption(
                    name = "Updated date",
                    code = 4,
                    subConfigs = null,
                    parentCode = 1
                )
            ),
            parentCode = null
        )
    )
    private var _hideOrShowCompleted = mutableStateOf(false)

    val sortByOptions = _sortByOptions
    val selectedSortOption = _selectedOption

    fun selectSortByOption(option: ConfigOption? = null) {
        if (option == null) {
            // TODO: Handle this error
            return
        }

        selectedSortOption.value = option
        settingsRepository.setSortByValue(SortBy.findByValue(option!!.code) ?: SortBy.BY_TITLE)
    }

    fun selectSortOptionCode(code: Int?) {
        val sortCode = code ?: SortBy.BY_TITLE.by

        selectedSortOption.value =
            ConfigOption(name = "", code = sortCode, subConfigs = null, parentCode = null)
    }

    fun readOptionCode(): Int? {
        return settingsRepository.readSortBy()
    }

    fun hideOrShowOptions(hideOrShow: Boolean) {
        _hideOrShowCompleted.value = hideOrShow
    }

    fun deleteAll() {
        viewModelScope.launch {
//            repository.deleteAll()
        }
    }
}