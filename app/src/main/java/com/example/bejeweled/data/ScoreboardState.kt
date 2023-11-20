package com.example.bejeweled.data

data class ScoreboardState(
    val scoreboardInfo: List<ScoreboardInfo> = emptyList(),
    val name: String = "",
    val score: String = "",
    val sortType: SortType = SortType.SCORE

)
