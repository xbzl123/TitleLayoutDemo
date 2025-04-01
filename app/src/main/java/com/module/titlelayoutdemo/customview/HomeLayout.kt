package com.module.titlelayoutdemo.customview

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.SearchView
import com.module.titlelayoutdemo.R
import com.module.titlelayoutdemo.adapter.FlowAdapter
import com.module.titlelayoutdemo.data.HistoryTag
import com.module.titlelayoutdemo.databinding.HomeLayoutBinding
import com.module.titlelayoutdemo.deepseek.DeepSeekApiClient
import com.module.titlelayoutdemo.deepseek.DeepSeekResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Copyright (c) 2023 Raysharp.cn. All rights reserved
 *
 * HomeLayout
 * @author longyanghe
 * @date 2023-02-01
 */
class HomeLayout(context: Context, attrs: AttributeSet? = null) : ContentView(context, attrs) {
    private var binding :HomeLayoutBinding
    private val waitingDialog = WaitingDialog(getContext(),3)

    init {
        val root = inflate(context,R.layout.home_layout,this)
        binding = HomeLayoutBinding.bind(root)
        waitingDialog.setMessage("正在进行DeepSeek查询，请稍等...")
        initDeepSeekSearch()
    }

    private fun initDeepSeekSearch() {
        val result = object : DeepSeekResult{
            override fun onResult(resut: String) {
                handler.post {
                    binding.searchResult.text = resut
                    waitingDialog.hide()

                }
            }
        }
        val client = DeepSeekApiClient(result)
        client.sendRequest("Hello, DeepSeek!")
        waitingDialog.show()
        binding.deepseekSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    GlobalScope.launch {
                        withContext(Dispatchers.IO){
                            client.sendRequest(query)
                        }
                        withContext(Dispatchers.Main){
                            waitingDialog.show()
                        }
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

    }



    var setUploadLog:(()->Unit)? = null
        set(value) {
            field = value
        }
}