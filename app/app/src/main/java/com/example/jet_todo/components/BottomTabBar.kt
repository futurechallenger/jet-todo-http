package com.example.jet_todo.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.jet_todo.screens.Screens

@Composable
fun BottomTabBar(navController: NavController, items: List<Screens>) {
    var tabIndex by remember { mutableStateOf(0) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.primary)) {
        TabRow(
            selectedTabIndex = tabIndex,
            modifier = Modifier,
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = contentColorFor(MaterialTheme.colorScheme.primary),
            indicator = @Composable { tabPositions ->
                tabPositions.forEach { println("POS: ${it.toString()}") }
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
                    color = Color.White
                )
            }
        ) {
            items.forEachIndexed { index, screen ->
                Tab(
                    text = { Text(stringResource(id = screen.resourceId)) },
                    selected = tabIndex == index,
                    onClick = {
                        tabIndex = index

                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }

                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}
