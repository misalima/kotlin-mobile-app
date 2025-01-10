package com.pgmv.bandify.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.pgmv.bandify.domain.Event
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: Event): Long

    @Update
    suspend fun updateEvent(event: Event)

    @Delete
    suspend fun deleteEvent(event: Event)

    @Query("DELETE FROM events")
    suspend fun deleteAllEvents()

    @Query("SELECT * FROM events")
    fun getAllEvents(): Flow<List<Event>>

    @Query("SELECT * FROM events WHERE id = :id LIMIT 1")
    suspend fun getEventById(id: Long): Event?

    @Query("SELECT * FROM events WHERE userId = :userId")
    fun getEventsByUserId(userId: Long): Flow<List<Event>>

    @Query("SELECT * FROM events WHERE title LIKE :title")
    suspend fun getEventsByTitle(title: String): List<Event>

    @Query("SELECT * FROM events WHERE date = :date")
    suspend fun getEventsByDate(date: String): List<Event>

    @Query("""
    SELECT e.* FROM events e 
    INNER JOIN event_files ef ON ef.event_id = e.id 
    WHERE ef.file_id = :fileId
""")
    suspend fun getEventsForFile(fileId: Long): List<Event>

    @Query("""
    SELECT e.* FROM events e
    INNER JOIN event_songs es ON es.event_id = e.id
    WHERE es.song_id = :songId
""")
    suspend fun getEventsForSong(songId: Long): List<Event>
}