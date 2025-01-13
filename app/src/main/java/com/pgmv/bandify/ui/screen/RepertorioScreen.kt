package com.pgmv.bandify.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pgmv.bandify.database.DatabaseHelper
import com.pgmv.bandify.domain.Event
import com.pgmv.bandify.domain.Song
import com.pgmv.bandify.ui.components.DeleteConfirmationDialog
import com.pgmv.bandify.ui.components.FilterRow
import com.pgmv.bandify.ui.components.SongCard
import com.pgmv.bandify.ui.theme.BandifyTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun RepertorioScreen(dbHelper: DatabaseHelper? = null) {
    val songDao = dbHelper?.songDao()
    var songs by remember { mutableStateOf(emptyList<Song>()) }
    var selectedFilter by remember { mutableStateOf("Todas") }
    var selectedEvent by remember { mutableStateOf<Long?>(null) }
    var eventList by remember { mutableStateOf(emptyList<Event>()) }
    val showDialog = remember { mutableStateOf(false) }
    var songToDelete by remember { mutableStateOf<Song?>(null) }

    fun loadSongs() {
        CoroutineScope(Dispatchers.IO).launch {
            val loadedSongs: List<Song> = when (selectedFilter) {
                "Todas" -> songDao?.getAllSongs()?.first() ?: emptyList()
                "Evento" -> selectedEvent?.let { eventId -> songDao?.getSongsForEvent(eventId)?.first() ?: emptyList() } ?: emptyList()
                else -> songDao?.getSongsByTag(selectedFilter)?.first() ?: emptyList()
            }
            withContext(Dispatchers.Main) {
                songs = loadedSongs
            }
        }
    }

    fun loadEvents() {
        CoroutineScope(Dispatchers.IO).launch {
            val events: List<Event>? = dbHelper?.eventDao()?.getAllEvents()?.first()
            withContext(Dispatchers.Main) {
                if (events != null) {
                    eventList = events
                }
            }
        }
    }

    fun deleteSong(song: Song) {
        CoroutineScope(Dispatchers.IO).launch {
            songDao?.deleteSong(song)
            loadSongs()
        }
    }

    LaunchedEffect(selectedFilter, selectedEvent) {
        loadSongs()
        loadEvents()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            Button (
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 4.dp,
                    pressedElevation = 8.dp
                ),
                shape = MaterialTheme.shapes.medium
            ){
                Text(
                    text = "Adicionar Música",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(8.dp)
                )
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Adicionar Música"
                )
            }
        }
    ){ innerPadding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(
                text = "Lista de Músicas",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )

            FilterRow(
                selectedFilter = selectedFilter,
                onFilterSelected = { filter ->
                    selectedFilter = filter
                    if (filter != "Evento") {
                        selectedEvent = null
                    }
                },
                eventList = eventList,
                selectedEvent = selectedEvent?.let { id -> eventList.find { it.id == id } },
                onEventSelected = { event ->
                    selectedEvent = event.id
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                if (songs.isEmpty()) {
                    Text(
                        text = "Nenhuma música encontrada",
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        modifier = Modifier.align(CenterHorizontally)
                    )
                } else {
                    songs.forEach { song ->
                        SongCard(
                            song = song,
                            onDelete = {
                                songToDelete = song
                                showDialog.value = true
                            }
                        )
                    }
                }
            }
        }
    }

    if (showDialog.value && songToDelete != null) {
        DeleteConfirmationDialog(
            item = songToDelete?.title?: "",
            onConfirm = {
                songToDelete?.let { deleteSong(it) }
                showDialog.value = false
            },
            onDismiss = {
                showDialog.value = false
            }
        )
    }
}

@Preview (
    showBackground = true,
    showSystemUi = true)
@Composable
fun RepertorioScreenPreview() {
    BandifyTheme {
        RepertorioScreen()
    }
}