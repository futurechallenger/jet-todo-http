package com.example.jet_todo.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jet_todo.R
import com.example.jet_todo.viewmodel.TodoViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    todoViewModel: TodoViewModel = hiltViewModel(),
    outerNav: NavController,
    navController: NavController,
) {
    val focusRequester = remember { FocusRequester() }
    var titleInput by remember {
        mutableStateOf("")
    }
    var descInput by remember {
        mutableStateOf("")
    }

    val sortByCode = todoViewModel.readSortOption()

    LaunchedEffect(sortByCode/*, TODO: isShowAll*/) {
        todoViewModel.getInitialTodoItems(sortByCode)
    }
    val todos = todoViewModel.todoListState.value

    Column {
        Spacer(modifier = Modifier.height(16.dp))

        val interactionSource = remember { MutableInteractionSource() }
        Box(modifier = Modifier
            .clickable(
                interactionSource = interactionSource, indication = null
            ) {
                Log.d("TabBarScreens", "Box is clicked")
                // Prepare adding todo items
            }
            .fillMaxSize()
        ) {
            if (todoViewModel.todoListState.value.isNullOrEmpty()) {
                Text(
                    text = stringResource(id = R.string.all_completed),
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                todos.forEach { /*(it: TodoItem) ->*/
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(top = 10.dp, bottom = 10.dp)
                                .clickable {
                                    outerNav.navigate("${Screens.Detail.route}/${it.id}")
                                }
                        ) {
                            if (it.id!! == -1) {
                                Column {
                                    TextField(modifier = Modifier.focusRequester(focusRequester),
                                        value = titleInput,
                                        onValueChange = { titleInput = it },
                                        label = { Text(text = "title") })
                                    TextField(value = descInput,
                                        onValueChange = { descInput = it },
                                        label = { Text(text = "desc") })
                                }
                            } else {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            start = 10.dp,
                                            top = 0.dp,
                                            end = 10.dp,
                                            bottom = 0.dp
                                        ),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .padding(
                                                start = 10.dp, top = 0.dp, end = 0.dp, bottom = 0.dp
                                            )
                                            .background(Color.White)
                                            .weight(1f),
                                        horizontalAlignment = Alignment.Start
                                    ) {
                                        Text(
                                            text = it.title ?: "",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 21.sp
                                        )
                                        Text(
                                            text = it.description ?: "",
                                            fontSize = 16.sp,
                                            color = Color.Gray
                                        )
                                    }

                                    IconButton(
                                        onClick = {
//                                            todoViewModel.markAsDone(it)
                                        }) {
                                        Icon(
                                            Icons.Rounded.Done,
                                            contentDescription = stringResource(id = R.string.complete)
                                        )
                                    }

                                    IconButton(
                                        onClick = { /*todoViewModel.delete(it)*/ }) {
                                        Icon(
                                            Icons.Rounded.Delete,
                                            contentDescription = stringResource(id = R.string.delete)
                                        )
                                    }
                                }
                            }
                        }
                        Divider(modifier = Modifier.padding(start = 10.dp, end = 10.dp))
                    }
                }
            }
        }
    }
}
