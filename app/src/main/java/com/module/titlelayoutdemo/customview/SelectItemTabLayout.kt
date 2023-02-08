package com.module.titlelayoutdemo.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.module.titlelayoutdemo.R
import com.module.titlelayoutdemo.adapter.CameraInfo
import com.module.titlelayoutdemo.databinding.SelectItemTapLayoutBinding

/**
 * Copyright (c) 2022 Raysharp.cn. All rights reserved
 *
 * SelectItemTabLayout 包含2个自定义标题切换的布局容器，可以增减
 * @author longyanghe
 * @date 2022-01-24
 */
class SelectItemTabLayout(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    private var novelLayout: NovelLayout
    private var homeLayout: HomeLayout
    private var musicLayout: MusicLayout
    private var binding:SelectItemTapLayoutBinding

    init {
        val root = LayoutInflater.from(context).inflate(R.layout.select_item_tap_layout, this)
        binding = SelectItemTapLayoutBinding.bind(root)

        homeLayout = HomeLayout(context!!,null)
        musicLayout = MusicLayout(context!!,null)
        novelLayout = NovelLayout(context!!,null)
        addViewAndFlag(homeLayout,R.string.home)
        addViewAndFlag(musicLayout,R.string.music)
        addViewAndFlag(novelLayout,R.string.novel)

    }

    fun addViewAndFlag(view: ContentView, content: Int){
        val flagTextView = FlagTextView(context!!,null,content)
        view.flagText = flagTextView
        addViewToContainer(flagTextView,view)
    }

    private fun addViewToContainer(flagTextView:FlagTextView, view: View){
        flagTextView.apply {
            binding.container.addView(view)
            binding.taps.addView(this)
            childView = view
            setOnClickListener {
                binding.taps.showSelectView(this)
            }
        }
    }

    fun loadData(cameraInfoList: ArrayList<CameraInfo>) {
        novelLayout.initAdapter(cameraInfoList)
    }
    var appLogAct:(()->Unit)? = null
        set(value) {
            musicLayout?.setUploadLog = value
        }

    var deviceLogAct:(()->Unit)? = null
        set(value) {
            novelLayout?.setUploadLog = value
        }
}