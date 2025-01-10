package com.pgmv.bandify.ui.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.pgmv.bandify.database.DatabaseHelper


@Composable
fun PerfilScreen(dbHelper: DatabaseHelper? = null) {
    val userDao = dbHelper?.userDao()
    //userDao now can be used for database operations

    Text(text = "Perfil")
}

@Preview(
    showBackground = true,
    showSystemUi = true)
@Composable
fun PerfilScreenPreview() {
    PerfilScreen()
}


