package com.example.towerdefense_seminar_bolecek_peter_5zyi24

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.data.dao.AppUserDao
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.data.daos.NewsItemDao
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.data.entities.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [AppUser::class, NewsItem::class, Game::class, StatsItem::class, Guide::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appUserDao(): AppUserDao
    abstract fun newsItemDao(): NewsItemDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration()
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Vloženie admina
                            db.execSQL("INSERT INTO appuser (username, password, isAdmin) VALUES ('admin', 'admin', 1)")
                        }
                    }).build()
                INSTANCE = instance
                instance
            }
        }
    }
}



