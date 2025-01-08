package com.pgmv.bandify.ui.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.pgmv.bandify.database.DatabaseHelper
import com.pgmv.bandify.domain.File
import com.pgmv.bandify.domain.User

@Composable
fun PerfilScreen() {
    Text(text = "Perfil")
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true)
@Composable
fun PerfilScreenPreview() {
    PerfilScreen()
}