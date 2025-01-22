package com.pgmv.bandify.ui.screen



import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.pgmv.bandify.database.DatabaseHelper
import com.pgmv.bandify.ui.theme.BandifyTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun ArquivosScreen(
    dbHelper: DatabaseHelper? = null,
    navController: NavController
){
    val fileDao = dbHelper?.fileDao()

    // Armazena a categoria dos arquivos
    var selectedCategory by remember { mutableStateOf("Todos") }


    val filesFlow = remember(selectedCategory) {
        when (selectedCategory) {
            "PDF" -> fileDao?.getFilesByCategory("PDF") ?: flowOf(emptyList())
            "Imagem" -> fileDao?.getFilesByCategory("Imagem") ?: flowOf(emptyList())
            else -> fileDao?.getAllFiles() ?: flowOf(emptyList())
        }
    }

}

@Preview (
    showBackground = true,
    showSystemUi = true)

@Composable
fun ArquivosScreenPreview() {
    BandifyTheme {
        ArquivosScreen(navController = NavController(context = androidx.compose.ui.platform.LocalContext.current))
    }
}