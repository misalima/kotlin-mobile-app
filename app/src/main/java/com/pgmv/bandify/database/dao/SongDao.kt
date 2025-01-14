package com.pgmv.bandify.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.pgmv.bandify.domain.Song
import kotlinx.coroutines.flow.Flow

@Dao
interface SongDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSong(song: Song): Long

    @Update
    suspend fun updateSong(song: Song)

    @Delete
    suspend fun deleteSong(song: Song)

    @Query("SELECT * FROM songs")
    fun getAllSongs(): Flow<List<Song>>

    @Query("SELECT * FROM songs WHERE id = :id LIMIT 1")
    suspend fun getSongById(id: Long): Song?

    @Query("SELECT * FROM songs WHERE user_id = :userId")
    fun getSongsByUserId(userId: Long): Flow<List<Song>>

    @Query("SELECT * FROM songs WHERE title LIKE :title")
    fun getSongsByTitle(title: String): Flow<List<Song>>

    @Query("SELECT * FROM songs WHERE artist LIKE :artist")
    fun getSongsByArtist(artist: String): Flow<List<Song>>

    @Query("SELECT * FROM songs WHERE tag LIKE :tag")
    fun getSongsByTag(tag: String): Flow<List<Song>>

    @Query("""
    SELECT s.* FROM songs s
    INNER JOIN event_songs es ON es.song_id = s.id
    WHERE es.event_id = :eventId
""")
    fun getSongsForEvent(eventId: Long): Flow<List<Song>>

}