package com.example.bejeweled.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface ScoreboardDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(scoreboardInfo: ScoreboardInfo)

    @Delete
    suspend fun delete(scoreboardInfo: ScoreboardInfo)

    @Query("SELECT * FROM score_table ORDER BY score DESC")
    fun getAllPlayersByScore(): Flow<List<ScoreboardInfo>>


}