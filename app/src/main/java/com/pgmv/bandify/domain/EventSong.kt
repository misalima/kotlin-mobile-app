package com.pgmv.bandify.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "event_songs", primaryKeys = ["event_id", "song_id"], foreignKeys = [
    ForeignKey(
        entity = Event::class,
        parentColumns = ["id"],
        childColumns = ["event_id"],
        onDelete = ForeignKey.CASCADE
    ),
    ForeignKey(
        entity = Song::class,
        parentColumns = ["id"],
        childColumns = ["song_id"],
        onDelete = ForeignKey.CASCADE
    )
])
data class EventSong(
    @ColumnInfo("event_id") val eventId: Long,
    @ColumnInfo("song_id") val songId: Long
)
