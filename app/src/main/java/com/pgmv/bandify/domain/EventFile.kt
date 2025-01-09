package com.pgmv.bandify.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "event_files", primaryKeys = ["event_id", "file_id"], foreignKeys = [
    ForeignKey(
        entity = Event::class,
        parentColumns = ["id"],
        childColumns = ["event_id"],
        onDelete = ForeignKey.CASCADE
    ),
    ForeignKey(
        entity = File::class,
        parentColumns = ["id"],
        childColumns = ["file_id"],
        onDelete = ForeignKey.CASCADE
    )
])
data class EventFile(
    @ColumnInfo("event_id") val eventId: Long,
    @ColumnInfo("file_id") val fileId: Long
)
