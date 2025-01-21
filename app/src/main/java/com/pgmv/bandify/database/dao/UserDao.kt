package com.pgmv.bandify.database.dao


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.pgmv.bandify.domain.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<User>>

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    fun getUserById(id: Int): Flow<User?>

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    fun getUserByUsername(username: String): Flow<User?>


}


