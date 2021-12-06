package examples.simulations

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import k5
import kotlin.math.floor
import kotlin.random.Random

/**
 * The Game of Life, also known simply as Life, is a cellular automaton devised by the British mathematician John Horton Conway in 1970.[1]
 * It is a zero-player game, meaning that its evolution is determined by its initial state, requiring no further input.
 * One interacts with the Game of Life by creating an initial configuration and observing how it evolves.
 * It is Turing complete and can simulate a universal constructor or any other Turing machine.
 * - Wiki [https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life]
 *
 * You can read more about game of life and cellular automaton here -
 * https://natureofcode.com/book/chapter-7-cellular-automata/
 */
class GameOfLife(dimensions: Size) {

    // val columns = floor(dimensions.height / 2).toInt()
    // val rows = floor(dimensions.height / 2).toInt()
    val columns = 35
    val rows = 35

    var board: MutableList<MutableList<Float>> = MutableList(columns) { MutableList(rows) { 0f } }
    var nextGen: MutableList<MutableList<Float>> = MutableList(columns) { MutableList(rows) { 0f } }

    init {
        for (i in 0 until columns) {
            for (j in 0 until rows) {
                // Lining the edges with 0s
                if (i == 0 || j == 0 || i == columns - 1 || j == rows - 1) {
                    board[i][j] = 0f
                } else {
                    // Filling the rest randomly
                    board[i][j] = floor(Random.nextDouble(2.0).toFloat())
                }
                nextGen[i][j] = 0f
            }
        }
    }

    fun generate() {
        // Loop through every spot in our 2D array and check spots neighbors
        for (x in 1 until columns - 1) {
            for (y in 1 until rows - 1) {
                // Add up all the states in a 3x3 surrounding grid
                var neighbours = 0f
                for (i in -1..1) {
                    for (j in -1..1) {
                        neighbours += board[x + i][y + j]
                    }
                }
                // subtract the current cell's state since we added it in the above loop
                neighbours -= board[x][y]

                // Rules of life
                /*
                 * Game of life is cellular automaton- https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life
                 * Part of chaos theory
                 */
                if ((board[x][y] == 1f) && (neighbours < 2f)) {
                    // 1. Loneliness
                    nextGen[x][y] = 0f
                } else if (board[x][y] == 1f && neighbours > 3f) {
                    // 2. Overpopulation
                    nextGen[x][y] = 0f
                } else if (board[x][y] == 0f && neighbours == 3f) {
                    // 3. Reproduction
                    nextGen[x][y] = 1f
                } else {
                    // Stasis
                    nextGen[x][y] = board[x][y]
                }
            }
        }

        // Swap with previous generation
        val temp = board
        board = nextGen
        nextGen = temp
    }
}

fun gameOfLife() = k5 {

    val gameOfLife = GameOfLife(dimensFloat)
    val w = 50f

    show { drawScope ->
        gameOfLife.generate()
        for (i in 0 until gameOfLife.columns) {
            for (j in 0 until gameOfLife.rows) {
                println("For [$i, $j] = ${gameOfLife.board[i][j]}")
                if (gameOfLife.board[i][j] == 1f) {
                    // dont fill rect
                    drawScope.drawRect(Color.Black, Offset(i * w, j * w), Size(w - 1, w - 1))
                } else {
                    // fill rect
                    drawScope.drawRect(Color.White, Offset(i * w, j * w), Size(w - 1, w - 1))
                }
            }
        }
    }
}
