package com.example.bejeweled.ui


import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bejeweled.R
import com.example.bejeweled.data.Player
import com.example.bejeweled.data.players
import com.example.bejeweled.ui.theme.londrinaFamily


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ScoreBoard(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            ScoreBoardTopAppBar()
        }
    ) {it ->
    LazyColumn(contentPadding = it, modifier = modifier.background(color = Color(0xFFE5E5E5))) {
        items(players) {
            ScoreItem(
                player = Player(name = it.name, score = it.score),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_small))
            )
        }
    }
}
}


@Composable
fun ScoreItem(
    modifier: Modifier = Modifier,
    player: Player
){
    Card (modifier = modifier){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_small))

        ) {
            PlayerInformation(player.name, player.score)
            Spacer(modifier = Modifier.weight(1f))

        }
    }
}



@Composable
fun PlayerInformation(
    @StringRes name: Int,
    score: Int,
    modifier: Modifier = Modifier
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_small))
    ) {
        Text(
            text = "Name: "+ stringResource(id = name),
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = "Score: $score",
            style = MaterialTheme.typography.headlineSmall
            )

    }
}

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