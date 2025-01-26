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
    var songTitle = mutableStateOf("")
    var artist = mutableStateOf("")
    var selectedTag = mutableStateOf("")
    var selectedTempo = mutableStateOf("")
    var selectedKey = mutableStateOf("")

    val tagOptions = listOf("Ensaio", "Prontas")
    val keyOptions = listOf(
        "C", "C#", "D", "D#", "E", "F",
        "F#", "G", "G#", "A", "A#", "B",
        "Cm", "C#m", "Dm", "D#m", "Em", "Fm",
        "F#m", "Gm", "G#m", "Am", "A#m", "Bm",
        "Db", "Ebm", "Gb", "Ab", "Bb",
        "Dbm", "Ebm", "Gbm", "Abm", "Bbm"
    )


    fun updateSongTitle(value: String) {
        songTitle.value = value
    }

    fun updateArtist(value: String) {
        artist.value = value
    }

    fun updateSelectedTag(value: String) {
        selectedTag.value = value
    }

    fun updateSelectedTempo(value: String) {
        selectedTempo.value = value
    }

    fun updateSelectedKey(value: String) {
        selectedKey.value = value
    }


    fun saveSong() {
        val newSong = Song(
            title = songTitle.value,
            artist = artist.value,
            tag = selectedTag.value,
            tempo = selectedTempo.value.toInt(),
            key = selectedKey.value,
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