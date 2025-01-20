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
import androidx.compose.runtime.collectAsState
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
import androidx.navigation.NavController
import com.pgmv.bandify.database.DatabaseHelper
import com.pgmv.bandify.domain.Song
import com.pgmv.bandify.ui.components.DeleteConfirmationDialog
import com.pgmv.bandify.ui.components.FilterRow
import com.pgmv.bandify.ui.components.SongCard
import com.pgmv.bandify.ui.theme.BandifyTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun RepertorioScreen(
    dbHelper: DatabaseHelper? = null,
    navController: NavController,
    eventId: String?
) {
    val songDao = dbHelper?.songDao()
    val eventDao = dbHelper?.eventDao()

    var selectedFilter by remember { mutableStateOf(if (eventId != null) "Evento" else "Todas") }
    var selectedEvent by remember { mutableStateOf<Long?>(eventId?.toLongOrNull()) }
    var showDialog by remember { mutableStateOf(false) }
    var songToDelete by remember { mutableStateOf<Song?>(null) }

    val songsFlow = remember(selectedFilter, selectedEvent) {
        when (selectedFilter) {
            "Todas" -> songDao?.getAllSongs() ?: kotlinx.coroutines.flow.flowOf(emptyList())
            "Evento" -> selectedEvent?.let { eventId ->
                songDao?.getSongsForEvent(eventId)
            } ?: kotlinx.coroutines.flow.flowOf(emptyList())
            else -> songDao?.getSongsByTag(selectedFilter) ?: kotlinx.coroutines.flow.flowOf(emptyList()) }
    }
    val songs by songsFlow.collectAsState(initial = emptyList())
    val eventList = eventDao?.getAllEvents()?.collectAsState(initial = emptyList()) ?: remember { mutableStateOf(emptyList()) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            Button (
                onClick = { navController.navigate("nova_musica") },
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
                eventList = eventList.value,
                selectedEvent = selectedEvent?.let {
                    id -> eventList.value.find {
                        it.id == id
                    }
               },
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
                            onDelete = { selectedSong ->
                                songToDelete = selectedSong
                                showDialog = true
                            }
                        )
                    }
                }
            }
        }
    }

    if (showDialog && songToDelete != null) {
        DeleteConfirmationDialog(
            item = songToDelete?.title?: "",
            onConfirm = {
                songToDelete?.let { song ->
                    CoroutineScope(Dispatchers.IO).launch {
                        songDao?.deleteSong(song)
                    }
                }
                showDialog = false
            },
            onDismiss = {
                showDialog = false
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
        RepertorioScreen(
            navController = NavController(
                context = androidx.compose.ui.platform.LocalContext.current
            ), eventId = null
        )
    }
}