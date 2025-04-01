package com.module.titlelayoutdemo.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.module.titlelayoutdemo.R
import com.module.titlelayoutdemo.adapter.FlowAdapter
import com.module.titlelayoutdemo.data.HistoryTag
import com.module.titlelayoutdemo.databinding.MusicFragmentBinding

/**
 * Copyright (c) 2022 Raysharp.cn. All rights reserved
 *
 * AppLogLayout
 * @author longyanghe
 * @date 2022-05-25
 */
class MusicLayout(context: Context, attrs: AttributeSet? = null) : ContentView(context, attrs) {
    private var binding: MusicFragmentBinding

    init {
        val root = LayoutInflater.from(context).inflate(R.layout.music_fragment,this)
        binding = MusicFragmentBinding.bind(root)
        initFlowLayout()

        binding.btnUploadLog.setOnClickListener{
            setUploadLog?.invoke()
        }
    }

    private fun initFlowLayout() {
        val recycler = binding.flowRecyclerview
        recycler.layoutManager = FlowLayoutManager()
        var flowList = arrayListOf<HistoryTag>()
        flowList.add(HistoryTag(0,"洗衣机"))
        flowList.add(HistoryTag(1,"电视机"))
        flowList.add(HistoryTag(2,"油烟机"))
        flowList.add(HistoryTag(3,"吹风机"))
        flowList.add(HistoryTag(4,"空调"))
        flowList.add(HistoryTag(5,"灯"))
        flowList.add(HistoryTag(6,"风扇"))
        flowList.add(HistoryTag(7,"柜子"))
        flowList.add(HistoryTag(8,"橱柜"))
        flowList.add(HistoryTag(9,"落地扇"))
        flowList.add(HistoryTag(10,"笔记本电脑"))
        flowList.add(HistoryTag(11,"热水器"))
        flowList.add(HistoryTag(12,"煤气灶"))

        recycler.adapter = FlowAdapter(R.layout.flow_list,flowList)
    }

    var setUploadLog:(()->Unit)? = null
        set(value) {
            field = value
        }
}