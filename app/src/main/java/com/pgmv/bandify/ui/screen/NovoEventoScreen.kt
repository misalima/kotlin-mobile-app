package com.pgmv.bandify.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.pgmv.bandify.database.DatabaseHelper
import com.pgmv.bandify.database.dao.ActivityHistoryDao
import com.pgmv.bandify.database.dao.EventDao
import com.pgmv.bandify.database.dao.EventSongDao
import com.pgmv.bandify.domain.ActivityHistory
import com.pgmv.bandify.domain.Event
import com.pgmv.bandify.domain.EventSong
import com.pgmv.bandify.domain.Song
import com.pgmv.bandify.ui.components.SongsSelectionForEvent
import com.pgmv.bandify.ui.theme.Blue20
import com.pgmv.bandify.ui.theme.Grey60
import com.pgmv.bandify.utils.millisToLocalDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NovoEventoScreen(dbHelper: DatabaseHelper) {
    val eventDao = dbHelper.eventDao()
    val eventSongDao = dbHelper.eventSongDao()
    val activityHistoryDao = dbHelper.activityHistoryDao()
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedTime: String by remember { mutableStateOf("Escolha um horário") }
    val selectedSongs = remember { mutableStateOf(setOf<Song>()) }
    var title: String by remember { mutableStateOf("") }
    var place: String by remember { mutableStateOf("") }
    var address: String by remember { mutableStateOf("") }
    var titleError by remember { mutableStateOf<String?>(null) }
    var placeError by remember { mutableStateOf<String?>(null) }
    var addressError by remember { mutableStateOf<String?>(null) }
    var timeError by remember { mutableStateOf<String?>(null) }

    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }




    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp, vertical = 16.dp),
    ) {
        val focusManager = LocalFocusManager.current
        val datePickerState =
            rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis())
        var showDatePickerDialog by remember { mutableStateOf(false) }
        var showTimeInput by remember { mutableStateOf(false) }
        val formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", Locale("pt", "BR"))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            if (showDatePickerDialog) {
                DatePickerDialog(
                    onDismissRequest = {
                        showDatePickerDialog = false
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                datePickerState
                                    .selectedDateMillis?.let { millis ->
                                        selectedDate = millis.millisToLocalDate()
                                    }
                                showDatePickerDialog = false
                            }) {
                            Text(text = "Escolher data")
                        }
                    }) {
                    DatePicker(state = datePickerState)
                }
            }

            TextField(
                value = selectedDate.format(formatter),
                onValueChange = { },
                Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .onFocusEvent {
                        if (it.isFocused) {
                            showDatePickerDialog = true
                            focusManager.clearFocus(force = true)
                        }
                    },
                label = {
                    Text("Data do Evento")
                },
                readOnly = true,
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedTextColor = MaterialTheme.colorScheme.primary,
                    unfocusedContainerColor = Blue20,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                textStyle = MaterialTheme.typography.titleLarge,
                trailingIcon = {
                    IconButton(
                        onClick = {
                            showDatePickerDialog = true
                        }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar data",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.padding(8.dp))

            if (showTimeInput) {
                TimeInputComposable(
                    onConfirm = { time ->
                        selectedTime = String.format("%02d:%02d", time.hour, time.minute)
                        showTimeInput = false
                    },
                    onDismiss = {
                        showTimeInput = false
                    }
                )
            }

            // TextField para o Horário
            TextField(
                value = selectedTime,
                onValueChange = {
                    timeError =
                        if (selectedTime == "Escolha um horário") "Informe o horário do evento" else null
                },
                Modifier
                    .fillMaxWidth()
                    .onFocusEvent {
                        if (it.isFocused) {
                            showTimeInput = true
                            focusManager.clearFocus(force = true)
                        }
                    },
                label = {
                    Text("Horário do Evento")
                },
                readOnly = true,
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedTextColor = MaterialTheme.colorScheme.primary,
                    unfocusedContainerColor = Blue20,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                textStyle = MaterialTheme.typography.titleLarge,
                trailingIcon = {
                    IconButton(
                        onClick = {
                            showTimeInput = true
                        }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar horário",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                isError = timeError != null
            )
            if (timeError != null) {
                Text(
                    text = timeError!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.padding(8.dp))

            TextFieldInput(
                value = title,
                onValueChange = {
                    title = it
                    titleError = if (it.isBlank()) "Informe o nome do evento" else null
                },
                label = "Nome do evento",
                isError = titleError != null
            )
            if (titleError != null) {
                Text(
                    text = titleError!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.padding(8.dp))

            TextFieldInput(
                value = place,
                onValueChange = {
                    place = it
                    placeError = if (it.isBlank()) "Informe o local do evento" else null
                },
                label = "Local do evento",
                isError = placeError != null
            )
            if (placeError != null) {
                Text(
                    text = placeError!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.padding(8.dp))

            TextFieldInput(
                value = address,
                onValueChange = {
                    address = it
                    addressError = if (it.isBlank()) "Informe o endereço do evento" else null
                },
                label = "Endereço",
                isError = addressError != null
            )
            if (addressError != null) {
                Text(
                    text = addressError!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.padding(16.dp))

            SongsSelectionForEvent(dbHelper = dbHelper, selectedSongs = selectedSongs)

            Spacer(modifier = Modifier.padding(16.dp))
        }

        SnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Button(
            onClick = {

                titleError = if (title.isBlank()) "Informe o nome do evento" else null
                placeError = if (place.isBlank()) "Informe o local do evento" else null
                addressError = if (address.isBlank()) "Informe o endereço do evento" else null
                timeError =
                    if (selectedTime == "Escolha um horário") "Informe o horário do evento" else null

                if (titleError == null && placeError == null && addressError == null && timeError == null) {
                    saveEvent(
                        title = title,
                        selectedDate = selectedDate,
                        selectedTime = selectedTime,
                        place = place,
                        address = address,
                        selectedSongs = selectedSongs,
                        eventDao = eventDao,
                        eventSongDao = eventSongDao,
                        activityHistoryDao = activityHistoryDao,
                        coroutineScope = coroutineScope
                    )

                    title = ""
                    place = ""
                    address = ""
                    selectedSongs.value = setOf()
                    selectedDate = LocalDate.now()
                    selectedTime = "Escolha um horário"

                    coroutineScope.launch {
                        snackBarHostState.showSnackbar(
                            message = "Evento criado com sucesso!",
                            actionLabel = "Ok",
                            duration = SnackbarDuration.Short
                        )
                    }
                } else {
                    coroutineScope.launch {
                        snackBarHostState.showSnackbar(
                            message = "Preencha todos os campos corretamente",
                            actionLabel = "Ok",
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = "Criar evento",
                fontSize = MaterialTheme.typography.bodyLarge.fontSize
            )
        }


    }
}

@Composable
fun TextFieldInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            unfocusedTextColor = Grey60,
            focusedTextColor = MaterialTheme.colorScheme.primary,
            unfocusedContainerColor = Blue20,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent
        ),
        isError = isError
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeInputComposable(
    onConfirm: (TimePickerState) -> Unit,
    onDismiss: () -> Unit,
) {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )
    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.wrapContentHeight(),
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            modifier = Modifier
                .requiredWidth(360.dp)
                .heightIn(max = 568.dp),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 24.dp,
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TimeInput(
                    state = timePickerState,
                )
                Row {
                    Button(onClick = onDismiss) {
                        Text("Cancelar")
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    Button(onClick = { onConfirm(timePickerState) }) {
                        Text("Confirmar")
                    }
                }
            }
        }
    }
}

fun saveEvent(
    title: String,
    selectedDate: LocalDate,
    selectedTime: String,
    place: String,
    address: String,
    selectedSongs: MutableState<Set<Song>>,
    eventDao: EventDao,
    eventSongDao: EventSongDao,
    activityHistoryDao: ActivityHistoryDao,
    coroutineScope: CoroutineScope
) {
    val songs = selectedSongs.value
    var eventId: Long = 0
    coroutineScope.launch {
        try {
            eventId = eventDao.insertEvent(
                Event(
                    title = title,
                    date = selectedDate.toString(),
                    time = selectedTime,
                    place = place,
                    address = address,
                    userId = 1
                )
            )

            if (songs.isNotEmpty()) {
                songs.forEach { song ->
                    eventSongDao.insertEventSong(
                        EventSong(
                            eventId, song.id
                        )
                    )
                }
            }
            activityHistoryDao.insertActivity(
                activityHistory = ActivityHistory(
                    userId = 1,
                    activity = "Um novo evento foi adicionado",
                    activityType = "event",
                    createdAt = LocalDateTime.now(ZoneId.systemDefault()).toString(),
                    eventId = eventId,
                    itemName = title
                )
            )

        } catch (e: Exception) {
            Log.d("NovoEventoScreen", "Erro ao salvar evento: ${e.message}")
        }
    }
}


