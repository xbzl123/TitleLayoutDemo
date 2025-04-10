package com.module.titlelayoutdemo.room

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object AppDBHandler {
    private var db: AppDatabase? = null

    fun getRoomDBObject(): AppDatabase? {
        return db
    }

    private val MIGRATION_2_3: Migration = object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                "CREATE TABLE searchinfos (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        "date TEXT, " +
                        "search_question TEXT, " +
                        "search_result TEXT NOT NULL, " +
                        "is_favourite INTEGER NOT NULL)"
            )
        }
    }
    private val MIGRATION_3_4: Migration = object : Migration(3, 4) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE searchinfos "
                    + " ADD COLUMN is_selected INTEGER NOT NULL DEFAULT 0");
        }
    }


    fun buildDBFile(context: Context){
        val dbPath = context.getDatabasePath("my_database.db").path
        Log.d("Database", "Path: $dbPath")  // 输出路径确认
        try {
            db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "my_database.db"
            ).addMigrations(MIGRATION_2_3)
                .addMigrations(MIGRATION_3_4)
                .build()
        } catch (e: Exception) {
            Log.e("Database", "初始化失败: ${e.message}")
            e.printStackTrace()
        } finally {
            Log.e("Database", "初始化成功")
        }
    }
}