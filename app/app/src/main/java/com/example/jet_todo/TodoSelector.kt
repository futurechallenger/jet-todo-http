package com.example.jet_todo

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun TodoSelector() {
    var selected by remember { mutableStateOf(false) }
    var selText = if (selected) "Selected" else " "

    Button(onClick = { selected = !selected }) {
        Text(text = selText)
    }
}