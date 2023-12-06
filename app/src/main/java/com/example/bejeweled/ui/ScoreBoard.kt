package com.example.bejeweled.ui


import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.provider.DocumentsContract.Root
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bejeweled.R
import com.example.bejeweled.data.ScoreboardInfo

import com.example.bejeweled.data.ScoreboardViewModel
import com.example.bejeweled.ui.navigation.NavigationDestination
import com.google.firebase.Firebase
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember


object ScoreboardDestination : NavigationDestination {
    override val route = "scoreboard"
    override val titleRes = R.string.scoreboard_title
}



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")

@Composable
fun ScoreBoard(

) {
    val scoreboardListValue = remember { mutableStateListOf<ScoreboardInfo>() }
    val database = Firebase.database("https://bejeweledmobiiliprojekti-default-rtdb.europe-west1.firebasedatabase.app/")
    val scoreboardRef = database.getReference("scoreboardDetails")
    scoreboardRef.addValueEventListener (object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val scoreboardList = mutableListOf<ScoreboardInfo>()
            for(i in snapshot.children){
                scoreboardList.add(i.getValue<ScoreboardInfo>()!!)
            }
            scoreboardList.sortByDescending { it.score }
            scoreboardListValue.addAll(scoreboardList)

        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
            Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            // ...
        }
    })

    ScoreboardList(scoreboardList = scoreboardListValue)

}


@Composable
fun ScoreboardList(
    scoreboardList: List<ScoreboardInfo>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(scoreboardList) { scoreboardInfo ->
            ScoreboardCard(scoreboardInfo = scoreboardInfo)
        }
    }
}


@Composable
fun ScoreboardCard(
    scoreboardInfo: ScoreboardInfo,

) {
    Card(
        modifier = Modifier.padding(16.dp) ,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),

    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = scoreboardInfo.name,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = scoreboardInfo.score.toString(),
                    style = MaterialTheme.typography.titleMedium
                )
            }

        }
    }
}
