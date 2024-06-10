package com.example.towerdefense_seminar_bolecek_peter_5zyi24.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class AppUser(
    @PrimaryKey val username: String,
    val password: String,
    val isAdmin: Boolean = false
)
