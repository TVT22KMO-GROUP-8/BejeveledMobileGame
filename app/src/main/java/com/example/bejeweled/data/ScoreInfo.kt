package com.example.bejeweled.data

import androidx.annotation.StringRes
import com.example.bejeweled.R


data class Player(
    @StringRes val name: Int,
    val score: Int
)

val players = listOf(
    Player(R.string.player_name_1, 100),
    Player(R.string.player_name_2, 150),
    Player(R.string.player_name_3, 10),
    Player(R.string.player_name_4, 10000),
    Player(R.string.player_name_5, 112300),
    Player(R.string.player_name_6, 100),
    Player(R.string.player_name_7, 1124)
)
