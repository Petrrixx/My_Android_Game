package com.example.towerdefense_seminar_bolecek_peter_5zyi24.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.data.entities.Guide

@Dao
interface GuideDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGuide(guide: Guide)

    @Query("SELECT * FROM guides")
    suspend fun getAllGuides(): List<Guide>
}
