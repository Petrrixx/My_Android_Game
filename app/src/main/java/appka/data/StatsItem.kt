package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stats_items")
data class StatsItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val gameName: String,
    val wins: Int,
    val losses: Int
)
