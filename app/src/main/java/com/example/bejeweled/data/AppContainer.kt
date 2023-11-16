package com.example.bejeweled.data

import android.content.Context

interface AppContainer {
    val scoreboardRepository: ScoreboardRepository
}
class AppContainerImpl(private val context: Context) : AppContainer {
    override val scoreboardRepository: ScoreboardRepository by lazy {
         OfflineScoreboardRepository(ScoreboardDatabase.getDatabase(context).scoreboardDao())
    }
}