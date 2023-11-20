package com.example.bejeweled

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.bejeweled.data.ScoreboardDatabase
import com.example.bejeweled.data.ScoreboardViewModel
import com.example.bejeweled.ui.theme.BejeweledTheme



class MainActivity : ComponentActivity() {

    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            ScoreboardDatabase::class.java,
            "scoreboard.db"
        ).build()
    }
    private val viewModel by viewModels<ScoreboardViewModel> (
        factoryProducer = {
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ScoreboardViewModel(database.dao) as T
            }
        }
    }
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            BejeweledTheme {
                val state by viewModel.state.collectAsState()
                BejeweledApp(state = state, onEvent = viewModel::onEvent)
            }
        }
    }
}



