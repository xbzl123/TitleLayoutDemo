package com.module.titlelayoutdemo.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.module.titlelayoutdemo.R
import com.module.titlelayoutdemo.databinding.CamerasListBinding

/**
 * Copyright (c) 2022 Raysharp.cn. All rights reserved
 *
 * CameraNameListAdapter
 * @author longyanghe
 * @date 2022-01-25
 */
/**
 * 检查是否除了第一位全部后面元素包含没有选定的情况
 */
fun checkItemIsNotAllSelected(selectStateList: ArrayList<CameraInfo>):Boolean {
    var temp = arrayListOf<CameraInfo>()
    temp.addAll(selectStateList)
        temp.apply {
            this.map {
                if(!it.isSelect){
                    return true
                }
            }
        }
    return false
}

/**
 * 包装类 CameraName
 */
data class CameraInfo(val name:String, var isSelect:Boolean)

/**
 *  Camera info适配器
 */
class InfoListAdapter(layoutResId: Int, cameraNameList: ArrayList<CameraInfo>) :BaseQuickAdapter<CameraInfo,BaseViewHolder>(layoutResId,cameraNameList){

    override fun convert(holder: BaseViewHolder, item: CameraInfo) {
        val binding = CamerasListBinding.bind(holder.itemView)
        holder.setText(R.id.camera_name,item.name)
        binding.checkboxCameraName.isChecked = item.isSelect
    }
}