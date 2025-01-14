package com.pgmv.bandify.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.pgmv.bandify.ui.theme.BandifyTheme

@Composable
fun DeleteConfirmationDialog(
    item: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Confirmar Exclusão")
        },
        text = {
            Text("Tem certeza que deseja excluir '$item' ?")
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text(text = "Excluir")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(text = "Cancelar")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DeleteConfirmationDialogPreview() {
    BandifyTheme {
        DeleteConfirmationDialog(
            item = "Raridade",
            onConfirm = {},
            onDismiss = {}
        )
    }
}