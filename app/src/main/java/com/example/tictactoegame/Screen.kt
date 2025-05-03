package com.example.tictactoegame

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

enum class Screen {
    Home, HumanGame, AIGame
}


@Composable
fun AIGameScreen(onBack: () -> Unit) {
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

    LaunchedEffect(currentPlayer, board) {
        if (currentPlayer == 'O' && winner == null && !isDraw) {
            delay(500L)
            val emptyIndices = board.mapIndexedNotNull { index, value ->
                if (value == null) index else null
            }
            if (emptyIndices.isNotEmpty()) {
                val aiMove = emptyIndices.random()
                board = board.toMutableList().also { it[aiMove] = 'O' }
                winner = checkWinner(board)
                isDraw = board.all { it != null } && winner == null
                if (winner == null && !isDraw) {
                    currentPlayer = 'X'
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    listOf(Color(0xFF00E5FF), Color(0xFF8E24AA))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = when {
                    winner != null -> "Winner: $winner"
                    isDraw -> "It's a Draw!"
                    else -> "Turn: $currentPlayer"
                },
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(top = 70.dp)
            )

            // Grid
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // take up remaining vertical space
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                for (row in 0 until 3) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp), // or 80.dp
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {

                        for (col in 0 until 3) {
                            val index = row * 3 + col
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .border(2.dp, Color.Black)
                                    .clickable(
                                        enabled = board[index] == null && currentPlayer == 'X' && winner == null
                                    ) {
                                        board = board.toMutableList().also { it[index] = 'X' }
                                        winner = checkWinner(board)
                                        isDraw = board.all { it != null } && winner == null
                                        if (winner == null && !isDraw) {
                                            currentPlayer = 'O'
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

            Row(
                modifier = Modifier.padding(bottom = 30.dp),
                horizontalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                Button(onClick = {
                    board = List(9) { null }
                    currentPlayer = 'X'
                    winner = null
                    isDraw = false
                }, colors = ButtonDefaults.buttonColors(containerColor = Color.Black)) {
                    Text("Reset")
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
fun AiScreenPreview(){

    AIGameScreen (
        onBack = {}
    )
}
