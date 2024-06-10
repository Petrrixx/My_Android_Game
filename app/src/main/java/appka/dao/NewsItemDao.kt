package com.example.towerdefense_seminar_bolecek_peter_5zyi24.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.data.entities.NewsItem

@Dao
interface NewsItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewsItem(newsItem: NewsItem)

    @Query("SELECT * FROM news_items")
    suspend fun getAllNewsItems(): List<NewsItem>
}
