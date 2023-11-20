package com.example.bejeweled

import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.bejeweled.data.ScoreboardEvent
import com.example.bejeweled.data.ScoreboardState
import com.example.bejeweled.ui.StartMenu
import com.example.bejeweled.ui.BejeweledGameBoard
import com.example.bejeweled.ui.SettingsScreen
import com.example.bejeweled.ui.ScoreBoard
import javax.sql.DataSource

enum class Screen(){
    Start,
    GameBoard,
    Settings,
    ScoreBoard,
    GameOver
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BejeweledAppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
){
    TopAppBar(
        title = { Text(stringResource(id = R.string.app_name)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BejeweledApp(
    navController : NavHostController = rememberNavController(),
    state: ScoreboardState,
    onEvent: (ScoreboardEvent) -> Unit,
) {

    Scaffold(
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Start.name,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(route = Screen.Start.name){
                StartMenu(
                    modifier = Modifier.fillMaxSize(),
                    navController = navController
                )
            }
            composable(route = Screen.GameBoard.name) {
                BejeweledGameBoard(
                    modifier = Modifier.fillMaxSize()
                )
            }
            composable(route = Screen.Settings.name) {
                SettingsScreen(
                    modifier = Modifier.fillMaxSize()
                )
            }
            composable(route = Screen.ScoreBoard.name) {
                ScoreBoard(
                    modifier = Modifier.fillMaxSize(),
                    state = ScoreboardState(),
                    onEvent = {}
                )
            }
        }
    }
}