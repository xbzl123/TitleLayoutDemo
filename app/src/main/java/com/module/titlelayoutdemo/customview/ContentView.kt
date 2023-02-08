package com.module.titlelayoutdemo.customview

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * Copyright (c) 2023 Raysharp.cn. All rights reserved
 *
 * ContentView
 * @author longyanghe
 * @date 2023-02-01
 */
open class ContentView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    var flagText: FlagTextView? = null

}