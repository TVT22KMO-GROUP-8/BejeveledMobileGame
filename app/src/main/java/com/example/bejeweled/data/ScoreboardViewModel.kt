package com.example.bejeweled.data

import android.icu.text.NumberFormat
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/**
 * ViewModel to validate and insert items in the Room database.
 */
class ScoreboardViewModel(private val scoreboardRepository: ScoreboardRepository) : ViewModel() {

    var scoreboardUiState by mutableStateOf(ScoreboardUiState())
    private set



    fun updateUiState(scoreboardDetails: ScoreboardDetails){
        scoreboardUiState = ScoreboardUiState(scoreboardDetails = scoreboardDetails, isEntryValid = validateInput(scoreboardDetails))

    }

    suspend fun saveScoreboardInfo(){
        if(validateInput()){
            scoreboardRepository.insert(scoreboardUiState.scoreboardDetails.toScoreboardInfo())
        }
    }
    private fun validateInput(uiState: ScoreboardDetails = scoreboardUiState.scoreboardDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && score.isNotBlank()
        }
    }


}

data class ScoreboardUiState(
    val scoreboardDetails: ScoreboardDetails = ScoreboardDetails(),
    val isEntryValid: Boolean = false

)
data class ScoreboardDetails(
    val id: Int = 0,
    var name: String = "",
    var score: String = ""
)

fun ScoreboardDetails.toScoreboardInfo(): ScoreboardInfo = ScoreboardInfo(
        id = id,
        name = name,
        score = score.toIntOrNull() ?: 0
    )

fun ScoreboardInfo.toScoreboardUiState(isEntryValid: Boolean = false): ScoreboardUiState = ScoreboardUiState(
        scoreboardDetails = ScoreboardDetails(),
        isEntryValid = isEntryValid
    )
fun ScoreboardInfo.toScoreboardDetails(): ScoreboardDetails = ScoreboardDetails(
        id = id,
        name = name,
        score = score.toString()
)
