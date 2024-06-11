package appka.misc

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.AppUser
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.AppUserDao
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.Converters
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.Game
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.GameDao
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.Guide
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.GuideDao
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.HubsItem
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.HubsItemDao
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.NewsItem
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.NewsItemDao
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.StatsItem
import com.example.towerdefense_seminar_bolecek_peter_5zyi24.StatsItemDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.room.TypeConverters
import java.util.Date

@Database(entities = [AppUser::class, HubsItem::class, Game::class, Guide::class, NewsItem::class, StatsItem::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appUserDao(): AppUserDao
    abstract fun hubsItemDao(): HubsItemDao
    abstract fun gameDao(): GameDao
    abstract fun guideDao(): GuideDao
    abstract fun newsItemDao(): NewsItemDao
    abstract fun statsItemDao(): StatsItemDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                INSTANCE?.appUserDao()?.insert(
                                    AppUser(
                                        username = "admin",
                                        password = "admin",
                                        nickname = "Peter Bolecek",
                                        isAdmin = true,
                                        registrationDate = Date()
                                    )
                                )
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

