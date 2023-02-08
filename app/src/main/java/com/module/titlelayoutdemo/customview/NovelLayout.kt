package com.module.titlelayoutdemo.customview

import android.content.Context
import android.util.AttributeSet
import android.widget.CheckBox
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.module.titlelayoutdemo.R
import com.module.titlelayoutdemo.adapter.CameraInfo
import com.module.titlelayoutdemo.adapter.InfoListAdapter
import com.module.titlelayoutdemo.adapter.checkItemIsNotAllSelected
import com.module.titlelayoutdemo.databinding.NovelFragmentBinding

/**
 * Copyright (c) 2022 Raysharp.cn. All rights reserved
 *
 * NovelLayout
 * @author longyanghe
 * @date 2022-05-25
 */
    class NovelLayout(context: Context, attrs: AttributeSet? = null) : ContentView(context, attrs) {

        private var binding: NovelFragmentBinding

    init {
        val root = inflate(context,R.layout.novel_fragment,this)
        binding = NovelFragmentBinding.bind(root)
        binding.scrollView.setFillViewport(true)
        binding.btnAuthorize.setOnClickListener{
            setUploadLog?.invoke()
        }
    }

    var setUploadLog:(()->Unit)? = null
        set(value) {
            field = value
        }

    fun initAdapter(cameraInfoList:ArrayList<CameraInfo>){
        binding.cameraRecyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))

            var cameraInfoListAdapter =
                InfoListAdapter(R.layout.cameras_list, cameraInfoList)
            cameraInfoListAdapter.addChildClickViewIds(R.id.checkbox_camera_name)
            cameraInfoListAdapter.setOnItemChildClickListener{
                    adapter,view,pos->
                val isChecked = (view as CheckBox).isChecked
                cameraInfoList[pos].isSelect = isChecked
                if(pos == 0){
                    cameraInfoList.map {
                        it.isSelect = isChecked
                    }
                    adapter.notifyDataSetChanged()
                }else{
                    cameraInfoList[0].isSelect = !checkItemIsNotAllSelected(cameraInfoList)
                    adapter.notifyItemChanged(0)
                }
            }
            adapter = cameraInfoListAdapter
        }
    }
}