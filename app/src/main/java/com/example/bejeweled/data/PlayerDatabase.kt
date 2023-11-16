package com.example.bejeweled.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database (entities = [ScoreboardInfo::class], version = 1, exportSchema = false)
abstract class ScoreboardDatabase :RoomDatabase(){
    abstract fun scoreboardDao(): ScoreboardDao

        companion object{
        @Volatile
        private var INSTANCE: ScoreboardDatabase? = null
            fun getDatabase(context: Context): ScoreboardDatabase{
                return INSTANCE ?: synchronized(this){
                    Room.databaseBuilder(context, ScoreboardDatabase::class.java, "scoreboard_database")
                        .fallbackToDestructiveMigration()
                        .build().also {
                            INSTANCE = it
                        }
                }
            }

        }
    }
