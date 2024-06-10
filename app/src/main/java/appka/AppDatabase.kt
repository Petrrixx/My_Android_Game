package com.example.towerdefense_seminar_bolecek_peter_5zyi24.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.data.dao.AppUserDao
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.data.entities.AppUser

@Database(entities = [AppUser::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appUserDao(): AppUserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
