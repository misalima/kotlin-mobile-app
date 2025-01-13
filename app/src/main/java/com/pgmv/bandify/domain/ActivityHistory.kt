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
    val activity: String, // Example: "A new song was added", "A new event was created", "A new file was uploaded"
    @ColumnInfo("activity_type") val activityType: String, //"song", "event", "file"
    @ColumnInfo("created_at") val createdAt: String = getCurrentTime(),
    @ColumnInfo("song_id") val songId: Long? = null,
    @ColumnInfo("event_id") val eventId: Long? = null,
    @ColumnInfo("file_id") val fileId: Long? = null,
    @ColumnInfo("item_name") val itemName: String //song name, event title, file name
)
