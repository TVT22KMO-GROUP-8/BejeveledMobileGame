package com.example.bejeweled

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController

import com.example.bejeweled.ui.theme.BejeweledTheme



class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BejeweledTheme {
                Surface (
                    modifier = Modifier.fillMaxSize()
                ){
                    BejeweledApp()
                }

            }
        }
    }
}



