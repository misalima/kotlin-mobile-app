package com.pgmv.bandify.ui.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PerfilScreen() {
    Text(text = "Perfil")
}

@Preview(
    showBackground = true,
    showSystemUi = true)
@Composable
fun PerfilScreenPreview() {
    PerfilScreen()
}