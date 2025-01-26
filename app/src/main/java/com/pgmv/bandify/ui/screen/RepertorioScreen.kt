package com.pgmv.bandify.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.pgmv.bandify.database.DatabaseHelper
import com.pgmv.bandify.domain.Song
import com.pgmv.bandify.ui.components.DeleteConfirmationDialog
import com.pgmv.bandify.ui.components.FilterRow
import com.pgmv.bandify.ui.components.SongCard
import com.pgmv.bandify.ui.theme.BandifyTheme
import com.pgmv.bandify.viewmodel.RepertorioViewModel
import com.pgmv.bandify.viewmodel.RepertorioViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun RepertorioScreen(
    dbHelper: DatabaseHelper? = null,
    navController: NavController,
    eventId: String?
) {
    val viewModel: RepertorioViewModel = viewModel(
        factory = RepertorioViewModelFactory(dbHelper)
    )

    var selectedFilter by remember { mutableStateOf(if (eventId != null) "Evento" else "Todas") }
    var selectedEvent by remember { mutableStateOf(eventId?.toLongOrNull()) }
    var showDialog by remember { mutableStateOf(false) }
    var songToDelete by remember { mutableStateOf<Song?>(null) }

    val songsFlow = remember(selectedFilter, selectedEvent) {
        viewModel.getSongsFlow(selectedFilter, selectedEvent)
    }

    val songs by songsFlow.collectAsState(initial = emptyList())

    val eventList by viewModel.events.collectAsState()

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
                eventList = eventList,
                selectedEvent = selectedEvent?.let {
                    id -> eventList.find {
                        it.id == id }
                },
                onEventSelected = { event ->
                    selectedEvent = event.id
                }
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                if (songs.isEmpty()) {
                    item {
                        Text(
                            text = "Nenhuma música encontrada.",
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray,
                            modifier = Modifier
                                .fillParentMaxSize()
                                .wrapContentSize()
                                .offset(y = (-80).dp)
                        )
                    }
                } else {
                    items(songs) { song ->
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
                        viewModel.deleteSong(song)
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
            ),
            eventId = null
        )
    }
}