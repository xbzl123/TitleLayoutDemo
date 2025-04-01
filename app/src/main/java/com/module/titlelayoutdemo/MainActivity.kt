package com.module.titlelayoutdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.blankj.utilcode.util.ToastUtils
import com.module.titlelayoutdemo.room.AppDBHandler
import com.module.titlelayoutdemo.room.User
import com.module.titlelayoutdemo.room.UserDao
import com.module.titlelayoutdemo.ui.main.MainFragment
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    var size = 0
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        val userDao = AppDBHandler.getRoomDBObject()?.userDao()
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                size = userDao?.getAll()?.size?:0
                val new_id = size.plus(1)
                if (userDao != null) {
                    userDao.insertAll(User(new_id,"long","yanghe","742781499@qq.com", "123456:$new_id"))
                }
            }
            if (userDao != null) {
                showData(userDao)
            }
        }
    }

    private suspend fun showData(userDao: UserDao) {
        var showContent =""
        withContext(Dispatchers.IO){
            showContent = userDao.getAll()[size].password
        }
        ToastUtils.showLong(showContent)
    }
    override fun onResume() {
        super.onResume()

    }
}