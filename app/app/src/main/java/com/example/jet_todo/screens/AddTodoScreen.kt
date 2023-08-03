package com.example.jet_todo.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jet_todo.R
import com.example.jet_todo.viewmodel.TodoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTodoScreen(
    todoViewModel: TodoViewModel = hiltViewModel(),
    navController: NavController?,
) {
    var textContent = remember {
        mutableStateOf("")
    }
    var description = remember {
        mutableStateOf("")
    }

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp)
        ) {
            Text(text = "Add Todo")
            TextField(
                value = textContent.value,
                onValueChange = { textContent.value = it },
                label = { Text(text = "Label") }, modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(5.dp))

            TextField(
                value = description.value,
                onValueChange = { description.value = it },
                label = { Text(text = "Description") }, modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = {
                        navController?.popBackStack()
                        // TODO: Error handling here
                        todoViewModel.addTodo(title = textContent.value, desc = description.value)
                    },
                ) {
                    Text(text = stringResource(id = R.string.ok))
                }
            }
        }
    }
}