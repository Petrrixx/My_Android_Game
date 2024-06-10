package com.example.towerdefense_seminar_bolecek_peter_5zyi24.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.data.entities.StatsItem

@Dao
interface StatsItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStatsItem(statsItem: StatsItem)

    @Query("SELECT * FROM stats_items")
    suspend fun getAllStatsItems(): List<StatsItem>
}
