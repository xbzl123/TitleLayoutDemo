package com.module.titlelayoutdemo

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.module.titlelayoutdemo.room.AppDBHandler
import com.module.titlelayoutdemo.room.AppDatabase

open class BaseApplication :Application(){

    override fun onCreate() {
        super.onCreate()
        AppDBHandler.buildDBFile(this)
    }
}