package com.example.bejeweled.data

import kotlinx.coroutines.flow.Flow


class OfflineScoreboardRepository (private val scoreboardDao: ScoreboardDao): ScoreboardRepository({
    override fun getAllPlayers(): Flow<List<ScoreboardInfo>> = scoreboardDao.getAllPlayers()

    override suspend fun insertScoreboardInfo(scoreboardInfo: ScoreboardInfo) = scoreboardDao.insert(scoreboardInfo)

    override suspend fun deleteScoreboardInfo(scoreboardInfo: ScoreboardInfo) = scoreboardDao.delete(scoreboardInfo)

    override suspend fun updateScoreboardInfo(scoreboardInfo: ScoreboardInfo) = scoreboardDao.update(scoreboardInfo)
}