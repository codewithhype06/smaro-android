package com.example.smaro.ui.home

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.airbnb.lottie.compose.*
import com.example.smaro.R
import kotlin.math.max
import kotlin.math.min
import kotlinx.coroutines.launch

/* ---------- COLORS ---------- */
private val CardDark = Color(0xFF181818)
private val GlassCard = Color.White.copy(alpha = 0.08f)
private val AccentCyan = Color(0xFF22D3EE)
private val XColor = Color(0xFF7DD3FC)
private val OColor = Color(0xFFFCA5A5)

/* ---------- ENUMS ---------- */
private enum class GameMode { PVP, PVC }
private enum class Difficulty { EASY, MEDIUM, HARD }

@Composable
fun TicTacToeScreen(onBack: () -> Unit) {
    BackHandler { onBack() }

    /* ---------- STATE ---------- */
    var board by remember { mutableStateOf(List(9) { "" }) }
    var isXTurn by remember { mutableStateOf(true) }
    var result by remember { mutableStateOf<String?>(null) }
    var showResult by remember { mutableStateOf(false) }
    var winningLine by remember { mutableStateOf<List<Int>>(emptyList()) }

    var mode by remember { mutableStateOf(GameMode.PVP) }
    var difficulty by remember { mutableStateOf(Difficulty.MEDIUM) }

    var xScore by remember { mutableStateOf(0) }
    var oScore by remember { mutableStateOf(0) }

    /* ---------- PANDA + LOGO ANIMATION ---------- */
    val imageOffsetX = remember { Animatable(0f) }
    val lottieOffsetX = remember { Animatable(0f) }
    val lottieAlpha = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    val pandaComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.panda)
    )

    LaunchedEffect(Unit) {
        lottieAlpha.animateTo(1f, tween(300))
        scope.launch { imageOffsetX.animateTo(-60f, tween(900)) }
        scope.launch { lottieOffsetX.animateTo(60f, tween(900)) }
    }

    /* ---------- RESET ---------- */
    fun resetGame(resetScore: Boolean = false) {
        board = List(9) { "" }
        isXTurn = true
        result = null
        showResult = false
        winningLine = emptyList()
        if (resetScore) {
            xScore = 0
            oScore = 0
        }
    }

    /* ---------- WIN CHECK ---------- */
    fun winner(b: List<String>): String? {
        val wins = listOf(
            listOf(0,1,2), listOf(3,4,5), listOf(6,7,8),
            listOf(0,3,6), listOf(1,4,7), listOf(2,5,8),
            listOf(0,4,8), listOf(2,4,6)
        )
        for (w in wins) {
            val (a,b1,c) = w
            if (b[a].isNotEmpty() && b[a] == b[b1] && b[a] == b[c]) {
                winningLine = w
                return b[a]
            }
        }
        return null
    }

    /* ---------- HARD AI ---------- */
    fun minimax(b: List<String>, isMax: Boolean): Int {
        val w = winner(b)
        if (w == "O") return 10
        if (w == "X") return -10
        if (b.all { it.isNotEmpty() }) return 0

        return if (isMax) {
            var best = -1000
            for (i in b.indices) if (b[i].isEmpty()) {
                val temp = b.toMutableList(); temp[i] = "O"
                best = max(best, minimax(temp, false))
            }
            best
        } else {
            var best = 1000
            for (i in b.indices) if (b[i].isEmpty()) {
                val temp = b.toMutableList(); temp[i] = "X"
                best = min(best, minimax(temp, true))
            }
            best
        }
    }

    fun computerMove() {
        when (difficulty) {
            Difficulty.EASY, Difficulty.MEDIUM -> {
                val move = board.indexOfFirst { it.isEmpty() }
                board = board.toMutableList().also { it[move] = "O" }
            }
            Difficulty.HARD -> {
                var bestScore = -1000
                var bestMove = 0
                for (i in board.indices) if (board[i].isEmpty()) {
                    val temp = board.toMutableList(); temp[i] = "O"
                    val score = minimax(temp, false)
                    if (score > bestScore) {
                        bestScore = score
                        bestMove = i
                    }
                }
                board = board.toMutableList().also { it[bestMove] = "O" }
            }
        }

        val w = winner(board)
        when {
            w != null -> { result = "$w Wins"; oScore++; showResult = true }
            board.all { it.isNotEmpty() } -> { result = "Draw"; showResult = true }
            else -> isXTurn = true
        }
    }

    /* ---------- UI ---------- */
    Box(Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(R.drawable.app_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            /* HEADER */
            Box(
                modifier = Modifier.fillMaxWidth().height(150.dp),
                contentAlignment = Alignment.Center
            ) {
                pandaComposition?.let {
                    LottieAnimation(
                        composition = it,
                        iterations = LottieConstants.IterateForever,
                        modifier = Modifier
                            .size(160.dp)
                            .offset { IntOffset(lottieOffsetX.value.dp.roundToPx(), 0) }
                            .alpha(lottieAlpha.value)
                    )
                }

                Image(
                    painter = painterResource(R.drawable.tictactoelogo),
                    contentDescription = null,
                    modifier = Modifier
                        .size(140.dp)
                        .offset { IntOffset(imageOffsetX.value.dp.roundToPx(), 0) }
                )
            }

            Spacer(Modifier.height(8.dp))

            /* SCORE CARD */
            Card(colors = CardDefaults.cardColors(CardDark)) {
                Column(Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Score Card", color = AccentCyan, fontWeight = FontWeight.Bold)
                    Text("X : $xScore     O : $oScore", color = Color.White)
                }
            }

            Spacer(Modifier.height(8.dp))

            /* MODE + DIFFICULTY CARD */
            Card(colors = CardDefaults.cardColors(GlassCard)) {
                Column(Modifier.padding(12.dp)) {

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        ModeButton("PVP", mode == GameMode.PVP) {
                            mode = GameMode.PVP
                            resetGame(true)
                        }
                        ModeButton("PVC", mode == GameMode.PVC) {
                            mode = GameMode.PVC
                            difficulty = Difficulty.MEDIUM
                            resetGame(true)
                        }
                    }

                    if (mode == GameMode.PVC) {
                        Spacer(Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            DifficultyButton("Easy", difficulty == Difficulty.EASY) {
                                difficulty = Difficulty.EASY; resetGame(true)
                            }
                            DifficultyButton("Medium", difficulty == Difficulty.MEDIUM) {
                                difficulty = Difficulty.MEDIUM; resetGame(true)
                            }
                            DifficultyButton("Hard", difficulty == Difficulty.HARD) {
                                difficulty = Difficulty.HARD; resetGame(true)
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            /* TURN TEXT */
            Text(
                when {
                    result != null -> result!!
                    isXTurn -> "Player X Turn"
                    mode == GameMode.PVC -> "Computer Turn"
                    else -> "Player O Turn"
                },
                color = Color.White
            )

            Spacer(Modifier.height(8.dp))

            /* BOARD */
            Card(colors = CardDefaults.cardColors(CardDark), shape = RoundedCornerShape(20.dp)) {
                Column(Modifier.padding(12.dp)) {
                    repeat(3) { r ->
                        Row {
                            repeat(3) { c ->
                                val i = r * 3 + c
                                Box(
                                    modifier = Modifier
                                        .size(90.dp)
                                        .padding(6.dp)
                                        .clip(RoundedCornerShape(14.dp))
                                        .background(Color.White.copy(0.2f))
                                        .border(
                                            if (winningLine.contains(i)) 2.dp else 0.dp,
                                            AccentCyan,
                                            RoundedCornerShape(14.dp)
                                        )
                                        .pointerInput(Unit) {
                                            detectTapGestures {
                                                if (board[i].isNotEmpty() || showResult) return@detectTapGestures
                                                if (mode == GameMode.PVC && !isXTurn) return@detectTapGestures

                                                board = board.toMutableList().also {
                                                    it[i] = if (isXTurn) "X" else "O"
                                                }

                                                val w = winner(board)
                                                when {
                                                    w != null -> {
                                                        result = "$w Wins"
                                                        if (w == "X") xScore++ else oScore++
                                                        showResult = true
                                                    }
                                                    board.all { it.isNotEmpty() } -> {
                                                        result = "Draw"
                                                        showResult = true
                                                    }
                                                    else -> {
                                                        isXTurn = !isXTurn
                                                        if (mode == GameMode.PVC && !isXTurn) computerMove()
                                                    }
                                                }
                                            }
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        board[i],
                                        fontSize = 34.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (board[i] == "X") XColor else OColor
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(14.dp))
            Button(onClick = onBack) { Text("Back to Home") }
        }

        /* 🎉 CELEBRATION – FRONT */
        if (showResult && result != "Draw") {
            val celeb by rememberLottieComposition(
                LottieCompositionSpec.RawRes(R.raw.celebration)
            )
            LottieAnimation(
                composition = celeb,
                iterations = 1,
                modifier = Modifier.fillMaxSize().zIndex(2f)
            )
        }

        /* RESULT CARD */
        if (showResult) {
            Box(
                modifier = Modifier.fillMaxSize()
                    .background(Color.Black.copy(0.6f))
                    .zIndex(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.clip(RoundedCornerShape(20.dp))
                        .background(Color.White)
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val anim = if (result == "Draw") R.raw.draw else R.raw.tictactoe
                    val comp by rememberLottieComposition(
                        LottieCompositionSpec.RawRes(anim)
                    )
                    LottieAnimation(comp, iterations = 1, modifier = Modifier.size(140.dp))

                    Text(result ?: "", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(10.dp))
                    Button(onClick = { resetGame() }) { Text("Play Again") }
                }
            }
        }
    }
}

/* ---------- BUTTONS ---------- */
@Composable
private fun ModeButton(text: String, selected: Boolean, onClick: () -> Unit) {
    Button(onClick, colors = ButtonDefaults.buttonColors(
        containerColor = if (selected) AccentCyan else Color.DarkGray
    )) { Text(text) }
}

@Composable
private fun DifficultyButton(text: String, selected: Boolean, onClick: () -> Unit) {
    OutlinedButton(onClick, colors = ButtonDefaults.outlinedButtonColors(
        containerColor = if (selected) AccentCyan else Color.Transparent,
        contentColor = Color.White
    )) { Text(text) }
}
