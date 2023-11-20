package com.example.bejeweled.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScoreboardViewModel(
    private val dao: ScoreboardDao
): ViewModel(){

    private val _sortType = MutableStateFlow(SortType.SCORE)
    @OptIn(ExperimentalCoroutinesApi::class)
    private val _scoreboardInfo = _sortType.flatMapLatest { sortType ->
        when(sortType) {
            SortType.SCORE -> dao.getAllPlayersByScore()
            SortType.NAME -> dao.getAllPlayersByName()
        } }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _state = MutableStateFlow(ScoreboardState())
    val state = combine(_state, _sortType, _scoreboardInfo){state, sortType, scoreboardInfo->
        state.copy(
            scoreboardInfo = scoreboardInfo,
            sortType = sortType)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ScoreboardState())

    fun onEvent(event: ScoreboardEvent){
        when(event){
            is ScoreboardEvent.Delete -> {
                viewModelScope.launch{
                    dao.delete(event.scoreboardInfo)
                }
            }
            is ScoreboardEvent.Sort -> {
                _sortType.value = event.sortType
            }

            ScoreboardEvent.SaveScoreBoardInfo -> {
                val name = _state.value.name
                val score = _state.value.score
                if(name.isBlank() || score.isBlank()){
                    return
                    }
                val scoreboardInfo = ScoreboardInfo(
                    name = name,
                    score = score
                )
                viewModelScope.launch {
                    dao.insert(scoreboardInfo)
                }
                _state.update{
                    it.copy(name = "", score = "")
                }

            }
            is ScoreboardEvent.SetName -> {
                _state.update{
                    it.copy(name = event.name)
                }
            }
            is ScoreboardEvent.SetScore -> {
                _state.update{
                    it.copy(score = event.score)
                }
            }
        }
    }
}