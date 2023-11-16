package com.example.bejeweled.data

import androidx.annotation.StringRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bejeweled.R


@Entity(tableName = "score_table")
data class ScoreboardInfo(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    @StringRes val name: Int,
    val score: Int
){

}

val players = listOf(
    ScoreboardInfo(name = R.string.player_name_1, score = 1000),
    ScoreboardInfo(name = R.string.player_name_2, score = 1000),
    ScoreboardInfo(name = R.string.player_name_3, score = 1000),
    ScoreboardInfo(name = R.string.player_name_4, score = 1000),
    ScoreboardInfo(name = R.string.player_name_5, score = 1000),
    ScoreboardInfo(name = R.string.player_name_6, score = 1000),
    ScoreboardInfo(name = R.string.player_name_7, score = 1000)
)
