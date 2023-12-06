package com.example.bejeweled.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.bejeweled.R
import com.example.bejeweled.ui.navigation.NavigationDestination
import android.media.MediaPlayer


import com.example.bejeweled.ui.theme.londrinaFamily

object StartMenuDestination : NavigationDestination {
    override val route = "start_menu"
    override val titleRes = R.string.start_menu_title
}
@Composable
fun StartMenu(
    scoreboardDestination: () -> Unit,
    gameboardDestination: () -> Unit,
    settingsDestination: () -> Unit,
    modifier: Modifier = Modifier
) {
    //Music
    val context = LocalContext.current
    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.le_bijouterie_main_menu) }
    LaunchedEffect(Unit) {
        mediaPlayer.isLooping = true
        mediaPlayer.start()
    }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }

    // Define your start menu UI here
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color(0xFFE5E5E5)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Le Bijouterie",
            style = TextStyle(
                fontSize = 60.sp,
                color = Black,
                fontFamily = londrinaFamily,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(16.dp)
        )

        Button(
            onClick = { gameboardDestination()
                mediaPlayer.stop()
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Start Game")
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = settingsDestination,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Settings")
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = scoreboardDestination,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Scoreboard")
        }

    }
}
