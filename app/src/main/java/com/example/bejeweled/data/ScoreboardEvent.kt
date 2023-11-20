package com.example.bejeweled.data

sealed interface ScoreboardEvent{
    object SaveScoreBoardInfo: ScoreboardEvent
    data class SetName(val name: String): ScoreboardEvent
    data class SetScore(val score: String): ScoreboardEvent
    data class Delete(val scoreboardInfo: ScoreboardInfo): ScoreboardEvent
    data class Sort(val sortType: SortType): ScoreboardEvent

}