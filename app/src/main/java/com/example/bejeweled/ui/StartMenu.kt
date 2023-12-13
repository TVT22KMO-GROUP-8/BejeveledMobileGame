package com.example.bejeweled.ui

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bejeweled.R
import com.example.bejeweled.ui.navigation.NavigationDestination
import com.example.bejeweled.ui.theme.BejeweledTheme
import com.example.bejeweled.ui.theme.ThemeOption
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle

object StartMenuDestination : NavigationDestination {
    override val route = "start_menu"
    override val titleRes = R.string.start_menu_title
}
@Composable
fun StartMenu(
    selectedTheme: ThemeOption,
    scoreboardDestination: () -> Unit,
    gameboardDestination: () -> Unit,
    settingsDestination: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    var settings by remember { mutableStateOf(loadSettings(context)) }

    LaunchedEffect(settings) {
        saveSettings(context, settings)
    }
    //Music
    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.le_bijouterie_main_menu) }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }

    DisposableEffect(settings.isVolumeOn) {
        if (!settings.isVolumeOn) {
            mediaPlayer.pause()
        } else {
            mediaPlayer.start()
        }
        onDispose {
            mediaPlayer.pause()
        }
    }

    // Define your start menu UI here
    BejeweledTheme(selectedTheme = settings.theme) {gradient ->
        val colorScheme = MaterialTheme.colorScheme
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(gradient),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val offset = Offset(5f, 10f)
        Text(
            text = "Le Bijouterie",
            style = TextStyle(
                color = colorScheme.primary,
                fontSize = 70.sp,
                shadow = Shadow(Color.Black, offset, 8f),
                fontFamily = MaterialTheme.typography.displayLarge.fontFamily,
                fontWeight = MaterialTheme.typography.displayLarge.fontWeight
            ),
            modifier = Modifier
                .padding(16.dp),

        )

        Button(
            onClick = { gameboardDestination()
                mediaPlayer.stop()
            },
            modifier = Modifier.padding(16.dp),
            shape = MaterialTheme.shapes.small,
            elevation = ButtonDefaults.buttonElevation(5.dp, 5.dp, 5.dp)
        ) {
            Text(
                text = "Start Game",
                color = colorScheme.surface,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 26.sp
            )
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = settingsDestination,
            modifier = Modifier.padding(8.dp),
            shape = MaterialTheme.shapes.small,
            elevation = ButtonDefaults.buttonElevation(5.dp, 5.dp, 5.dp)

        ) {
            Text(
                text = "Settings",
                color = colorScheme.surface,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 26.sp
            )
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = scoreboardDestination,
            modifier = Modifier.padding(8.dp),
            shape = MaterialTheme.shapes.small,
            elevation = ButtonDefaults.buttonElevation(5.dp, 5.dp, 5.dp)
        ) {
            Text(
                text = "Scoreboard",
                color = colorScheme.surface,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 26.sp)
             }
        }
    }
}

private fun saveSettings(context: Context, settings: Settings) {
}

@Preview
@Composable
fun StartMenuPreview() {
    StartMenu(
        selectedTheme = ThemeOption.LIGHT,
        scoreboardDestination = {},
        gameboardDestination = {},
        settingsDestination = {}
    )
}