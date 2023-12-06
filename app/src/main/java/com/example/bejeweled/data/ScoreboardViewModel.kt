package com.example.bejeweled.data

import android.icu.text.NumberFormat
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/**
 * ViewModel to validate and insert items in the Room database.
 */
class ScoreboardViewModel : ViewModel() {



    var scoreboardUiState by mutableStateOf(ScoreboardUiState())
    private set



    fun updateUiState(scoreboardDetails: ScoreboardDetails){
        scoreboardUiState = ScoreboardUiState(scoreboardDetails = scoreboardDetails, isEntryValid = validateInput(scoreboardDetails))

    }

    private fun validateInput(uiState: ScoreboardDetails = scoreboardUiState.scoreboardDetails): Boolean {
        return with(uiState) {
            name.isNotBlank()
        }
    }
}


data class ScoreboardUiState(
    val scoreboardDetails: ScoreboardDetails = ScoreboardDetails(),
    val isEntryValid: Boolean = false

)
data class ScoreboardDetails(
    var name: String = "",
    var score: Int = 0
)

fun ScoreboardDetails.toScoreboardInfo(): ScoreboardInfo = ScoreboardInfo(
        name = name,
        score = score
    )

fun ScoreboardInfo.toScoreboardUiState(isEntryValid: Boolean = false): ScoreboardUiState = ScoreboardUiState(
        scoreboardDetails = ScoreboardDetails(),
        isEntryValid = isEntryValid
    )
fun ScoreboardInfo.toScoreboardDetails(): ScoreboardDetails = ScoreboardDetails(
        name = name,
        score = score
)
