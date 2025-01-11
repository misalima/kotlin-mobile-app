package com.pgmv.bandify.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pgmv.bandify.utils.getCurrentTime

@Entity(tableName = "users")
data class User (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val username: String,
    @ColumnInfo("first_name") var firstName: String,
    val surname: String,
    val email: String,
    val password: String,
    val phone: String,
    val roles: List<String> = emptyList(),
    @ColumnInfo("created_at") val createdAt: String = getCurrentTime(),
)