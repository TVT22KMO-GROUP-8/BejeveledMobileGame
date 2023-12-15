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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
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
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.ColorFilter

object SettingsDestination : NavigationDestination {
    override val route = "settings"
    override val titleRes = R.string.settings_title
}

private const val PREFS_NAME = "ThemePreferences"
private const val THEME_KEY = "selectedTheme"
private const val NAME_KEY = "userName"
private const val VOLUME_KEY = "volume"
private const val VOLUME_ON_KEY = "volumeOn"

data class Settings(
    val name: String,
    val volume: Float,
    val theme: ThemeOption,
    val isVolumeOn: Boolean
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    sharedPreferences: SharedPreferences,
    selectedTheme: ThemeOption,
    navController: androidx.navigation.NavController = rememberNavController()
) {
    val context = LocalContext.current

    var settings by remember { mutableStateOf(loadSettings(context)) }

    LaunchedEffect(settings) {
        saveSettings(context, settings)
    }

    BejeweledTheme(selectedTheme = settings.theme) { gradient ->
        val colorScheme = MaterialTheme.colorScheme
        Scaffold(
            modifier = modifier
                .fillMaxSize()
                .background(gradient),
            topBar = {
                TopAppBar(
                    title = { Text(text = "Settings") },
                    navigationIcon = {
                        IconButton(
                            onClick = { navController.navigate("start_menu") },
                            modifier = Modifier.padding(16.dp),
                        ) {
                            Icon(Icons.Rounded.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .background(gradient)
                    .padding(innerPadding)
            ) {
                item {
                    SettingsItem("Name", titleColor = colorScheme.primary) {
                        BasicTextField(
                            value = settings.name,
                            onValueChange = { newName -> settings = settings.copy(name = newName) },
                            textStyle = TextStyle(fontSize = 18.sp, color = colorScheme.primary),
                            modifier = Modifier.background(color = Color.Transparent),
                            singleLine = true,
                        )
                    }
                }
                item {
                    SettingsItem("Volume", titleColor = colorScheme.primary) {
                        Switch(
                            checked = settings.isVolumeOn,
                            onCheckedChange = {
                                settings = settings.copy(isVolumeOn = it)
                                saveSettings(context, settings)
                            },
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
                item {
                    SettingsItem("Theme Mode", titleColor = colorScheme.primary) {
                        Box(
                            modifier = Modifier.background(color = Color.Transparent)
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
fun CustomSoundToggleIcon(
    isSoundOn: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val contentDescription = if (isSoundOn) "Sound On" else "Mute"
    val iconId = when {
        isSoundOn -> R.drawable.baseline_music_note_on
        else -> R.drawable.baseline_volume_off
    }

    Image(
        painter = painterResource(id = iconId),
        contentDescription = contentDescription,
        modifier = modifier
            .size(24.dp)
            .background(color = Color.Transparent)
            .clickable { onToggle() },
        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
    )
}

@Composable
fun CustomMusicToggleIcon(
    isMusicOn: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val contentDescription = if (isMusicOn) "Music On" else "Mute"
    val iconId = when {
        isMusicOn -> R.drawable.baseline_volume_on
        else -> R.drawable.baseline_volume_off
    }

    Image(
        painter = painterResource(id = iconId),
        contentDescription = contentDescription,
        modifier = modifier
            .size(24.dp)
            .background(color = Color.Transparent)
            .clickable { onToggle() },
        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
    )
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
                .background(color = Color.Transparent)
        )
        Text(
            text = option.name,
            modifier = Modifier.padding(start = 8.dp),
            color = colorScheme.primary
        )
    }
}

private const val VOLUME_ON_DEFAULT = true
fun loadSettings(context: Context): Settings {
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val savedName = prefs.getString(NAME_KEY, "John Doe") ?: "John Doe"
    val savedVolume = prefs.getFloat(VOLUME_KEY, 0.5f)
    val savedThemeName = prefs.getString(THEME_KEY, ThemeOption.LIGHT.name)
    val savedTheme = ThemeOption.valueOf(savedThemeName ?: ThemeOption.LIGHT.name)
    val savedVolumeOn = prefs.getBoolean(VOLUME_ON_KEY, VOLUME_ON_DEFAULT)

    return Settings(savedName, savedVolume, savedTheme, savedVolumeOn)
}

private fun saveSettings(context: Context, settings: Settings) {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val editor = prefs.edit()
    editor.putString(NAME_KEY, settings.name)
    editor.putFloat(VOLUME_KEY, settings.volume)
    editor.putString(THEME_KEY, settings.theme.name)
    editor.putBoolean(VOLUME_ON_KEY, settings.isVolumeOn)
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
