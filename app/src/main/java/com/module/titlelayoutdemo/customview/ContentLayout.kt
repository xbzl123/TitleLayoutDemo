package com.module.titlelayoutdemo.customview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import kotlin.math.absoluteValue

/**
 * Copyright (c) 2023 Raysharp.cn. All rights reserved
 *
 * ContentLayout
 * @author longyanghe
 * @date 2023-02-01
 */
class ContentLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    private var gestureDetector: GestureDetector
    val SLIDE_ACTION_LEN = 60f
    private var onInterceptTouch = false

    init {

        gestureDetector = GestureDetector(context,object : GestureDetector.OnGestureListener{
            override fun onDown(p0: MotionEvent): Boolean {
                return true
            }

            override fun onShowPress(p0: MotionEvent) {
            }

            override fun onSingleTapUp(p0: MotionEvent): Boolean {
                return false
            }

            override fun onScroll(
                p0: MotionEvent?,
                p1: MotionEvent,
                p2: Float,
                p3: Float
            ): Boolean {
                Log.e("ContentLayout","onScroll----->p0:"+p0?.action+",p1:"+p1?.action)
                return false
            }

            override fun onLongPress(p0: MotionEvent) {
            }

            override fun onFling(p0: MotionEvent?, p1: MotionEvent, p2: Float, p3: Float): Boolean {
                p0?.let {
                    val slideX = p0?.x?.minus(p1!!.x)?.minus(SLIDE_ACTION_LEN)
                    val slideY = p0?.y?.minus(p1!!.y)?.minus(SLIDE_ACTION_LEN)
                    val slideAbsX = p0!!.x!!.minus(p1!!.x)!!.absoluteValue!!.minus(SLIDE_ACTION_LEN)
                    val slideAbsY = p0!!.y!!.minus(p1!!.y)!!.absoluteValue!!.minus(SLIDE_ACTION_LEN)
                    Log.e("ContentLayout","onFling----->")

                    if (slideX!! > 0 && slideAbsX > slideAbsY){
                        switchView(true)
                        return true
                    }else if (slideX!! < 0 && slideAbsX > slideAbsY){
                        switchView(false)
                        return true
                    }
                    if (slideY!! > 0 && slideAbsX < 0){
                        return true
                    }
                    if (slideY!! < 0 && slideAbsX < 0){
                        return true
                    }
                }
                return false
            }
        })
    }

    fun switchView(left: Boolean){
        var curPos = 0
        //获取当前选中的页位置
        for (pos in 0 until childCount){
            val childView = getChildAt(pos) as ContentView
            if (childView.flagText!!.isSelected){
                 curPos = pos
            }
        }
        if (left){
            //设置滑动界限
            if(curPos == 0) return
            curPos-=1
        } else{
            if(curPos == childCount-1) return
            curPos+=1
        }
        //滑动页面
        for (pos in 0 until childCount){
            val childView = getChildAt(pos) as ContentView
            if(pos == curPos){
                childView.flagText?.isSelected = true
                childView.visibility =  View.VISIBLE
            }else{
                childView.flagText?.isSelected = false
                childView.visibility =  View.GONE
            }
            childView.flagText?.invalidate()

        }
    }

    var oldX = 0f
    var oldY = 0f
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        //水平滑动则拦截事件
        onInterceptTouch = (ev?.x!!-oldX).absoluteValue > (ev?.y!!-oldY).absoluteValue
        oldX = ev?.x!!
        oldY = ev?.y!!
        Log.e("onInterceptTouchEvent","onInterceptTouchEvent----->"+onInterceptTouch)
        when (ev.action){
            MotionEvent.ACTION_MOVE->{
                return onInterceptTouch
            }
        }
        return false
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event!!)
    }
}