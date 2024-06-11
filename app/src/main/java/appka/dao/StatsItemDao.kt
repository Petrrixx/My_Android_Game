package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StatsItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStatsItem(statsItem: StatsItem)

    @Query("SELECT * FROM stats_items")
    suspend fun getAllStatsItems(): List<StatsItem>
}
