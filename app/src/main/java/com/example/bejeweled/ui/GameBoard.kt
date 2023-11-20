package com.example.bejeweled.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bejeweled.R
import com.example.bejeweled.ui.navigation.NavigationDestination
import com.example.bejeweled.ui.theme.BejeweledTheme

object GameBoardDestination : NavigationDestination {
    override val route = "game_board"
    override val titleRes = R.string.game_board_title
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BejeweledGameBoard(
    modifier: Modifier = Modifier,

) {
    val gridSize = 8

    // Initialize gemGrid with generateGemGrid(8) using remember
    val gemGrid = remember { mutableStateOf(generateGemGrid(gridSize)) }

    var selectedGemPosition by remember { mutableStateOf<Pair<Int, Int>?>(null) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        for (i in 0 until gridSize) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (j in 0 until gridSize) {
                    GridCell(gemType = gemGrid.value[i][j]) {
                        // Handle gem click
                        if (selectedGemPosition == null) {
                            // First gem click
                            selectedGemPosition = Pair(i, j)
                        } else {
                            // Second gem click, swap the gems
                            val (x1, y1) = selectedGemPosition!!
                            val (x2, y2) = Pair(i, j)
                            val newGemGrid = gemGrid.value.toMutableList() as MutableList<MutableList<GemType>>
                            swapGems(newGemGrid, x1, y1, x2, y2)
                            gemGrid.value = newGemGrid
                            selectedGemPosition = null
                        }
                    }
                }
            }
        }

        Button(
            onClick = {
                // Regenerate the gem grid
                gemGrid.value = generateGemGrid(gridSize)
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Regenerate Grid")
        }

    }

}



fun generateGemGrid(gridSize: Int): List<List<GemType>> {
    val gemGrid = MutableList(gridSize) {
        MutableList(gridSize) {
            GemType.values().random()
        }
    }

    // Check for and prevent three identical gems in a row
    for (i in 0 until gridSize) {
        for (j in 2 until gridSize) {
            if (gemGrid[i][j] == gemGrid[i][j - 1] && gemGrid[i][j] == gemGrid[i][j - 2]) {
                val newGem = GemType.values().filter { it != gemGrid[i][j] }.random()
                gemGrid[i][j] = newGem
            }
        }
    }

    // Check for and prevent three identical gems in a column
    for (j in 0 until gridSize) {
        for (i in 2 until gridSize) {
            if (gemGrid[i][j] == gemGrid[i - 1][j] && gemGrid[i][j] == gemGrid[i - 2][j]) {
                val newGem = GemType.values().filter { it != gemGrid[i][j] }.random()
                gemGrid[i][j] = newGem
            }
        }
    }

    return gemGrid
}

fun swapGems(grid: MutableList<MutableList<GemType>>, x1: Int, y1: Int, x2: Int, y2: Int) {
    val temp = grid[x1][y1]
    grid[x1][y1] = grid[x2][y2]
    grid[x2][y2] = temp
}

@Composable
fun GridCell(
    gemType: GemType,
    onGemClick: () -> Unit
) {
    val gemDrawableRes = gemType.drawableResId

    Image(
        painter = painterResource(id = gemDrawableRes),
        contentDescription = null,
        modifier = Modifier
            .size(50.dp)
            .background(Color.Gray)
            .clickable { onGemClick() } // Handle gem click
    )
}

enum class GemType(val drawableResId: Int) {
    AMBER(R.drawable.amber),
    AMETHYST(R.drawable.amethyst),
    DIAMOND(R.drawable.diamond),
    EMERALD(R.drawable.emerald),
    SAPPHIRE(R.drawable.sapphire),
    TOPAZ(R.drawable.topaz),
    RUBY(R.drawable.ruby),
}

@Preview(showBackground = true)
@Composable
fun BejeweledGameBoardPreview() {
    BejeweledTheme {
        BejeweledGameBoard()
    }
}