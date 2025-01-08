package com.pgmv.bandify.ui.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AgendaScreen() {

    Text(text = "Agenda Screen")
}

@Preview (
    showBackground = true,
    showSystemUi = true)
@Composable
fun AgendaScreenPreview() {
    AgendaScreen()
}