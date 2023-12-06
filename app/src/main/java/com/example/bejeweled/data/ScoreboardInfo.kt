package com.example.bejeweled.data

import androidx.annotation.StringRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bejeweled.R



data class ScoreboardInfo(
    @PrimaryKey
    val name: String = "",
    val score: Int = 0
)



