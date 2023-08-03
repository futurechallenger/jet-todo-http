package com.example.jet_todo.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jet_todo.components.SettingsRow
import com.example.jet_todo.viewmodel.SettingsViewModel
import com.example.jet_todo.components.SettingsRow
import com.example.jet_todo.ui.theme.JettodoTheme

@Composable
fun SettingsScreen(
    navController: NavController?,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val sortByOption = settingsViewModel.sortByOptions.value
    val selectedSortOption = settingsViewModel.selectedSortOption.value

    LaunchedEffect(Unit) {
        settingsViewModel.selectSortOptionCode(settingsViewModel.readOptionCode())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        SettingsRow(children = listOf { Text(text = sortByOption.name) }) {
            Log.d("SettingsRow", "SettingsRow is clicked")
        }
        Divider(startIndent = 0.dp, thickness = 1.dp)

        if (!sortByOption.subConfigs.isNullOrEmpty()) {
            sortByOption.subConfigs!!.forEachIndexed { index, config ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    SettingsRow(
                        children = if (selectedSortOption?.code == config.code) listOf(
                            { Icon(Icons.Rounded.Done, contentDescription = "Done") },
                            {
                                Row(modifier = Modifier.weight(1.0f)) {
                                    Text(text = config.name)
                                }
                            }
                        ) else listOf {
                            Row(modifier = Modifier.weight(1.0f)) {
                                Text(text = config.name)
                            }
                        }
                    ) {
                        Log.i("Settings", "sub config option")
                        settingsViewModel.selectSortByOption(config)
                    }

                    if (index < sortByOption.subConfigs!!.size - 1) {
                        Divider(startIndent = 0.dp, thickness = 1.dp)
                    }
                }
            }

            Divider(startIndent = 0.dp, thickness = 1.dp)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SettingsScreensPreview() {
    JettodoTheme() {
        SettingsScreen(null)
    }
}
