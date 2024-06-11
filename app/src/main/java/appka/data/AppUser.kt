package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
import java.util.Date

@Entity(tableName = "app_users")
data class AppUser(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String,
    val password: String,
    val nickname: String,
    val isAdmin: Boolean,
    @ColumnInfo(name = "registration_date") val registrationDate: Date? = null
)





