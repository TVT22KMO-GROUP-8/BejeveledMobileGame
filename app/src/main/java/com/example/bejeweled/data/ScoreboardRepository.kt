package com.example.bejeweled.data

import kotlinx.coroutines.flow.Flow

interface ScoreboardRepository {

    fun getAllPlayers(): Flow<List<ScoreboardInfo>>

    suspend fun insertScoreboardInfo(scoreboardInfo: ScoreboardInfo)

    suspend fun deleteScoreboardInfo(scoreboardInfo: ScoreboardInfo)

    suspend fun updateScoreboardInfo(scoreboardInfo: ScoreboardInfo)
}