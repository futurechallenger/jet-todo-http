package com.example.jet_todo.screens

import android.accounts.NetworkErrorException
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jet_todo.components.SettingsRow
import com.example.jet_todo.viewmodel.SettingsViewModel
import com.example.jet_todo.components.SettingsRow
import com.example.jet_todo.ui.theme.JettodoTheme
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.yield
import okhttp3.internal.wait
import java.lang.ArithmeticException
import java.lang.IndexOutOfBoundsException

@Composable
fun SettingsScreen(
    navController: NavController?,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val sortByOption = settingsViewModel.sortByOptions.value
    val selectedSortOption = settingsViewModel.selectedSortOption.value

    val scope = rememberCoroutineScope()

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

        Row {
            Button(onClick = {
                println("Try flow...")
                val fl = (1..5).asFlow()
                scope.launch {
                    fl.reduce { a, b -> a + b }
                }

            }) {
                Text(text = "Try flow", color = Color.White)
            }

            Button(
                modifier = Modifier.padding(start = 20.dp),
                onClick = {
                    println("Start")

                    scope.launch {
                        launch {
                            printSomething("launched 1")
                        }

                        println("launched 2")

                        launch {
                            printSomething("launched 3")
                            printSomething("launched 4")
                        }
                    }

                    println("End")
                }) {
                Text(text = "Try Coroutine", color = Color.White)
            }

            Button(
                modifier = Modifier.padding(start = 20.dp),
                onClick = {
                    println("Start")

                    scope.launch {
                        val start = System.currentTimeMillis()

//                        val firstUserDeferred: Deferred<UserInfo> = async {
//                            fetchUser(1)
//                        }
//
//                        val secondUserDeferred: Deferred<UserInfo> = async {
//                            fetchUser(2)
//                        }
//
//                        val firstUser = firstUserDeferred.await()
//                        val secondUser = secondUserDeferred.await()

                        val (firstUser, secondUser) = listOf(1, 2).map {
                            async(Dispatchers.IO) {
                                fetchUser(it)
                            }
                        }.awaitAll()


                        println("Fetch user completed: first: ${firstUser.userId}, second: ${secondUser.userId}")
                        println("Execution time: ${System.currentTimeMillis() - start}")
                    }

                    scope.launch(Dispatchers.Main) {

                    }

                    println("End")
                }) {
                Text(text = "Coroutine Async", color = Color.White)
            }
        }
        Row {
            Button(onClick = {
                val handler =
                    CoroutineExceptionHandler { _, exception -> println("CoroutineExceptionHandler caught $exception") }
                GlobalScope.launch() {

//                    val job = scope.launch(handler) {
//                        throw IndexOutOfBoundsException()
//                    }
//                    job.join()
//
//                    println("Joined failed job")
//
//                    val deferred = scope.async(handler) {
//                        println("Throw exception in async")
//                        throw ArithmeticException()
//                    }
//
//                    try {
//                        deferred.await()
//                    } catch (e: ArithmeticException) {
//                        println("Caught ArithmeticException")
//                    }
                    try {
                        supervisorScope {
                            val usersDeferred = async { getUsers() }
                            val todoListDeferred = async { getTodoList() }

                            val users = try {
                                usersDeferred.await()
                            } catch (e: Exception) {
                                println("get users error")
                                null
                            }

                            val todoList = try {
                                todoListDeferred.await()
                            } catch (e: Exception) {
                                println("get todo list error")
                                emptyList<Int>()
                            }
                        }
                    } catch (e: Exception) {
                        println("Get users & todo list caught exception: $e")
                    }
                }
            }) {
                Text("Exception", color = Color.White)
            }

            Button(onClick = {
                val handler =
                    CoroutineExceptionHandler { _, exception -> println("CoroutineExceptionHandler caught $exception") }

                scope.launch {
//                    val job = launch {
//                        try {
//
//                            repeat(1_000) {
//                                println("Coroutine running... $it")
//                                delay(500L)
//                            }
//                        } catch (e: Exception) {
//                            println("Caught exception: $e")
//                        }
//                    }
//
//                    delay(1000L)
//
//                    println("MAIN: I'm no waiting")
//
//                    job.cancelAndJoin()
//
//                    println("main: quited")

//                    val startTime = System.currentTimeMillis()
//                    val job = launch(Dispatchers.Default) {
//                        var nextPrintTime = startTime
//                        var i = 0
//                        while (i < 5) { // computation loop, just wastes CPU
//                            // print a message twice a second
//                            if (System.currentTimeMillis() >= nextPrintTime) {
//                                println("job: I'm sleeping ${i++} ...")
//                                nextPrintTime += 500L
//                                delay(1L)
//                            }
//                        }
//                    }
//                    delay(1300L) // delay a bit
//                    println("main: I'm tired of waiting!")
//                    job.cancelAndJoin() // cancels the job and waits for its completion
//                    println("main: Now I can quit.")
                    val startTime = System.currentTimeMillis()
                    val job = launch(Dispatchers.Default) {
                        var nextPrintTime = startTime
                        var i = 0
                        while (true) {
                            yield()
                            // print a message twice a second
                            if (System.currentTimeMillis() >= nextPrintTime) {
                                println("job: I'm sleeping ${i++} ...")
                                nextPrintTime += 500L
                            }
                        }
                    }
                    delay(1300L) // delay a bit
                    println("main: I'm tired of waiting!")
                    job.cancelAndJoin() // cancels the job and waits for its completion
                    println("main: Now I can quit.")

                }
            }) {
                Text("Cancel coroutine", color = Color.White)
            }
        }
    }
}

suspend fun getUsers() {
    delay(200L)
    println("fetching users")
}

suspend fun getTodoList() {
    throw NetworkErrorException()
}

suspend fun printSomething(something: String) {
    println(something ?: "something launched")
}

data class UserInfo(var userId: Int)

suspend fun fetchUser(userId: Int): UserInfo {
    delay(1000L)
    return UserInfo(userId)
}

@Composable
@Preview(showBackground = true)
fun SettingsScreensPreview() {
    JettodoTheme() {
        SettingsScreen(null)
    }
}
