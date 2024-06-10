package com.example.towerdefense_seminar_bolecek_peter_5zyi24.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.data.entities.AppUser

@Dao
interface AppUserDao {
    @Insert
    suspend fun insert(user: AppUser)

    @Query("SELECT * FROM User WHERE username = :username AND password = :password LIMIT 1")
    suspend fun getUser(username: String, password: String): AppUser?
}
