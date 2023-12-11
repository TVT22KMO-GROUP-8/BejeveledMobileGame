package com.example.bejeweled.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Insert
import com.example.bejeweled.R
import com.example.bejeweled.data.ScoreboardDetails
import com.example.bejeweled.ui.navigation.NavigationDestination
import com.example.bejeweled.ui.theme.BejeweledTheme
import com.example.bejeweled.ui.theme.ThemeOption
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext

object SettingsDestination : NavigationDestination {
    override val route = "settings"
    override val titleRes = R.string.settings_title
}

private const val PREFS_NAME = "ThemePreferences"
private const val THEME_KEY = "selectedTheme"
private const val NAME_KEY = "userName"
private const val NOTIFICATION_KEY = "notificationEnabled"
private const val VOLUME_KEY = "volume"

data class Settings(
    val name: String,
    val notificationEnabled: Boolean,
    val volume: Float,
    val theme: ThemeOption
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    sharedPreferences: SharedPreferences,
    selectedTheme: ThemeOption
) {
    val context = LocalContext.current

    var settings by remember { mutableStateOf(loadSettings(context)) }

    LaunchedEffect(settings) {
        saveSettings(context, settings)
    }

    BejeweledTheme(selectedTheme = settings.theme) {
        val colorScheme = MaterialTheme.colorScheme
        Scaffold(modifier = modifier.background(color = colorScheme.primary)) {
            LazyColumn() {
                item {
                    SettingsItem("Name", titleColor = colorScheme.primary) {
                        BasicTextField(
                            value = settings.name,
                            onValueChange = { newName -> settings = settings.copy(name = newName) },
                            textStyle = TextStyle(fontSize = 18.sp, color = colorScheme.primary),
                            modifier = Modifier.background(color = colorScheme.background)
                        )
                    }
                }
                item {
                    SettingsItem("Notifications", titleColor = colorScheme.primary) {
                        Switch(
                            checked = settings.notificationEnabled,
                            onCheckedChange = { notificationEnabled ->
                                settings = settings.copy(notificationEnabled = notificationEnabled)
                            },
                            modifier = Modifier.background(color = colorScheme.background)
                        )
                    }
                }
                item {
                    SettingsItem("Volume", titleColor = colorScheme.primary) {
                        Slider(
                            value = settings.volume,
                            onValueChange = { newVolume ->
                                settings = settings.copy(volume = newVolume)
                            },
                            valueRange = 0f..1f,
                            steps = 100,
                            modifier = Modifier.background(color = colorScheme.background)
                        )
                    }
                }
                item {
                    SettingsItem("Theme Mode", titleColor = colorScheme.primary) {
                        Box(
                            modifier = Modifier.background(color = colorScheme.background)
                        ) {
                            ThemeOptionRadioGroup(
                                selectedTheme = settings.theme,
                                onThemeSelected = { selectedThemeMode ->
                                    settings = settings.copy(theme = selectedThemeMode)
                                },
                                colorScheme = colorScheme
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ThemeOptionRadioGroup(
    selectedTheme: ThemeOption,
    onThemeSelected: (ThemeOption) -> Unit,
    colorScheme: ColorScheme
) {
    Column {
        ThemeOptionRadioButton(
            option = ThemeOption.LIGHT,
            selectedTheme = selectedTheme,
            onThemeSelected = onThemeSelected,
            colorScheme = colorScheme
        )
        ThemeOptionRadioButton(
            option = ThemeOption.DARK,
            selectedTheme = selectedTheme,
            onThemeSelected = onThemeSelected,
            colorScheme = colorScheme
        )
    }
}

@Composable
fun ThemeOptionRadioButton(
    option: ThemeOption,
    selectedTheme: ThemeOption,
    onThemeSelected: (ThemeOption) -> Unit,
    colorScheme: ColorScheme
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onThemeSelected(option) }
    ) {
        RadioButton(
            selected = option == selectedTheme,
            onClick = null,
            colors = RadioButtonDefaults.colors(
                unselectedColor = colorScheme.primary.copy(alpha = 0.6f),
                selectedColor = colorScheme.primary
            ),
            modifier = Modifier
                .size(24.dp)
                .background(color = colorScheme.background)
        )
        Text(
            text = option.name,
            modifier = Modifier.padding(start = 8.dp),
            color = colorScheme.primary
        )
    }
}

fun loadSettings(context: Context): Settings {
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val savedName = prefs.getString(NAME_KEY, "John Doe") ?: "John Doe"
    val savedNotificationEnabled = prefs.getBoolean(NOTIFICATION_KEY, true)
    val savedVolume = prefs.getFloat(VOLUME_KEY, 0.5f)
    val savedThemeName = prefs.getString(THEME_KEY, ThemeOption.LIGHT.name)
    val savedTheme = ThemeOption.valueOf(savedThemeName ?: ThemeOption.LIGHT.name)

    return Settings(savedName, savedNotificationEnabled, savedVolume, savedTheme)
}

private fun saveSettings(context: Context, settings: Settings) {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val editor = prefs.edit()
    editor.putString(NAME_KEY, settings.name)
    editor.putBoolean(NOTIFICATION_KEY, settings.notificationEnabled)
    editor.putFloat(VOLUME_KEY, settings.volume)
    editor.putString(THEME_KEY, settings.theme.name)
    editor.apply()
}

@Composable
fun SettingsItem(title: String, titleColor: Color = Color.Gray, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            color = titleColor,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        content()
    }
}

@Composable
fun SettingsDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onSave: () -> Unit,
    volumeValue: Float,
    onVolumeChange: (Float) -> Unit,
    selectedTheme: ThemeOption,
    onThemeSelected: (ThemeOption) -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text("Settings")
            },
            text = {
                SettingsContent(
                    volumeValue = volumeValue,
                    onVolumeChange = onVolumeChange,
                    selectedTheme = selectedTheme,
                    onThemeSelected = onThemeSelected
                )
            },
            confirmButton = {
                TextButton(
                    onClick = onSave
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                // Optional dismiss button
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(MaterialTheme.colorScheme.background),
        )
    }
}

@Composable
fun SettingsContent(
    volumeValue: Float,
    onVolumeChange: (Float) -> Unit,
    selectedTheme: ThemeOption,
    onThemeSelected: (ThemeOption) -> Unit,
) {
    BejeweledTheme(selectedTheme = selectedTheme) {
        val colorScheme = MaterialTheme.colorScheme
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Volume")
            Slider(
                value = volumeValue,
                onValueChange = {
                    onVolumeChange(it)
                },
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ThemeOptionRadioButton(
                    option = ThemeOption.LIGHT,
                    selectedTheme = selectedTheme,
                    onThemeSelected = onThemeSelected,
                    colorScheme = colorScheme
                )
                ThemeOptionRadioButton(
                    option = ThemeOption.DARK,
                    selectedTheme = selectedTheme,
                    onThemeSelected = onThemeSelected,
                    colorScheme = colorScheme
                )
                Text("Dark mode")
            }
        }
    }
}