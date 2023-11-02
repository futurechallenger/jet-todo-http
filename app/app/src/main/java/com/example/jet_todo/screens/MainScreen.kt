package com.example.jet_todo.screens

import android.content.res.Configuration
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.jet_todo.R
import com.example.jet_todo.components.BottomTabBar
import com.example.jet_todo.viewmodel.SettingsViewModel
import com.example.jet_todo.components.BottomTabBar
import com.example.jet_todo.ui.theme.JettodoTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

sealed class Screens(val route: String, @StringRes val resourceId: Int) {
    object Home : Screens("home", R.string.home)
    object Detail : Screens("detail", R.string.detail)
    object Settings : Screens("settings", R.string.settings)
    object AddTodo : Screens("addTodo", R.string.add_todo)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable(route = "main") {
            val config = LocalConfiguration.current
            when (config.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> {
                    Text("Landscape")
                    DrawerScreen(outerNav = navController)
                }

                else -> {
                    TabScreen(outerNav = navController)
                }
            }
        }

        composable(route = "${Screens.Detail.route}/{todoId}") { backStackEntry ->
            DetailScreen(
                navController = navController,
                backStackEntry.arguments?.getString("todoId")?.toIntOrNull()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabScreen(outerNav: NavHostController) {
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
                if (navBackStackEntry?.destination?.route != "home") null else IconButton(
                    onClick = {
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
                    outerNav = outerNav,
                    navController = navController
                )
            }
            composable(Screens.Settings.route) {
                SettingsScreen(
                    navController = navController,
                )
            }
            composable(
                route = Screens.AddTodo.route, // TODO: enter & exit transition?
            ) { AddTodoScreen(navController = navController) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerScreen(outerNav: NavHostController?) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(drawerContent = {
        ModalDrawerSheet {
            Text(text = "Drawer Title", modifier = Modifier.padding(16.dp))
            Divider()
            NavigationDrawerItem(
                label = { Text(text = "Drawer Item `") },
                selected = false,
                onClick = { /*TODO*/ })
            NavigationDrawerItem(
                label = { Text(text = "Drawer Item 2") },
                selected = false,
                onClick = { /*TODO*/ })
        }
    }) {
        Scaffold(
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = { Text("Show drawer") },
                    icon = { Icon(Icons.Filled.Add, contentDescription = "") },
                    onClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                )
            }
        ) { contentPadding ->
            Box(modifier = Modifier.padding(contentPadding)) {
                Text("Something new")
            }
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
