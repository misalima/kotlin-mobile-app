package com.pgmv.bandify.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pgmv.bandify.domain.EventFile

@Dao
interface EventFileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEventFile(eventFile: EventFile)

    @Query("DELETE FROM event_files WHERE event_id = :eventId AND file_id = :fileId")
    suspend fun deleteEventFile(eventId: Long, fileId: Long)

    @Query("SELECT * FROM event_files WHERE event_id = :eventId")
    suspend fun getFilesForEvent(eventId: Long): List<EventFile>

    @Query("SELECT * FROM event_files WHERE file_id = :fileId")
    suspend fun getEventsForFile(fileId: Long): List<EventFile>
}