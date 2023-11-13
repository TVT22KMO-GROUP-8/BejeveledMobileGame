package com.example.bejeweleddemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
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
import com.example.bejeweleddemo.ui.theme.BejeweledDemoTheme
import kotlin.math.abs

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BejeweledDemoTheme {
                // Bejeweled game
                BejeweledGameBoard()
            }
        }
    }
}

@Composable
fun BejeweledGameBoard() {
    val gridSize = 8
    var gemGrid by remember { mutableStateOf(generateGemGrid(gridSize)) }
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
                    GridCell(gemType = gemGrid[i][j]) {
                        // Handle gem click
                        if (selectedGemPosition == null) {
                            // First gem click
                            selectedGemPosition = Pair(i, j)
                        } else {
                            // Second gem click, swap the gems and process the board
                            val (x1, y1) = selectedGemPosition!!
                            val (x2, y2) = Pair(i, j)

                            val isAdjacent = (x1 == x2 && abs(y1 - y2) == 1) || (y1 == y2 && abs(x1 - x2) == 1)

                            if (isAdjacent) {
                                val newGemGrid = gemGrid.map { it.toMutableList() }.toMutableList()
                                swapGems(newGemGrid, x1, y1, x2, y2)

                                // Process the game board for matches and updates
                                if (processGameBoard(newGemGrid)) {
                                    gemGrid = newGemGrid // Update gemGrid only if there were changes
                                }
                            }
                            selectedGemPosition = null
                        }
                    }
                }
            }
        }

        Button(
            onClick = {
                // Regenerate the gem grid
                gemGrid = generateGemGrid(gridSize) // Update gemGrid
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
            GemType.values().filter { it != GemType.EMPTY }.random() // Exclude EMPTY in initial assignment
        }
    }

    // Check for and prevent three identical gems in a row
    for (i in 0 until gridSize) {
        for (j in 2 until gridSize) {
            while (gemGrid[i][j] == gemGrid[i][j - 1] && gemGrid[i][j] == gemGrid[i][j - 2]) {
                gemGrid[i][j] = GemType.values().filter { it != GemType.EMPTY }.random()
            }
        }
    }

    // Check for and prevent three identical gems in a column
    for (j in 0 until gridSize) {
        for (i in 2 until gridSize) {
            while (gemGrid[i][j] == gemGrid[i - 1][j] && gemGrid[i][j] == gemGrid[i - 2][j]) {
                gemGrid[i][j] = GemType.values().filter { it != GemType.EMPTY }.random()
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
            .size(48.dp)
            .clickable { onGemClick() } // Handle gem click
    )
}

fun findMatches(grid: List<List<GemType>>): List<Pair<Int, Int>> {
    val matches = mutableListOf<Pair<Int, Int>>()

    for (i in grid.indices) {
        for (j in 0 until grid[i].size - 2) {
            if (grid[i][j] != GemType.EMPTY &&
                grid[i][j] == grid[i][j + 1] && grid[i][j] == grid[i][j + 2]) {
                matches.add(Pair(i, j))
                matches.add(Pair(i, j + 1))
                matches.add(Pair(i, j + 2))
            }
        }
    }

    for (j in grid[0].indices) {
        for (i in 0 until grid.size - 2) {
            if (grid[i][j] != GemType.EMPTY &&
                grid[i][j] == grid[i + 1][j] && grid[i][j] == grid[i + 2][j]) {
                matches.add(Pair(i, j))
                matches.add(Pair(i + 1, j))
                matches.add(Pair(i + 2, j))
            }
        }
    }

    return matches.distinct()
}

fun dropGems(grid: MutableList<MutableList<GemType>>, columnsToDrop: List<Int>) {
    var changesMade = false

    for (col in columnsToDrop) {
        // Drop existing gems down and check if changes were made
        for (row in grid.size - 1 downTo 0) {
            if (grid[row][col] == GemType.EMPTY) {
                var newRow = row - 1
                while (newRow >= 0 && grid[newRow][col] == GemType.EMPTY) {
                    newRow--
                }
                if (newRow >= 0) {
                    grid[row][col] = grid[newRow][col]
                    grid[newRow][col] = GemType.EMPTY
                    changesMade = true
                }
            }
        }

        // Fill the top rows with new random gems
        for (row in 0 until grid.size) {
            if (grid[row][col] == GemType.EMPTY) {
                grid[row][col] = GemType.values().filter { it != GemType.EMPTY }.random()
                changesMade = true
            }
        }
    }

    // Call processGameBoard if changes were made
    if (changesMade) {
        processGameBoard(grid)
    }
}

fun removeMatches(grid: MutableList<MutableList<GemType>>, matches: List<Pair<Int, Int>>): List<Int> {
    val columnsToDrop = mutableListOf<Int>()

    for ((x, y) in matches) {
        grid[x][y] = GemType.EMPTY
        if (y !in columnsToDrop) {
            columnsToDrop.add(y)
        }
    }

    return columnsToDrop

}

fun processGameBoard(grid: MutableList<MutableList<GemType>>): Boolean {
    val matches = findMatches(grid)
    if (matches.isNotEmpty()) {
        val columnsToDrop = removeMatches(grid, matches)
        dropGems(grid, columnsToDrop)
        return true
    }
    return false
}

enum class GemType(val drawableResId: Int) {
    AMBER(R.drawable._pentagram),
    AMETHYST(R.drawable._x),
    DIAMOND(R.drawable._circle),
    EMERALD(R.drawable._tiimalasi),
    RUBY(R.drawable._square),
    SAPPHIRE(R.drawable._kolmio),
    TOPAZ(R.drawable._ruutu),
    EMPTY(R.drawable.empty)
}

@Preview(showBackground = true)
@Composable
fun BejeweledGameBoardPreview() {
    BejeweledDemoTheme {
        BejeweledGameBoard()
    }
}