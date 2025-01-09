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
import androidx.room.Database
import com.pgmv.bandify.database.DatabaseHelper
import com.pgmv.bandify.domain.File
import com.pgmv.bandify.ui.theme.BandifyTheme

@Composable
fun ArquivosScreen(dbHelper: DatabaseHelper? = null) {
    val fileDao = dbHelper?.fileDao()
    // fileDao now can be used for database operations

   Text(text = "Arquivos")
}

@Preview (
    showBackground = true,
    showSystemUi = true)
@Composable
fun ArquivoScreenPreview() {
    BandifyTheme {
        ArquivosScreen()
    }
}