package com.example.bejeweled.data

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.bejeweled.ui.scoreboardListValue

import com.google.firebase.Firebase
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import kotlinx.coroutines.flow.StateFlow


class ScoreboardListViewModel: ViewModel(){


    val scoreboardListValue = mutableListOf<ScoreboardInfo>()
    private val database = Firebase.database("https://bejeweledmobiiliprojekti-default-rtdb.europe-west1.firebasedatabase.app/")
    val scoreboardRef = database.getReference("scoreboardDetails")

    init {
        getScoreboardList()
    }
    private fun getScoreboardList() =
    scoreboardRef.addValueEventListener (object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val scoreboardList = mutableListOf<ScoreboardInfo>()
            for(i in snapshot.children){
                scoreboardList.add(i.getValue<ScoreboardInfo>()!!)
            }
            scoreboardList.sortByDescending { it.score }
            scoreboardListValue.addAll(scoreboardList)
            Log.d("TAG", "ScoreboardListValue is: $scoreboardListValue")

        }


        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
            Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            // ...
        }
    })
}
data class ScoreboardListUiState(
    val scoreboardList: List<ScoreboardInfo> = listOf(),

)





