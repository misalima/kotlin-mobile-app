package com.pgmv.bandify.ui.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.pgmv.bandify.database.DatabaseHelper

@Composable
fun HomeScreen(dbHelper: DatabaseHelper? = null) {
    //Now you can use dbHelper.[dao] to access the database

    Text(text = "Home Screen")
}

@Preview (
    showBackground = true,
    showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}