package com.pgmv.bandify.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pgmv.bandify.database.dao.ActivityHistoryDao
import com.pgmv.bandify.database.dao.EventDao
import com.pgmv.bandify.database.dao.EventFileDao
import com.pgmv.bandify.database.dao.EventSongDao
import com.pgmv.bandify.database.dao.FileDao
import com.pgmv.bandify.database.dao.SongDao
import com.pgmv.bandify.database.dao.UserDao
import com.pgmv.bandify.domain.ActivityHistory
import com.pgmv.bandify.domain.Event
import com.pgmv.bandify.domain.EventFile
import com.pgmv.bandify.domain.EventSong
import com.pgmv.bandify.domain.File
import com.pgmv.bandify.domain.Song
import com.pgmv.bandify.domain.User

@TypeConverters(Converters::class)
@Database(
    version = 1,
    entities = [Event::class, Song::class, User::class, EventSong::class, ActivityHistory::class, File::class, EventFile::class],
    exportSchema = false
)
abstract class DatabaseHelper : RoomDatabase() {

    abstract fun eventDao(): EventDao
    abstract fun songDao(): SongDao
    abstract fun userDao(): UserDao
    abstract fun eventSongDao(): EventSongDao
    abstract fun activityHistoryDao(): ActivityHistoryDao
    abstract fun fileDao(): FileDao
    abstract fun eventFileDao(): EventFileDao

    // Static do Kotlin
    companion object {
        @Volatile
        private var INSTANCE: DatabaseHelper? = null

        fun getInstance(context: Context): DatabaseHelper {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseHelper::class.java,
                    "bandify.db"
                ).build()
                }
            }
        }
    }





