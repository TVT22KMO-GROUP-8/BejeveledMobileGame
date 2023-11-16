package com.example.bejeweled.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface ScoreDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(player: Player)

    @Update
    suspend fun update(player: Player)

    @Delete
    suspend fun delete(player: Player)

    @Query("SELECT * FROM score_table ORDER BY score DESC")
    fun getAllPlayers(): List<Player>

    @Query("SELECT * FROM score_table WHERE name LIKE name")
    fun getPlayerByName(name: String):Player
}