package com.example.tictactoegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.tictactoegame.ui.theme.TicTacToeGameTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var currentScreen by remember { mutableStateOf(Screen.Home) }

            when (currentScreen) {
                Screen.Home -> HomeScreen(
                    onPlayHuman = { currentScreen = Screen.HumanGame },
                    onPlayAI = { currentScreen = Screen.AIGame }
                )
                Screen.HumanGame -> TicTacToeGame(onBack = { currentScreen = Screen.Home })
                Screen.AIGame -> AIGameScreen(onBack = { currentScreen = Screen.Home })
            }

        }
    }
}

@Composable
fun HomeScreen(onPlayHuman: () -> Unit, onPlayAI: () -> Unit) {
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
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Tic Tac Toe", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 30.dp))
            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = onPlayHuman, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Color.Black)) {
                Text("Play with Human")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onPlayAI, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Color.Black)) {
                Text("Play with Computer")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TicTacPreview() {
    TicTacToeGameTheme {
        HomeScreen(
            onPlayHuman = {},
            onPlayAI = {}
        )
    }
}
