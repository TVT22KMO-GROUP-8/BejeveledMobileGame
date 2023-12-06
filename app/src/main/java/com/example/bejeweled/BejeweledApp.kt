package com.example.bejeweled

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bejeweled.data.ScoreboardViewModel
import com.example.bejeweled.ui.AppViewModelProvider
import com.example.bejeweled.ui.navigation.BejeweledNavHost
import com.google.firebase.database.ktx.database

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BejeweledApp(
    navController: NavHostController = rememberNavController(),
    sharedPreferences: SharedPreferences
){
    BejeweledNavHost(
        navController = navController,
        sharedPreferences = sharedPreferences
    )

}


