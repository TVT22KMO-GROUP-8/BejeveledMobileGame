package com.example.bejeweled.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bejeweled.Screen


@Composable
fun StartMenu(
   modifier: Modifier = Modifier,
   navController: NavController
) {
    // Define your start menu UI here
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Le Bijouterie", style = TextStyle(fontSize = 24.sp))
        Button(
            onClick = {navController.navigate(Screen.GameBoard.name) },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Aloita peli")
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = { navController.navigate(Screen.Settings.name) },
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Asetukset")
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = { navController.navigate(Screen.ScoreBoard.name) },
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Tulostaulukko")
        }

    }
}