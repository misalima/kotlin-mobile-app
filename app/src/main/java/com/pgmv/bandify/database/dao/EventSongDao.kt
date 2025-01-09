package com.pgmv.bandify.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pgmv.bandify.domain.EventSong

@Dao
interface EventSongDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEventSong(eventSong: EventSong): Long

    @Query("DELETE FROM event_songs WHERE event_id = :eventId AND song_id = :songId")
    suspend fun deleteEventSong(eventId: Long, songId: Long)

    @Query("SELECT * FROM event_songs WHERE event_id = :eventId")
    suspend fun getSongsForEvent(eventId: Long): List<EventSong>

    @Query("SELECT * FROM event_songs WHERE song_id = :songId")
    suspend fun getEventsForSong(songId: Long): List<EventSong>
}