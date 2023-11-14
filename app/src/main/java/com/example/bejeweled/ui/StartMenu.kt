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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bejeweled.Screen
import com.example.bejeweled.title.londrinaSketch
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun StartMenu(
   modifier: Modifier = Modifier,
   navController: NavController
) {
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
                fontFamily = londrinaSketch,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(16.dp)
        )

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
