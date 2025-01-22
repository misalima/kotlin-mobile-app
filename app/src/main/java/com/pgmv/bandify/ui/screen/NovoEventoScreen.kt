package com.pgmv.bandify.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pgmv.bandify.database.DatabaseHelper
import com.pgmv.bandify.ui.components.SongsSelectionForEvent
import com.pgmv.bandify.ui.components.TimeInputComposable
import com.pgmv.bandify.ui.theme.Blue20
import com.pgmv.bandify.ui.theme.Grey60
import com.pgmv.bandify.utils.millisToLocalDate
import com.pgmv.bandify.viewmodel.EventViewModel
import com.pgmv.bandify.viewmodel.EventViewModelFactory
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NovoEventoScreen(dbHelper: DatabaseHelper) {

    val eventViewModel: EventViewModel = viewModel(
        factory = EventViewModelFactory(dbHelper)
    )
    val eventDate = eventViewModel.selectedDate.value
    val eventTime = eventViewModel.selectedTime.value
    val eventSongs = eventViewModel.selectedSongs
    val eventTitle = eventViewModel.title.value
    val eventPlace = eventViewModel.place.value
    val eventAddress = eventViewModel.address.value
    val eventTitleError = eventViewModel.titleError.value
    val eventPlaceError = eventViewModel.placeError.value
    val eventAddressError = eventViewModel.addressError.value
    val eventTimeError = eventViewModel.timeError.value


    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    fun handleSubmitting() {
        val isOK = eventViewModel.validateAllFields()

        if (!isOK) {
            coroutineScope.launch {
                snackBarHostState.showSnackbar(
                    message = "Preencha todos os campos corretamente",
                    actionLabel = "Ok",
                    duration = SnackbarDuration.Short
                )
            }
        } else {
            eventViewModel.saveEvent()
            coroutineScope.launch {
                snackBarHostState.showSnackbar(
                    message = "Evento criado com sucesso",
                    actionLabel = "Ok",
                    duration = SnackbarDuration.Short
                )
            }

        }
    }

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
                                        eventViewModel.updateSelectedDate(millis.millisToLocalDate())
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
                value = eventDate.format(formatter),
                onValueChange = {},
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
                        eventViewModel.updateSelectedTime(
                            String.format(
                                "%02d:%02d",
                                time.hour,
                                time.minute
                            )
                        )
                        showTimeInput = false
                    },
                    onDismiss = {
                        showTimeInput = false
                    }
                )
            }

            // TextField para o Horário
            TextField(
                value = eventTime,
                onValueChange = {},
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
                isError = eventTimeError != null
            )
            eventTimeError?.let {
                Text(
                    text = eventTimeError,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.padding(8.dp))

            TextFieldInput(
                value = eventTitle,
                onValueChange = {
                    eventViewModel.updateTitle(it)
                },
                label = "Nome do evento",
                isError = eventTitleError != null
            )
            eventTitleError?.let {
                Text(
                    text = eventTitleError,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.padding(8.dp))

            TextFieldInput(
                value = eventPlace,
                onValueChange = {
                    eventViewModel.updatePlace(it)
                },
                label = "Local do evento",
                isError = eventPlaceError != null
            )
            eventPlaceError?.let {
                Text(
                    text = eventPlaceError,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.padding(8.dp))

            TextFieldInput(
                value = eventAddress,
                onValueChange = {
                    eventViewModel.updateAddress(it)
                },
                label = "Endereço",
                isError = eventAddressError != null
            )
            eventAddressError?.let {
                Text(
                    text = eventAddressError,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.padding(16.dp))

            SongsSelectionForEvent(dbHelper = dbHelper, selectedSongs = eventSongs)

            Spacer(modifier = Modifier.padding(16.dp))
        }

        SnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Button(
            onClick = { handleSubmitting() },
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





