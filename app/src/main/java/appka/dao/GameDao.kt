package com.example.towerdefense_seminar_bolecek_peter_5zyi24.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.data.entities.Game

@Dao
interface GameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: Game)

    @Query("SELECT * FROM games")
    suspend fun getAllGames(): List<Game>
}
