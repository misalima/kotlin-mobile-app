package com.pgmv.bandify.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.pgmv.bandify.utils.getCurrentTime


@Entity(tableName = "songs", foreignKeys = [
    ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.CASCADE
    )
])
data class Song(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val title: String,
    val artist: String,
    val duration: Int,
    val key: String,
    val tempo: Int,
    @ColumnInfo("user_id") val userId: Long,
    @ColumnInfo("created_at") val createdAt: String = getCurrentTime(),
    @ColumnInfo("updated_at") val updatedAt: String? = null
)
