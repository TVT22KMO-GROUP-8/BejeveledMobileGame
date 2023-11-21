package com.example.bejeweled.ui


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bejeweled.R
import com.example.bejeweled.data.ScoreboardDetails
import com.example.bejeweled.data.ScoreboardUiState
import com.example.bejeweled.data.ScoreboardViewModel
import com.example.bejeweled.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object ScoreboardDestination : NavigationDestination {
    override val route = "scoreboard"
    override val titleRes = R.string.scoreboard_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoreBoard(
    modifier: Modifier = Modifier,
    viewModel: ScoreboardViewModel = viewModel(factory = AppViewModelProvider.Factory)

) {
       val coroutineScope = rememberCoroutineScope()
       Scaffold(
              topBar = {
                CenterAlignedTopAppBar(
                     title = { Text("Scoreboard") },
                )
              },



       ) { innerPadding ->
          ScoreboardEntryBody(
              scoreboardUiState = viewModel.scoreboardUiState,
              onItemValueChange = viewModel::updateUiState,
              onSaveClick = {
                  coroutineScope.launch {
                      viewModel.saveScoreboardInfo()
                  }
              },
              modifier = Modifier
                  .padding(innerPadding)
                  .verticalScroll(rememberScrollState())
                  .fillMaxWidth())
       }
}

@Composable
fun ScoreboardEntryBody(
    scoreboardUiState: ScoreboardUiState,
    onItemValueChange: (ScoreboardDetails) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.padding(16.dp)
    ) {

        ScoreboardInputForm(
            scoreboardDetails = scoreboardUiState.scoreboardDetails,
            onValueChange = onItemValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = scoreboardUiState.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Tallenna")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoreboardInputForm(
    scoreboardDetails: ScoreboardDetails,
    modifier: Modifier = Modifier,
    onValueChange: (ScoreboardDetails) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        OutlinedTextField(
            value = scoreboardDetails.name,
            onValueChange = { onValueChange(scoreboardDetails.copy(name = it)) },
            label = { "name" },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = scoreboardDetails.score,
            onValueChange = { onValueChange(scoreboardDetails.copy(score = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            label = { "score" },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        if (enabled) {
            Text(
                text = "Täytä kaikki kentät",
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}
