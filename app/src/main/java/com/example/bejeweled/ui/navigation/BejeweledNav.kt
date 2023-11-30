package com.example.bejeweled.ui.navigation

import android.content.SharedPreferences
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bejeweled.R
import com.example.bejeweled.data.ScoreboardDetails
import com.example.bejeweled.data.ScoreboardUiState
import com.example.bejeweled.data.ScoreboardViewModel
import com.example.bejeweled.ui.AppViewModelProvider
import com.example.bejeweled.ui.StartMenu
import com.example.bejeweled.ui.BejeweledGameBoard
import com.example.bejeweled.ui.GameBoardDestination
import com.example.bejeweled.ui.SettingsScreen
import com.example.bejeweled.ui.ScoreBoard
import com.example.bejeweled.ui.ScoreboardDestination
import com.example.bejeweled.ui.SettingsDestination
import com.example.bejeweled.ui.StartMenuDestination



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BejeweledNavHost(
    navController : NavHostController,
    modifier: Modifier = Modifier,
    sharedPreferences: SharedPreferences
    ) {

    Scaffold(
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = StartMenuDestination.route,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(route = StartMenuDestination.route){
                StartMenu(
                    modifier = Modifier.fillMaxSize(),
                    scoreboardDestination = { navController.navigate(ScoreboardDestination.route) },
                    gameboardDestination = { navController.navigate(GameBoardDestination.route) },
                    settingsDestination = { navController.navigate(SettingsDestination.route) }
                )
            }
            composable(route = GameBoardDestination.route) {
                BejeweledGameBoard(
                    modifier = Modifier.fillMaxSize(),
                    sharedPreferences = sharedPreferences
                )
            }
            composable(route = SettingsDestination.route) {
                SettingsScreen(
                    modifier = Modifier.fillMaxSize(),
                    sharedPreferences = sharedPreferences
                )
            }
            composable(route = ScoreboardDestination.route) {
                ScoreBoard(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}