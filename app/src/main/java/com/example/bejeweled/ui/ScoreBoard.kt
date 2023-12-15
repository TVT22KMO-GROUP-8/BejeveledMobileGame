package com.example.bejeweled.ui


import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Button
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bejeweled.R
import com.example.bejeweled.data.ScoreboardInfo
import com.example.bejeweled.ui.navigation.NavigationDestination
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import com.example.bejeweled.ui.theme.BejeweledTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.IconButton
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.bejeweled.ui.theme.ThemeOption
import com.example.bejeweled.ui.theme.ThemeOption.*
import com.google.firebase.database.ChildEventListener


object ScoreboardDestination : NavigationDestination {
    override val route = "scoreboard"
    override val titleRes = R.string.scoreboard_title
}



@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")

@Composable
fun ScoreBoard(
    selectedTheme: ThemeOption,
    modifier: Modifier = Modifier,
    navController: androidx.navigation.NavController = rememberNavController()


) {

    val scoreboardListValue = remember { mutableStateListOf<ScoreboardInfo>() }
    val database = Firebase.database("https://bejeweledmobiiliprojekti-default-rtdb.europe-west1.firebasedatabase.app/")
    val scoreboardRef = database.getReference("scoreboardDetails")
    val query = scoreboardRef.orderByChild("score").limitToLast(25)

    query.addValueEventListener (object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val scoreboardList = mutableListOf<ScoreboardInfo>()
            for(snapshot in snapshot.children){
                scoreboardList.add(snapshot.getValue<ScoreboardInfo>()!!)
            }
            scoreboardList.reverse()
            Log.d("Scoreboard", "Scoreboard list: $scoreboardList")
            scoreboardListValue.clear()
            scoreboardListValue.addAll(scoreboardList)

        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
            Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            // ...
        }
    })
    Scaffold (
        topBar = {
            TopAppBar(title = { Text(text = "Scoreboard") },
                navigationIcon = {
                    IconButton(
                        onClick= { navController.navigate("start_menu")}  ,
                        modifier = Modifier.padding(16.dp),
                    ){
                        Icon(Icons.Rounded.ArrowBack, contentDescription = "Back" )
                    }
                })

        }
    ){ innerPadding ->
        ScoreboardList(
            scoreboardList = scoreboardListValue,
            selectedTheme = LIGHT,
            modifier = Modifier.padding(innerPadding)
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScoreboardList(
    scoreboardList: List<ScoreboardInfo>,
    selectedTheme: ThemeOption,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    var settings by remember { mutableStateOf(loadSettings(context)) }

    LaunchedEffect(settings) {
        saveSettings(context, settings)
    }

    BejeweledTheme(selectedTheme = settings.theme) {gradient ->
        val colorScheme = MaterialTheme.colorScheme

            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .background(gradient)

            ) {
                items(scoreboardList) { scoreboardInfo ->
                    ScoreboardCard(scoreboardInfo = scoreboardInfo)
                }
            }
        }
        
    }


@Composable
fun ScoreboardCard(
    scoreboardInfo: ScoreboardInfo,

) {
    val colorScheme = MaterialTheme.colorScheme
    ElevatedCard(
        modifier = Modifier.padding(16.dp) ,
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = MaterialTheme.shapes.medium,


    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),

        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = scoreboardInfo.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = colorScheme.primary,
                    fontSize = 30.sp
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "Score : ${scoreboardInfo.score}",
                    style = MaterialTheme.typography.titleLarge,
                    color = colorScheme.primary
                )
            }

        }
    }
}

private fun saveSettings(context: Context, settings: Settings) {
}

@Preview
@Composable
fun ScoreboardCardPreview() {
    ScoreboardCard(
        scoreboardInfo = ScoreboardInfo(
            name = "Test",
            score = 100
        )
    )
}
