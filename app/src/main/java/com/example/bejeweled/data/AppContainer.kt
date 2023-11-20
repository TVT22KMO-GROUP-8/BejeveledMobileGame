package com.example.bejeweled.data

import android.content.Context

interface AppContainer {
    val scoreboardRepository: ScoreboardRepository
}
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [ItemsRepository]
     */
    override val scoreboardRepository: ScoreboardRepository by lazy {
        OfflineScoreboardRepository(ScoreboardDatabase.getDatabase(context).scoreboardDao())
    }
}