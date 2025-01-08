package com.pgmv.bandify.ui.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun RepertorioScreen(setScreenTitle: (String) -> Unit) {
    setScreenTitle("Repertório")
    Text(text = "Repertório Screen")
}

@Preview (
    showBackground = true,
    showSystemUi = true)
@Composable
fun RepertorioScreenPreview() {
    RepertorioScreen(setScreenTitle = {})
}