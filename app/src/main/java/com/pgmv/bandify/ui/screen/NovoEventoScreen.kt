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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.pgmv.bandify.database.DatabaseHelper
import com.pgmv.bandify.domain.Song
import com.pgmv.bandify.ui.components.SongsSelectionForEvent
import com.pgmv.bandify.ui.theme.Blue20
import com.pgmv.bandify.ui.theme.Grey60
import com.pgmv.bandify.utils.millisToLocalDate
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NovoEventoScreen(dbHelper: DatabaseHelper) {
    val focusManager = LocalFocusManager.current
    var showDatePickerDialog by remember {
        mutableStateOf(false)
    }
    var showTimeInput by remember { mutableStateOf(false) }
    val datePickerState =
        rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis())
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedTime: TimePickerState? by remember { mutableStateOf(null) }
    var formattedTime: String by remember { mutableStateOf("Escolha um horário") }

    val formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", Locale("pt", "BR"))

    var selectedSongs = remember { mutableStateOf(setOf<Song>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp, vertical = 16.dp),
    ) {
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
                                        Log.d("agenda", "selectedDate before: $selectedDate")
                                        selectedDate = millis.millisToLocalDate()
                                        Log.d(
                                            "agenda",
                                            "selectedDate after: $selectedDate \n millis: $millis"
                                        )
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

            if(showTimeInput) {
                TimeInputComposable(
                    onConfirm = { time ->
                        selectedTime = time
                        formattedTime = String.format("%02d:%02d", time.hour, time.minute)
                        showTimeInput = false
                    },
                    onDismiss = {
                        showTimeInput = false
                    }
                )
            }

            // TextField para o Horário
            TextField(
                value = formattedTime,
                onValueChange = { },
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
                }
            )

            Spacer(modifier = Modifier.padding(8.dp))

            TextFieldInput(
                value = "",
                onValueChange = { /*TODO*/ },
                label = "Nome do evento"
            )

            Spacer(modifier = Modifier.padding(8.dp))

            TextFieldInput(
                value = "",
                onValueChange = { /*TODO*/ },
                label = "Local do evento"
            )

            Spacer(modifier = Modifier.padding(8.dp))

            TextFieldInput(
                value = "",
                onValueChange = { /*TODO*/ },
                label = "Endereço"
            )

            Spacer(modifier = Modifier.padding(16.dp))

            SongsSelectionForEvent(dbHelper = dbHelper, selectedSongs = selectedSongs)

            Spacer(modifier = Modifier.padding(16.dp))
        }

        Button(
            onClick = { /*TODO*/ },
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
fun TextFieldInput(value: String, onValueChange: (String) -> Unit, label: String) {
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
            focusedIndicatorColor = Color.Transparent
        )
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
        Surface (
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





