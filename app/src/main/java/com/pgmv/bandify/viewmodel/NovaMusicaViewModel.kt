package com.pgmv.bandify.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.pgmv.bandify.database.DatabaseHelper
import com.pgmv.bandify.domain.Song
import kotlinx.coroutines.launch

class NovaMusicaViewModel(dbHelper: DatabaseHelper? = null): ViewModel() {
    private val songDao = dbHelper?.songDao()

    var songAdded = mutableStateOf(false)
    var musicTitle = mutableStateOf("")
    var bandaName = mutableStateOf("")
    var selectedTag = mutableStateOf("")
    var selectedTempo = mutableStateOf("")
    var selectedTom = mutableStateOf("")

    val tagOptions = listOf("Ensaio", "Prontas")
    val tomOptions = listOf(
        "C", "C#", "D", "D#", "E", "F",
        "F#", "G", "G#", "A", "A#", "B",
        "Db", "Eb", "Gb", "Ab", "Bb"
    )

    fun updateMusicTitle(value: String) {
        musicTitle.value = value
    }

    fun updateBandaName(value: String) {
        bandaName.value = value
    }

    fun updateSelectedTag(value: String) {
        selectedTag.value = value
    }

    fun updateSelectedTempo(value: String) {
        selectedTempo.value = value
    }

    fun updateSelectedTom(value: String) {
        selectedTom.value = value
    }


    fun saveSong() {
        val newSong = Song(
            title = musicTitle.value,
            artist = bandaName.value,
            tag = selectedTag.value,
            tempo = selectedTempo.value.toInt(),
            key = selectedTom.value,
            userId = 1L,
            duration = "4:30"
        )
        viewModelScope.launch {
            try {
                songDao?.insertSong(newSong)
                songAdded.value = true
            }catch (e: Exception) {
                songAdded.value = false
            }
        }
    }
}

class NovaMusicaViewModelFactory(private val dbHelper: DatabaseHelper?): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NovaMusicaViewModel::class.java)) {
            return NovaMusicaViewModel(dbHelper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}