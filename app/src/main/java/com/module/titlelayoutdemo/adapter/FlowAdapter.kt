package com.module.titlelayoutdemo.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.module.titlelayoutdemo.R
import com.module.titlelayoutdemo.data.HistoryTag
import com.module.titlelayoutdemo.databinding.FlowListBinding

class FlowAdapter(layoutResId: Int, historyTagList: ArrayList<HistoryTag>) :
    BaseQuickAdapter<HistoryTag, BaseViewHolder>(layoutResId,historyTagList){

    override fun convert(holder: BaseViewHolder, item: HistoryTag) {
        val binding = FlowListBinding.bind(holder.itemView)
        holder.setText(R.id.history_name,item.name)
    }
}