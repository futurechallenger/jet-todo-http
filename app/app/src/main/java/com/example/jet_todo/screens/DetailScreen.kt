package com.example.jet_todo.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jet_todo.ui.theme.JettodoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController? = null, todoId: Int? = null) {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                androidx.compose.material3.Text(
                    text = "Todos > $todoId"
                )
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                titleContentColor = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary,
                navigationIconContentColor = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary,
                actionIconContentColor = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary,
            ),
            actions = {}
        )
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text =
                """
                    This is an example of a scaffold. It uses the Scaffold composable's parameters to create a screen with a simple top app bar, bottom app bar, and floating action button.

                    It also contains some basic inner content, such as this text.
                """.trimIndent(),
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun DetailScreenPreview() {
    JettodoTheme() {
        DetailScreen(null)
    }
}