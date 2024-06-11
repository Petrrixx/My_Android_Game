package com.example.towerdefense_seminar_bolecek_peter_5zyi24.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.data.entities.HubsItem

@Dao
interface HubsItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(hubsItem: HubsItem)

    @Query("SELECT * FROM hubsitem WHERE title = :title AND description = :description")
    suspend fun getHubsItem(title: String, description: String): HubsItem?
}
