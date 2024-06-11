package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(newsItem: NewsItem)

    @Query("SELECT * FROM news_items")
    suspend fun getAllNewsItems(): List<NewsItem>
}


