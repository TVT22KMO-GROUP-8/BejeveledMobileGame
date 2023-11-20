package com.example.bejeweled.data

import androidx.annotation.StringRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bejeweled.R


@Entity(tableName = "score_table")
data class ScoreboardInfo(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val name: String,
    val score: String
)



