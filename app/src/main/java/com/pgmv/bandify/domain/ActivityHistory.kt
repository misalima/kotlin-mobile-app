package com.pgmv.bandify.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.pgmv.bandify.utils.getCurrentTime

@Entity(tableName = "activity_history", foreignKeys = [
    ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.CASCADE
    ),
    ForeignKey(
        entity = Song::class,
        parentColumns = ["id"],
        childColumns = ["song_id"],
        onDelete = ForeignKey.CASCADE
    ),
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
data class ActivityHistory(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo("user_id") val userId: Long,
    val activity: String,
    @ColumnInfo("activity_type") val activityType: String,
    @ColumnInfo("created_at") val createdAt: String = getCurrentTime(),
    @ColumnInfo("song_id") val songId: Long?,
    @ColumnInfo("event_id") val eventId: Long?,
    @ColumnInfo("file_id") val fileId: Long?
)
