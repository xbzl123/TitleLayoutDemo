package com.module.titlelayoutdemo.room

import android.content.Context
import android.util.Log
import androidx.room.Room

object AppDBHandler {
    private var db: AppDatabase? = null

    fun getRoomDBObject(): AppDatabase? {
        return db
    }

    fun buildDBFile(context: Context){
        val dbPath = context.getDatabasePath("my_database.db").path
        Log.d("Database", "Path: $dbPath")  // 输出路径确认
        try {
            db = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "my_database.db"
            )
                .build()
        } catch (e: Exception) {
            Log.e("Database", "初始化失败: ${e.message}")
            e.printStackTrace()
        } finally {
            Log.e("Database", "初始化成功")
        }
    }
}