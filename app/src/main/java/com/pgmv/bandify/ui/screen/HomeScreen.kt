package com.pgmv.bandify.ui.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreen(setScreenTitle: (String) -> Unit) {
    setScreenTitle("Home")
    Text(text = "Home Screen")
}

@Preview (
    showBackground = true,
    showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(setScreenTitle = {})
}