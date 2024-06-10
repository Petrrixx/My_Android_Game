package com.example.towerdefense_seminar_bolecek_peter_5zyi24.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "guides")
data class Guide(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val link: String
)
