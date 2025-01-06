package com.pgmv.bandify.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pgmv.bandify.ui.components.EventCard
import com.pgmv.bandify.ui.theme.BandifyTheme

@Composable
fun AgendaScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 32.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Calendário de Eventos",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight(700),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(horizontal = 32.dp)
                .background(MaterialTheme.colorScheme.onBackground)
        ) {
            Text(
                text = "Calendário",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight(700),
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier.padding(16.dp)
            )
        }
        Text(

            text = "DD de Mês de Ano",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(16.dp)
        )

        EventCard(
            id = "1",
            title = "Evento 1",
            time = "HH:MM",
            place = "Local do Evento",
            address = "Rua tal, numero tal"
        )
    }
}

@Preview
@Composable
fun AgendaScreenPreview() {
    BandifyTheme {
        AgendaScreen()
    }
}