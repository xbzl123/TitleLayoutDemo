package com.module.titlelayoutdemo.room

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [User::class,SearchInfo::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun searchDao():SearchInfoDao

}