package com.example.jet_todo.screens

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jet_todo.R
import com.example.jet_todo.components.BottomTabBar
import com.example.jet_todo.viewmodel.SettingsViewModel
import com.example.jet_todo.components.BottomTabBar
import com.example.jet_todo.ui.theme.JettodoTheme
import dagger.hilt.android.lifecycle.HiltViewModel

sealed class Screens(val route: String, @StringRes val resourceId: Int) {
    object Home : Screens("home", R.string.home)
    object Settings : Screens("settings", R.string.settings)
    object AddTodo : Screens("addTodo", R.string.add_todo)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(settingsViewModel: SettingsViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val tabBarItems = listOf(
        Screens.Home,
        Screens.Settings,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Todos"
                )
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
            ),
            actions = {
                if (navBackStackEntry?.destination?.route != "home") null else IconButton(onClick = {
                    navController.navigate(Screens.AddTodo.route)
                }) {
                    Icon(
                        Icons.Rounded.Add, contentDescription = "Add an item"
                    )
                }
            },
        )
    }, bottomBar = {
        BottomTabBar(navController = navController, items = tabBarItems)
    }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screens.Home.route,
            Modifier.padding(innerPadding)
        ) {
            composable(Screens.Home.route) {
                HomeScreen(
                    navController = navController
                )
            }
            composable(Screens.Settings.route) {
                SettingsScreen(
                    navController = navController,
                )
            }
            composable(route = Screens.AddTodo.route,
//                enterTransition = {
//                    slideIntoContainer(
//                        AnimatedContentTransitionScope.SlideDirection.Left
//                    )
//                }, exitTransition = {
//                    slideOutOfContainer(
//                        AnimatedContentTransitionScope.SlideDirection.Right
//                    )
//                }, popEnterTransition = {
//                    slideIntoContainer(
//                        AnimatedContentTransitionScope.SlideDirection.Left
//                    )
//                }, popExitTransition = {
//                    slideOutOfContainer(
//                        AnimatedContentTransitionScope.SlideDirection.Right
//                    )
//                }
            ) { AddTodoScreen(navController = navController) }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ScreensPreview() {
    JettodoTheme() {
        MainScreen()
    }
}
