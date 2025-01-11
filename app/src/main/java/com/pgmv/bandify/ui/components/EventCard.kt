package com.pgmv.bandify.ui.components

import android.location.Address
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pgmv.bandify.ui.theme.BandifyTheme

@Composable
fun EventCard(
    id: String,
    title: String,
    time: String,
    place: String,
    address: String
) {
 Card (
     modifier = Modifier.padding(horizontal = 24.dp),
     colors = CardDefaults.cardColors(
         containerColor = MaterialTheme.colorScheme.onPrimary
     ),
 ) {
     Column(modifier = Modifier
         .fillMaxWidth()
         .padding(vertical = 16.dp, horizontal = 24.dp),
         verticalArrangement = Arrangement.spacedBy(8.dp)) {
         Column {
             Text(
                 text = "Nome do evento:",
                 style = MaterialTheme.typography.labelSmall,
                 fontWeight = FontWeight(400),
                 color = MaterialTheme.colorScheme.onSecondary
             )
             Text(text = title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight(700))
         }
         Row (
             modifier = Modifier.fillMaxWidth()
         ) {
             Column (
                 modifier = Modifier
                     .weight(2F)
                     .fillMaxWidth()
             ) {
                 Text(
                     text = "Local:",
                     style = MaterialTheme.typography.labelSmall,
                     fontWeight = FontWeight(400),
                     color = MaterialTheme.colorScheme.onSecondary
                 )
                 Text(text = place, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSecondary, fontWeight = FontWeight(500))
             }

             Column (
                 modifier = Modifier
                     .weight(1F)
                     .fillMaxWidth()
             ) {
                 Text(
                     text = "Horário:",
                     style = MaterialTheme.typography.labelSmall,
                     fontWeight = FontWeight(400),
                     color = MaterialTheme.colorScheme.onSecondary
                 )
                 Text(text = time, style = MaterialTheme.typography.bodyMedium,  color = MaterialTheme.colorScheme.onSecondary, fontWeight = FontWeight(500))
             }
         }
         Column {
             Text(
                 text = "Endereço:",
                 style = MaterialTheme.typography.labelSmall,
                 fontWeight = FontWeight(400),
                 color = MaterialTheme.colorScheme.onSecondary
             )
             Text(text = address, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSecondary, fontWeight = FontWeight(400))
         }
         TextButton(onClick = { /* TODO: Handle button click */ }, modifier = Modifier
             .align(Alignment.CenterHorizontally)) {
             Text(text = "ver músicas do evento")
         }
     }
 }
}

@Preview
@Composable
fun EventCardPreview() {
    BandifyTheme {
        EventCard(
            id = "1",
            title = "Evento 1",
            time = "HH:MM",
            place = "Local do Evento",
            address = "Endereço do Evento"
        )
    }
}