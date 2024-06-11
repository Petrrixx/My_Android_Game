package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
@Dao
interface AppUserDao {

    @Query("SELECT * FROM app_users WHERE username = :username AND password = :password LIMIT 1")
    suspend fun getUser(username: String, password: String): AppUser?

    @Query("SELECT * FROM app_users WHERE username = :username AND nickname = :nickname LIMIT 1")
    suspend fun getUserByNickname(username: String, nickname: String): AppUser?

    @Query("SELECT * FROM app_users WHERE id = :userId")
    fun loadUserById(userId: Int): AppUser?

    @Insert
    suspend fun insertUser(user: AppUser)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: AppUser)

    @Query("UPDATE app_users SET nickname = :nickname WHERE username = :username")
    suspend fun updateNickname(username: String, nickname: String)
}





