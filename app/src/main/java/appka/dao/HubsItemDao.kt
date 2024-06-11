package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HubsItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(hubsItem: HubsItem)

    @Query("SELECT * FROM hubsitem WHERE title = :title AND description = :description")
    suspend fun getHubsItem(title: String, description: String): HubsItem?

    @Query("SELECT * FROM hubsItem")
    suspend fun getAllHubsItems(): List<HubsItem>
}
