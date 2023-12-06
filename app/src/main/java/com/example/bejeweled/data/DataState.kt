package com.example.bejeweled.data

sealed class DataState{
    class Success(val scoreboardInfo: List<ScoreboardInfo>): DataState()
    class Error(val exception: Exception): DataState()
    object Loading: DataState()
    object Empty: DataState()
}
