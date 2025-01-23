package com.pgmv.bandify.ui.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pgmv.bandify.database.DatabaseHelper
import com.pgmv.bandify.domain.Song
import com.pgmv.bandify.ui.components.DropdownTextField
import com.pgmv.bandify.ui.components.ValidatedTextField
import com.pgmv.bandify.ui.theme.BandifyTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun NovaMusicaScreen(
    dbHelper: DatabaseHelper? = null,
    navController: NavController? = null
) {
    val context = LocalContext.current
    val songDao = dbHelper?.songDao()
    var songAdded by remember { mutableStateOf(false) }
    var musicTitle by remember { mutableStateOf("") }
    var bandaName by remember { mutableStateOf("") }
    var selectedTag by remember { mutableStateOf("") }
    var selectedTempo by remember { mutableStateOf("") }
    var selectedTom by remember { mutableStateOf("") }
    val tagOptions = listOf("Prontas", "Ensaio")
    val tomOptions = listOf(
        "C", "C#", "D", "D#", "E", "F",
        "F#", "G", "G#", "A", "A#", "B",
        "Db", "Eb", "Gb", "Ab", "Bb"
    )
    val validFields = musicTitle.isNotBlank() && bandaName.isNotBlank() && selectedTempo.isNotBlank() &&
            selectedTom.isNotBlank() && selectedTag.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        ValidatedTextField(
            value = musicTitle,
            onValueChange = { musicTitle = it },
            label = "Nome da Música",
            modifier = Modifier.padding(top = 12.dp),
            leadingIcon = {
                Icon(Icons.Filled.MusicNote,
                    contentDescription = "Música")
            }
        )

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ){
            ValidatedTextField(
                value = bandaName,
                onValueChange = { bandaName = it },
                label = "Banda/Artista",
                modifier = Modifier.weight(1f)
            )

            DropdownTextField(
                label = "Tag",
                options = tagOptions,
                selectedOption = selectedTag,
                onOptionSelected = { selectedTag = it },
                modifier = Modifier.weight(1f)
            )
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ){
            ValidatedTextField(
                value = selectedTempo,
                onValueChange = { selectedTempo = it },
                label = "Tempo (bpm)",
                modifier = Modifier.weight(1f)
            )

            DropdownTextField(
                label = "Tom",
                options = tomOptions,
                selectedOption = selectedTom,
                onOptionSelected = { selectedTom = it },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.padding(24.dp))

        Button(
            onClick = {
                if (validFields) {
                    val newSong = Song(
                        title = musicTitle,
                        artist = bandaName,
                        duration = "3:20",
                        tempo = selectedTempo.toInt(),
                        key = selectedTom,
                        tag = selectedTag,
                        userId = 1
                    )
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            Log.d("AddSong", "Inserting song: $newSong")
                            songDao?.insertSong(newSong)
                            Log.d("AddSong", "Song added successfully")
                            songAdded = true
                        } catch (e: Exception) {
                            e.printStackTrace()
                            Log.e("AddSong", "Error adding song: ${e.message}")
                        }
                    }
                    navController?.popBackStack()
                } else {
                    Log.d("AddSong", "Fields not filled correctly.")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            enabled = validFields
        ) {
            Text(text = "Adicionar Música", style = MaterialTheme.typography.titleMedium)
        }
    }

    LaunchedEffect(songAdded) {
        if (songAdded) {
            Toast.makeText(context, "Música adicionada com sucesso!", Toast.LENGTH_SHORT).show()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NovaMusicaScreenPreview() {
    BandifyTheme {
        NovaMusicaScreen()
    }
}