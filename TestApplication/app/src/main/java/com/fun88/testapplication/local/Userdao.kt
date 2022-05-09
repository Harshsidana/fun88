package com.fun88.testapplication.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface Userdao {
    @Query("SELECT * FROM user")
    suspend fun getAll(): List<User>

    @Insert
    suspend fun insertAll(user: User)

}