package com.pgmv.bandify.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.pgmv.bandify.database.DatabaseHelper
import com.pgmv.bandify.domain.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AgendaViewModel(private val dbHelper: DatabaseHelper) : ViewModel() {
    private val _events = MutableStateFlow<List<Event>>(emptyList<Event>())
    val events: StateFlow<List<Event>> = _events.asStateFlow()

    init {
        loadUserEvents(userId = 1L)
    }

    private fun loadUserEvents(userId: Long) {
        viewModelScope.launch {
            dbHelper.eventDao().getEventsByUserId(userId).collect { events ->
                _events.value = events
            }
        }
    }
}

class AgendaViewModelFactory(private val dbHelper: DatabaseHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AgendaViewModel::class.java)) {
            return AgendaViewModel(dbHelper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}