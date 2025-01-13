package com.pgmv.bandify.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.pgmv.bandify.domain.ActivityHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivity(activityHistory: ActivityHistory): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivities(activityHistories: List<ActivityHistory>)

    @Update
    suspend fun updateActivity(activityHistory: ActivityHistory)

    @Delete
    suspend fun deleteActivity(activityHistory: ActivityHistory)

    @Query("DELETE FROM activity_history")
    suspend fun deleteAllActivities()

    @Query("SELECT * FROM activity_history")
    suspend fun getAllActivities(): List<ActivityHistory>

    @Query("SELECT * FROM activity_history WHERE id = :id LIMIT 1")
    suspend fun getActivityById(id: Long): ActivityHistory?

    @Query("SELECT * FROM activity_history WHERE user_id = :userId ORDER BY created_at DESC LIMIT 5")
    fun getRecentActivitiesByUserId(userId: Long): Flow<List<ActivityHistory>>

    @Query("SELECT * FROM activity_history WHERE user_id = :userId")
    suspend fun getActivitiesByUserId(userId: Long): List<ActivityHistory>

    @Query("SELECT * FROM activity_history WHERE activity_type = :activityType")
    suspend fun getActivitiesByType(activityType: String): List<ActivityHistory>

    @Query("SELECT * FROM activity_history WHERE created_at BETWEEN :startDate AND :endDate ORDER BY created_at DESC")
    suspend fun getActivitiesBetweenDates(startDate: String, endDate: String): List<ActivityHistory>

}