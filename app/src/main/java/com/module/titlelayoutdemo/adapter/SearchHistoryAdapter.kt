package com.module.titlelayoutdemo.adapter

import androidx.recyclerview.widget.DiffUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.module.titlelayoutdemo.R
import com.module.titlelayoutdemo.databinding.FlowListBinding
import com.module.titlelayoutdemo.room.SearchInfo

class SearchHistoryAdapter(layoutResId: Int, historyTagList: ArrayList<SearchInfo>) :
    BaseQuickAdapter<SearchInfo, BaseViewHolder>(layoutResId,historyTagList){

    override fun convert(holder: BaseViewHolder, item: SearchInfo) {
        val binding = FlowListBinding.bind(holder.itemView)
        holder.setText(R.id.history_name,item.search_question)
        holder.setBackgroundColor(R.id.history_name,if (item.is_selected == 0)
            context.getColor(R.color.transparent) else context.getColor(R.color.red))
    }

}