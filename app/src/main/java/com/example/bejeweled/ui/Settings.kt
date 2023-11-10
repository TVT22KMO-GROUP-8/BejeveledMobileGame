package com.example.bejeweled.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf("John Doe") }
    var notificationEnabled by remember { mutableStateOf(true) }
    var themeMode by remember { mutableIntStateOf(0) }

    val themeModes = listOf("Light", "Dark", "System Default")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Settings") }
            )
        }
    ) {
        LazyColumn {
            item {
                SettingsItem("Name") {
                    BasicTextField(
                        value = name,
                        onValueChange = { newName -> name = newName },
                        textStyle = TextStyle(fontSize = 18.sp)
                    )
                }
            }
            item {
                SettingsItem("Notifications") {
                    Switch(
                        checked = notificationEnabled,
                        onCheckedChange = { notificationEnabled = it }
                    )
                }
            }
            item {
                SettingsItem("Theme Mode") {
                    Text(
                        text = themeModes[themeMode],
                        fontSize = 18.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                themeMode = (themeMode + 1) % themeModes.size
                            }
                    )
                    }
                }
            }
        }
    }



@Composable
fun SettingsItem(title: String, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        content()
    }
}



