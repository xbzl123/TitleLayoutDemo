package com.module.titlelayoutdemo.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.module.titlelayoutdemo.R

/**
 * Copyright (c) 2023 Raysharp.cn. All rights reserved
 *
 * HomeLayout
 * @author longyanghe
 * @date 2023-02-01
 */
class HomeLayout(context: Context, attrs: AttributeSet? = null) : ContentView(context, attrs) {

    init {
        val root = LayoutInflater.from(context).inflate(R.layout.home_layout,this)
    }
    var setUploadLog:(()->Unit)? = null
        set(value) {
            field = value
        }
}