package com.module.titlelayoutdemo.customview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.children

/**
 * Copyright (c) 2022 Raysharp.cn. All rights reserved
 *
 * TapsMenu 把新增的View水平排序
 * @author longyanghe
 * @date 2022-01-28
 */
class TapsMenu(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    /**
     * 切换不同的布局视图
     */
    fun showSelectView(selectView: FlagTextView) {
        for (pos in 0 until childCount){
            val childView = getChildAt(pos) as FlagTextView
                if(childView.text.contentEquals(selectView.text)){
                    childView.isSelected = true
                    childView.childView?.visibility =  View.VISIBLE
                }else{
                    childView.isSelected = false
                    childView.childView?.visibility =  View.GONE
                }
            }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val childView = getChildAt(0) as FlagTextView
        setMeasuredDimension(widthMeasureSpec,childView.textSize.toInt()*2)
    }

    override fun addView(child: View?) {
        val childView = child as FlagTextView
        if (childCount == 0){
            childView.isSelected = true
            childView.childView!!.visibility = VISIBLE
        } else {
            childView.isSelected = false
            childView.childView!!.visibility = GONE
        }
        super.addView(child)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (pos in 0 until childCount){
            val childView = getChildAt(pos) as FlagTextView

            val offsetX = childView.text.length * childView.textSize/3
            childView.measure(measuredWidth/childCount,
                childView.textSize.toInt()*2)
            childView.layout((measuredWidth/childCount/2 * (pos * 2 + 1) - offsetX).toInt(),
                0,measuredWidth/childCount*(pos+1),childView.textSize.toInt()*2)
        }
    }
}