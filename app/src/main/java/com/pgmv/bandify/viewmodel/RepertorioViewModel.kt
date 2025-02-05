package com.pgmv.bandify.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.pgmv.bandify.database.DatabaseHelper
import com.pgmv.bandify.domain.Event
import com.pgmv.bandify.domain.Song
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class RepertorioViewModel(dbHelper: DatabaseHelper? = null) : ViewModel() {
    private val songDao = dbHelper?.songDao()
    private val eventDao = dbHelper?.eventDao()

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events.asStateFlow()

    init {
        viewModelScope.launch {
            getSongsFlow(selectedFilter = "Todas", selectedEvent = null)
            getEventsFlow().collect { events ->
                _events.value = events
            }
        }
    }

    fun getSongsFlow(selectedFilter: String, selectedEvent: Long?): Flow<List<Song>> {
        return when (selectedFilter) {
            "Todas" -> songDao?.getAllSongs() ?: flowOf(emptyList())
            "Evento" -> selectedEvent?.let { eventId ->
                songDao?.getSongsForEvent(eventId)
            } ?: flowOf(emptyList())
            else -> songDao?.getSongsByTag(selectedFilter) ?: flowOf(emptyList())
        }
    }

    private fun getEventsFlow(): Flow<List<Event>> {
        return eventDao?.getAllEvents() ?: flowOf(emptyList())
    }

    fun deleteSong(song: Song) {
        viewModelScope.launch {
            songDao?.deleteSong(song)
        }
    }
}

class RepertorioViewModelFactory(private val dbHelper: DatabaseHelper?) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RepertorioViewModel::class.java)) {
            return RepertorioViewModel(dbHelper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}