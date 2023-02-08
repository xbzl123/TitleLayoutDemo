package com.module.titlelayoutdemo.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.module.titlelayoutdemo.R
import com.module.titlelayoutdemo.databinding.MusicFragmentBinding

/**
 * Copyright (c) 2022 Raysharp.cn. All rights reserved
 *
 * AppLogLayout
 * @author longyanghe
 * @date 2022-05-25
 */
class MusicLayout(context: Context, attrs: AttributeSet? = null) : ContentView(context, attrs) {

    init {
        val root = LayoutInflater.from(context).inflate(R.layout.music_fragment,this)
        val binding = MusicFragmentBinding.bind(root)
        binding.btnUploadLog.setOnClickListener{
            setUploadLog?.invoke()
        }
    }
    var setUploadLog:(()->Unit)? = null
        set(value) {
            field = value
        }
}