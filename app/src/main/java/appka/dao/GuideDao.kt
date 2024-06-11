package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GuideDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGuide(guide: Guide)

    @Query("SELECT * FROM guides")
    suspend fun getAllGuidesItems(): List<Guide>
}
