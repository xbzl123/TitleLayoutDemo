package com.module.titlelayoutdemo.customview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import com.blankj.utilcode.util.ToastUtils
import com.module.titlelayoutdemo.R
import com.module.titlelayoutdemo.adapter.SearchHistoryAdapter
import com.module.titlelayoutdemo.adapter.SuggestionsAdapter
import com.module.titlelayoutdemo.databinding.HomeLayoutBinding
import com.module.titlelayoutdemo.deepseek.DeepSeekApiClient
import com.module.titlelayoutdemo.deepseek.DeepSeekResult
import com.module.titlelayoutdemo.room.AppDBHandler
import com.module.titlelayoutdemo.room.SearchInfo
import com.module.titlelayoutdemo.room.SearchInfoDao
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar


/**
 * Copyright (c) 2023 Raysharp.cn. All rights reserved
 *
 * HomeLayout
 * @author longyanghe
 * @date 2023-02-01
 */
class HomeLayout(context: Context, attrs: AttributeSet? = null) : ContentView(context, attrs) {
    private var searchInfo: SearchInfo? = null
    private lateinit var searchDao: SearchInfoDao
    private var binding :HomeLayoutBinding
    private val waitingDialog = WaitingDialog(getContext(),3)

    init {
        val root = inflate(context,R.layout.home_layout,this)
        binding = HomeLayoutBinding.bind(root)
        waitingDialog.setMessage("正在进行DeepSeek查询，请稍等...")
        initFlowLayout()
        initDeepSeekSearch()
    }

    private fun initFlowLayout() {
        val recycler = binding.historyRecyclerview
        recycler.layoutManager = FlowLayoutManager()
        searchDao = AppDBHandler.getRoomDBObject()!!.searchDao()
        GlobalScope.launch {
            searchDao.getAll().collect{
                recycler.adapter = SearchHistoryAdapter(R.layout.flow_list,
                    it as ArrayList<SearchInfo>)
                withContext(Dispatchers.Main) {
                    recycler.run { adapter?.notifyDataSetChanged() }
                }
            }
        }
        recycler.addOnItemTouchListener(object :OnItemTouchListener{
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                val childView = rv.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && e.getAction() == MotionEvent.ACTION_UP) {
                    val position = rv.getChildAdapterPosition(childView)
                    GlobalScope.launch {
                        searchDao.getAll().collect{
                            withContext(Dispatchers.Main) {
                                it[position].is_selected = 1
                                recycler.adapter = SearchHistoryAdapter(R.layout.flow_list,
                                    it as ArrayList<SearchInfo>)
                                binding.searchResult.text = it[position].search_result
                            }
                        }
                    }
                }
                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
            }
        })
    }
    private fun initDeepSeekSearch() {
        val result = object : DeepSeekResult{
            override fun onResult(result: String) {
                if (result.contains("Error")) {
                    GlobalScope.launch {
                        withContext(Dispatchers.IO){
                            if (searchInfo != null) {
                                searchInfo!!.search_result = result
                                searchDao.insertAll(searchInfo!!)
                            }
                        }
                        withContext(Dispatchers.Main){
                            binding.searchResult.text = result
                            waitingDialog.hide()
                        }
                    }
                } else {
                    waitingDialog.hide()
                    ToastUtils.showLong(result)
                }
            }
        }
        val client = DeepSeekApiClient(result)
        client.sendRequest("Hello, DeepSeek!")
        waitingDialog.show()
        val suggestions = ArrayList<String>()
        suggestions.add("Apple")
        suggestions.add("Banana")
        val adapter = ArrayAdapter(
            context,
            android.R.layout.simple_dropdown_item_1line, suggestions
        )
        val suggestionsAdapter = SuggestionsAdapter(context,adapter)
        binding.deepseekSearch.setSuggestionsAdapter(suggestionsAdapter)
        binding.deepseekSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    GlobalScope.launch {
                        withContext(Dispatchers.IO){
                            val calendar = Calendar.getInstance()
                            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                            val formatted = dateFormat.format(calendar.time)
                            searchInfo = SearchInfo(0,formatted,query,"",0,0)
                            client.sendRequest(query)
                        }
                        withContext(Dispatchers.Main){
                            waitingDialog.show()
                        }
                    }
                }
                return false
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