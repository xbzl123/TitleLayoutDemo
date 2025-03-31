package com.module.titlelayoutdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.blankj.utilcode.util.ToastUtils
import com.module.titlelayoutdemo.room.AppDBHandler
import com.module.titlelayoutdemo.room.User
import com.module.titlelayoutdemo.room.UserDao
import com.module.titlelayoutdemo.ui.main.MainFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        var userDao = AppDBHandler.getRoomDBObject()?.userDao()
        GlobalScope.launch {
            if (userDao != null) {
                showData(userDao)
            }
        }
    }

    suspend fun showData(userDao: UserDao) {
        var showContent =""
        withContext(Dispatchers.IO){
            userDao?.insertAll(User(1,"long","yanghe","742781499@qq.com","123456"))
            showContent = userDao!!.getAll().get(0).email
        }
        val s = withContext(Dispatchers.Main){
            ToastUtils.showLong(showContent)
        }
    }
    override fun onResume() {
        super.onResume()

    }
}