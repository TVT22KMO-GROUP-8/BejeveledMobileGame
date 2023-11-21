package com.example.bejeweled.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import com.example.bejeweled.data.ScoreboardRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ScoreboardListViewModel(scoreboardRepository: ScoreboardRepository): ViewModel(){

    val scoreboardListUiState: StateFlow<ScoreboardListUiState> =
        scoreboardRepository.allPlayersByScore().map { ScoreboardListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ScoreboardListUiState()

            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}
data class ScoreboardListUiState(val scoreboardList: List<ScoreboardInfo> = listOf())