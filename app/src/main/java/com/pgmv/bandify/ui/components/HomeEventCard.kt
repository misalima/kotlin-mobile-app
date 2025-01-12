package com.pgmv.bandify.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.pgmv.bandify.R
import com.pgmv.bandify.domain.Event
import com.pgmv.bandify.ui.theme.BandifyTheme
import com.pgmv.bandify.ui.theme.Grey60
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun HomeEventCard(
    event: Event
) {
    val formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", Locale("pt", "BR"))
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        ),
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Box(
                modifier = Modifier
                    .weight(1.5F)
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    model = if (event.imageUrl != "") event.imageUrl else R.drawable.logo_bandify,
                    contentDescription = "Imagem do evento",
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp)
                    .weight(4F),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "${LocalDate.parse(event.date).format(formatter)}, ${event.time}",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight(700),
                    color = Grey60
                )
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight(700),
                    color = MaterialTheme.colorScheme.onSecondary
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Local do evento",
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                    Column(
                        modifier = Modifier
                            .weight(2.5F)
                            .fillMaxWidth().padding(start = 4.dp),
                    ) {
                        Text(
                            text = "${event.place}, ${event.address}",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight(400),
                            color = Grey60
                        )
                    }


                }
            }
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.weight(1F)
            ) {
                Icon(
                    imageVector = Icons.Default.LibraryMusic,
                    contentDescription = "Músicas do evento",
                    tint = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeEventCardPreview() {
    BandifyTheme {
        Scaffold { innerPading ->
            Surface(
                modifier = Modifier.padding(innerPading)
            ) {
                Column (
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    HomeEventCard(
                        event = Event(
                            title = "Evento de teste",
                            place = "Local de teste",
                            time = "12:00",
                            address = "Rua de teste",
                            id = 10,
                            date = "2024-12-12",
                            userId = 1,
                            imageUrl = "",
                            createdAt = "2023-12-12",
                            updatedAt = "2024-12-01"
                        )
                    )

                    HomeEventCard(
                        event = Event(
                            title = "Grande título para teste de nome de evento",
                            place = "Local de teste",
                            time = "12:00",
                            address = "Endereço longo para teste como costumam ser os endereços",
                            id = 10,
                            date = "2024-12-12",
                            userId = 1,
                            imageUrl = "",
                            createdAt = "2023-12-12",
                            updatedAt = "2024-12-01"
                        )
                    )
                }


            }
        }

    }
}