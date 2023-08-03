package com.example.jet_todo.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsRow(
    modifier: Modifier? = null,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    children: List<@Composable RowScope.() -> Unit>,
    onClick: () -> Unit
) {
    val defaultModifier = Modifier
        .height(40.dp)
        .fillMaxWidth()
        .clickable {
            onClick()
        }
    val mod = modifier ?: defaultModifier

    Row(
        modifier = mod,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment
    ) {
        Row(modifier = Modifier.weight(1.0f)) {
            // TODO: This may be not necessary, but i'm gonna keep it
            children.map { it ->
                it()
            }
        }
    }
}