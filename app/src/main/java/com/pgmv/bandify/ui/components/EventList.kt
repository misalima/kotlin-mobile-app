package com.pgmv.bandify.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
fun EventList(
    eventList: List<Event>,
    selectedEvent: Event?,
    onEventSelected: (Event) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .heightIn(min = 0.dp, max = 200.dp)
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            eventList.forEach { event ->
                Button(
                    onClick = {
                        onEventSelected(event)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (event == selectedEvent) MaterialTheme.colorScheme.primary else Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 2.dp,
                        pressedElevation = 2.dp
                    )
                ) {
                    Text(
                        text = event.title,
                        color = if (event == selectedEvent) Color.White else Color.Gray
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventListPreview() {
    BandifyTheme {
        val eventList = listOf(
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
        )
        EventList(
            eventList = eventList,
            selectedEvent = eventList[0],
            onEventSelected = {}
        )
    }
}