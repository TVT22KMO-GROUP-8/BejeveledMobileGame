package com.example.bejeweled.ui


import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bejeweled.R
import com.example.bejeweled.data.ScoreboardEvent
import com.example.bejeweled.data.ScoreboardInfo
import com.example.bejeweled.data.ScoreboardState
import com.example.bejeweled.data.SortType

import com.example.bejeweled.ui.theme.londrinaFamily


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoreBoard(
    state: ScoreboardState,
    onEvent: (ScoreboardEvent) -> Unit,
    modifier: Modifier = Modifier
) {
       Scaffold(
           topBar ={ ScoreBoardTopAppBar() }

       ) { padding ->
           LazyColumn(
               contentPadding = padding,
               modifier = Modifier.fillMaxSize(),
               verticalArrangement = Arrangement.spacedBy(16.dp)
           ){
               item{
                   Row (
                       modifier = Modifier
                           .fillMaxWidth()
                           .horizontalScroll(rememberScrollState())
                   ){
                       SortType.values().forEach{ sortType ->
                           Row(
                              modifier = Modifier
                                  .clickable {
                                      onEvent(ScoreboardEvent.Sort(sortType))
                                  },
                                 verticalAlignment = Alignment.CenterVertically
                           ){
                               RadioButton(
                                   selected = state.sortType == sortType,
                                   onClick = {
                                       onEvent(ScoreboardEvent.Sort(sortType))
                                   })
                               Text(text = sortType.name)
                           }
                       }

                   }
               }
               items(state.scoreboardInfo){ scoreboardInfo ->
                   Row (
                       modifier = Modifier.fillMaxWidth()
                   ){
                       Column(
                           modifier = Modifier.weight(1f)
                       ) {
                           Text(
                               text = "Name: ${scoreboardInfo.name}",
                               fontSize = 20.sp
                           )
                           Text(
                               text = "Score: ${scoreboardInfo.score}",
                               fontSize = 20.sp
                           )
                       }
                   }
               }

           }
       }

}



//@Composable
//fun ScoreItem(
//    modifier: Modifier = Modifier,
//    scoreboardInfo: ScoreboardInfo
//){
//    Card (modifier = modifier){
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(dimensionResource(R.dimen.padding_small))
//
//        ) {
//            PlayerInformation(scoreboardInfo.name, scoreboardInfo.score)
//            Spacer(modifier = Modifier.weight(1f))
//
//        }
//    }
//}



//@Composable
//fun PlayerInformation(
//    @StringRes name: Int,
//    score: Int,
//    modifier: Modifier = Modifier
//){
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(dimensionResource(R.dimen.padding_small))
//    ) {
//        Text(
//            text = "Name: "+ stringResource(id = name),
//            style = MaterialTheme.typography.headlineSmall
//        )
//        Text(
//            text = "Score: $score",
//            style = MaterialTheme.typography.headlineSmall
//            )
//
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoreBoardTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(

        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,

                ){
                Text(
                    text = "Score Board",
                    style = MaterialTheme.typography.displayLarge,

                )
            }
        }
    )
}