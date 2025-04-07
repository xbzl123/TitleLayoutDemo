package com.module.titlelayoutdemo.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchInfoDao {
    @Query("SELECT * FROM searchinfos")
    fun getAll(): Flow<List<SearchInfo>>

    @Query("SELECT * FROM searchinfos WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<SearchInfo>

    @Query("SELECT * FROM searchinfos WHERE search_question LIKE :searchInfo AND " +
            "date LIKE :datetime LIMIT 1")
    fun findByName(searchInfo: String, datetime: String): SearchInfo

    @Insert
    fun insertAll(vararg searchInfo: SearchInfo)

    @Delete
    fun delete(searchInfo: SearchInfo)
}