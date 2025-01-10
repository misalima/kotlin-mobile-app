package com.pgmv.bandify.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.pgmv.bandify.utils.getCurrentTime

@Entity(tableName = "events",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )])
data class Event (
    @PrimaryKey(autoGenerate = true)val id: Long = 0,
    val title: String,
    val date: String,
    val time: String,
    val place: String,
    val address: String,
    val userId: Long,
    @ColumnInfo("image_url") val imageUrl: String = "",
    @ColumnInfo("created_at") val createdAt: String = getCurrentTime(),
    @ColumnInfo("updated_at") val updatedAt: String? = null,
)