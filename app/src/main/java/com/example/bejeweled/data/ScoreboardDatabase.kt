package com.example.bejeweled.data

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ScoreboardInfo::class],
    version = 1
)
abstract class ScoreboardDatabase: RoomDatabase() {
    abstract val dao: ScoreboardDao

}