package com.example.tictactoegame

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TicTacToeGame(onBack: () -> Unit) {
    var board by remember { mutableStateOf(List(9) { null as Char? }) }
    var currentPlayer by remember { mutableStateOf('X') }
    var winner by remember { mutableStateOf<Char?>(null) }
    var isDraw by remember { mutableStateOf(false) }

    fun checkWinner(board: List<Char?>): Char? {
        val lines = listOf(
            listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8),
            listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8),
            listOf(0, 4, 8), listOf(2, 4, 6)
        )
        for (line in lines) {
            val (a, b, c) = line
            if (board[a] != null && board[a] == board[b] && board[a] == board[c]) {
                return board[a]
            }
        }
        return null
    }

    fun resetGame() {
        board = List(9) { null }
        currentPlayer = 'X'
        winner = null
        isDraw = false
    }

    Box(
        modifier = Modifier.fillMaxSize().background(
            Brush.linearGradient(
                listOf(Color(0xFF00E5FF), Color(0xFF8E24AA))
            )
        ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = when {
                    winner != null -> "Winner: $winner"
                    isDraw -> "It's a Draw!"
                    else -> "Turn: $currentPlayer"
                },
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 40.dp)
            )

            // Grid
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (row in 0 until 3) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        for (col in 0 until 3) {
                            val index = row * 3 + col
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(100.dp)
                                    .border(2.dp, Color.Black)
                                    .clickable(enabled = board[index] == null && winner == null) {
                                        board = board.toMutableList().also { it[index] = currentPlayer }
                                        winner = checkWinner(board)
                                        isDraw = board.all { it != null } && winner == null
                                        if (winner == null && !isDraw) {
                                            currentPlayer = if (currentPlayer == 'X') 'O' else 'X'
                                        }
                                    }
                            ) {
                                Text(
                                    text = board[index]?.toString() ?: "",
                                    style = MaterialTheme.typography.headlineLarge
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(34.dp))

            Row(
                
                modifier = Modifier.padding(top = 35.dp),
                horizontalArrangement = Arrangement.spacedBy(35.dp)

            ) {

                Button(onClick = { resetGame() }, colors = ButtonDefaults.buttonColors(containerColor = Color.Black)) {
                    Text("Reset Game")
                }

                Button(onClick = onBack, colors = ButtonDefaults.buttonColors(containerColor = Color.Black)) {
                    Text("Back")
                }
            }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun TictactoePreview2(){
    TicTacToeGame (
        onBack = {}
    )
}