package com.pgmv.bandify.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pgmv.bandify.ui.theme.BandifyTheme

@Composable
fun <T> MultiSelectDropdownSelector(
    items: List<T>,
    onItemSelected: (T) -> Unit,
    displayItems: (T) -> String,
    selectedItems: Set<T>,
) {
    var expanded by remember { mutableStateOf(false) }



    Column {
        OutlinedButton(
            onClick = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.primary
            ),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
        ) {
            Row (
                Modifier.padding(vertical = 4.dp),
            ) {
                Text(text = "Selecionar músicas para o evento", fontSize = MaterialTheme.typography.bodyMedium.fontSize)
                Icon(Icons.Filled.ArrowDropDown, contentDescription = "Expandir")
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = !expanded },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = selectedItems.contains(item),
                                onCheckedChange = {
                                    onItemSelected(item)
                                }
                            )
                            Text(text = displayItems(item), style = MaterialTheme.typography.bodyLarge)
                        }
                    },
                    onClick = {
                        onItemSelected(item)
                    }
                )


            }
        }
    }
}

@Preview
@Composable
fun ButtonPreview() {
    BandifyTheme {
        var selectedItems by remember { mutableStateOf(setOf<String>()) }
        val items = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
        MultiSelectDropdownSelector(
            items = items,
            onItemSelected = {
                selectedItems = if (selectedItems.contains(it)) {
                    selectedItems - it
                } else {
                    selectedItems + it
                }
            },
            displayItems = { it },
            selectedItems = selectedItems
        )
    }
}