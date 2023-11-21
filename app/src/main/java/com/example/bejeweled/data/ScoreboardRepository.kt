package com.example.bejeweled.data

import kotlinx.coroutines.flow.Flow

interface ScoreboardRepository {

    fun allPlayersByScore(): Flow<List<ScoreboardInfo>>

    suspend fun insert(scoreboardInfo: ScoreboardInfo)

    suspend fun delete(scoreboardInfo: ScoreboardInfo)


}