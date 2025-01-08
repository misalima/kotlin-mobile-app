package com.pgmv.bandify.ui.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.daysOfWeek
import com.pgmv.bandify.ui.components.Day
import com.pgmv.bandify.ui.components.DaysOfWeekTitle
import com.pgmv.bandify.ui.components.EventCard
import com.pgmv.bandify.ui.components.MonthTitle
import com.pgmv.bandify.ui.theme.BandifyTheme
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun AgendaScreen() {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(12) } // Adjust as needed
    val endMonth = remember { currentMonth.plusMonths(24) } // Adjust as needed
    val daysOfWeek = remember { daysOfWeek() }

    val selectedDay = remember { mutableStateOf<CalendarDay?>(null) }

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first()
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
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
                .padding(horizontal = 32.dp)
        ) {
            HorizontalCalendar(
                state = state,
                dayContent = { day ->
                    Day (
                        day = day,
                        isSelected = selectedDay.value == day,
                        onClick = { selectedDay.value = day }
                    )
                },
                monthHeader = {
                    MonthTitle(month = it)
                    DaysOfWeekTitle(daysOfWeek)
                }
            )
        }
        val formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", Locale("pt", "BR"))
        Text(
            text = selectedDay.value?.date?.format(formatter) ?: "Selecione uma data",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 32.dp)
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