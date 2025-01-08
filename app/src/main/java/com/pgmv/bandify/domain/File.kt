package com.pgmv.bandify.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.pgmv.bandify.utils.getCurrentTime


@Entity(tableName = "files", foreignKeys = [
    ForeignKey(
        entity = Song::class,
        parentColumns = ["id"],
        childColumns = ["song_id"],
        onDelete = ForeignKey.CASCADE
    ),
    ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.CASCADE
    )
])
data class File(
    @PrimaryKey(true) val id: Long = 0L,
    val name: String,
    val url: String,
    val size: Long,
    val type: String,
    val description: String = "",
    @ColumnInfo("song_id") val songId: Long?,
    @ColumnInfo("user_id") val userId: Long,
    @ColumnInfo("created_at")val createdAt: String = getCurrentTime(),
    @ColumnInfo("updated_at")val updatedAt: String? = null
)
