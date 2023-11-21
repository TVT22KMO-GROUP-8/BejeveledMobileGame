package com.example.bejeweled.ui


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.bejeweled.data.ScoreboardListUiState
import com.example.bejeweled.data.ScoreboardListViewModel
import com.example.bejeweled.data.ScoreboardUiState
import com.example.bejeweled.data.ScoreboardViewModel
import com.example.bejeweled.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.bejeweled.data.ScoreboardInfo


object ScoreboardDestination : NavigationDestination {
    override val route = "scoreboard"
    override val titleRes = R.string.scoreboard_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoreBoard(
    modifier: Modifier = Modifier,
    viewModel: ScoreboardListViewModel = viewModel(factory = AppViewModelProvider.Factory)

) {
       val scoreboardListUiState by viewModel.scoreboardListUiState.collectAsState()
       Scaffold(
              topBar = {
                CenterAlignedTopAppBar(
                     title = { Text("Scoreboard") },
                )
              },

       ) { innerPadding ->
                ScoreboardBody(
                    scoreboardList = scoreboardListUiState.scoreboardList,
                    modifier = Modifier.padding(innerPadding)

                )

       }
}
@Composable
private fun ScoreboardBody(
    scoreboardList: List<ScoreboardInfo>, modifier: Modifier = Modifier
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if (scoreboardList.isEmpty()) {
            Text(
                text = "No Scores found",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        } else {
            ScoreboardList(
                scoreboardList = scoreboardList,
            )
        }
    }

}
@Composable
private fun ScoreboardList(
    scoreboardList: List<ScoreboardInfo>
) {
    LazyColumn {
        items(items= scoreboardList) { scoreboardInfo ->
            InventoryItem(
                scoreboardInfo = scoreboardInfo,
                modifier = Modifier
                    .padding(16.dp))
        }
    }
}

@Composable
private fun InventoryItem(
    scoreboardInfo: ScoreboardInfo, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = scoreboardInfo.name,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = scoreboardInfo.score.toString(),
                    style = MaterialTheme.typography.titleMedium
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScoreboardBodyPreview() {

    ScoreboardBody(listOf(
        ScoreboardInfo(1, "Test", 100))
    )
}

