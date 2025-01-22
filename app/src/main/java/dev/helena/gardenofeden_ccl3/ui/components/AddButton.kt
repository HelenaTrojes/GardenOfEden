package dev.helena.gardenofeden_ccl3.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.helena.gardenofeden_ccl3.ui.theme.Green

@Composable
fun AddFloatingButton(onClick: () -> Unit) {
    androidx.compose.material3.FloatingActionButton(
        onClick = onClick,
        containerColor = Green,
        modifier = Modifier.padding(16.dp)
    ) {
        androidx.compose.material3.Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Add Entry",
            tint = Color.White
        )
    }
}
