package com.module.titlelayoutdemo.room

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "searchinfos")
data class SearchInfo(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val date: String?,
    val search_question: String?,
    @NonNull var search_result: String, // 确保非空
    @NonNull val is_favourite: Int // 确保非空
)
