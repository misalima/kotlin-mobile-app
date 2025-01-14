package com.pgmv.bandify.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pgmv.bandify.domain.Event
import com.pgmv.bandify.ui.theme.BandifyTheme

@Composable
fun FilterRow(
    selectedFilter: String,
    onFilterSelected: (String) -> Unit,
    eventList: List<Event>,
    selectedEvent: Event?,
    onEventSelected: (Event) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        listOf("Todas", "Ensaio", "Prontas", "Evento").forEach { filter ->
            Button(
                onClick = {
                    onFilterSelected(filter)
                },
                modifier = Modifier.padding(2.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (filter == selectedFilter) MaterialTheme.colorScheme.primary else Color.White,
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 2.dp,
                    pressedElevation = 2.dp
                )
            ) {
                Text(
                    text = filter,
                    color = if (filter == selectedFilter) Color.White else Color.Gray
                )
            }
        }
    }

    if (selectedFilter == "Evento" && eventList.isNotEmpty()) {
        EventList(
            eventList = eventList,
            selectedEvent = selectedEvent,
            onEventSelected = onEventSelected
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FilterRowPreview() {
    BandifyTheme {
        FilterRow(
            selectedFilter = "Todas",
            onFilterSelected = {},
            eventList = listOf(
                Event(
                    id = 1,
                    title = "Evento 1",
                    date = "01/01/2021",
                    address = "Address 1",
                    place = "Place 1",
                    time = "10:00 AM",
                    userId = 1L
                ),
                Event(
                    id = 2,
                    title = "Evento 2",
                    date = "02/02/2021",
                    address = "Address 2",
                    place = "Place 2",
                    time = "11:00 AM",
                    userId = 2L
                ),
                Event(
                    id = 3,
                    title = "Evento 3",
                    date = "03/03/2021",
                    address = "Address 3",
                    place = "Place 3",
                    time = "12:00 PM",
                    userId = 3L
                )
            ),
            selectedEvent = Event(
                id = 1,
                title = "Evento 1",
                date = "01/01/2021",
                address = "Address 1",
                place = "Place 1",
                time = "10:00 AM",
                userId = 1L
            ),
            onEventSelected = {}
        )
    }
}