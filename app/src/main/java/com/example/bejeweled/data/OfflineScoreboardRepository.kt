package com.example.bejeweled.data

import kotlinx.coroutines.flow.Flow

class OfflineScoreboardRepository(private val scoreboardDao: ScoreboardDao): ScoreboardRepository{

        override fun allPlayersByScore(): Flow<List<ScoreboardInfo>> = scoreboardDao.getAllPlayersByScore()


        override suspend fun insert(scoreboardInfo: ScoreboardInfo) = scoreboardDao.insert(scoreboardInfo)


        override suspend fun delete(scoreboardInfo: ScoreboardInfo) = scoreboardDao.delete(scoreboardInfo)
}