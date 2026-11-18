package com.finalnight.game.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finalnight.game.game.GamePhase
import com.finalnight.game.game.GameState

@Composable
fun OfficeScreen(
    state: GameState,
    onToggleDoor: () -> Unit,
    onToggleCamera: () -> Unit,
    onRestart: (() -> Unit)? = null,
    onMenu: (() -> Unit)? = null
) {
    val powerProgress by animateFloatAsState(
        targetValue = state.power / 100f,
        label = "power_progress"
    )
    val isOver = state.phase == GamePhase.WON || state.phase == GamePhase.LOST

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF090B10))
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("SECURITY OFFICE", color = Color(0xFFE53945), fontWeight = FontWeight.Black, letterSpacing = 2.sp)
            Text(state.nightTime, color = Color.White, fontSize = 25.sp, fontWeight = FontWeight.Bold)
        }

        Surface(
            modifier = Modifier.fillMaxWidth().weight(1f),
            color = Color(0xFF151922),
            shape = RoundedCornerShape(18.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    if (state.cameraActive) "CAMERA FEED // CAM 01" else "OFFICE // MAIN HALL",
                    color = Color(0xFF9CA3AF),
                    fontSize = 11.sp,
                    letterSpacing = 2.sp
                )
                Spacer(Modifier.height(28.dp))
                Text(
                    if (state.cameraActive) "SIGNAL ACTIVE" else " ",
                    color = Color(0xFFE53945),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    if (state.cameraActive) "No movement detected." else " ",
                    color = Color.LightGray,
                    fontSize = 15.sp
                )
                Spacer(Modifier.height(24.dp))
                AnimatedVisibility(visible = state.doorClosed) {
                    Text(
                        "SECURITY DOOR LOCKED",
                        color = Color(0xFFFF7777),
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }
                if (!state.cameraActive && !state.doorClosed) {
                    Text(
                        "The hallway is quiet...",
                        color = Color(0xFF777F8C),
                        fontSize = 14.sp
                    )
                }
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("POWER", color = Color.White, fontWeight = FontWeight.Bold)
                Text("${state.power.toInt()}%", color = Color(0xFFFFB4A9))
            }
            LinearProgressIndicator(
                progress = { powerProgress },
                modifier = Modifier.fillMaxWidth().height(9.dp),
                color = if (state.power < 25f) Color(0xFFE53945) else Color(0xFF75C98B),
                trackColor = Color(0xFF343944)
            )
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("THREAT LEVEL", color = Color.White, fontWeight = FontWeight.Bold)
                Text("${state.threat}%", color = Color(0xFFFF7777))
            }
            LinearProgressIndicator(
                progress = { state.threat / 100f },
                modifier = Modifier.fillMaxWidth().height(7.dp),
                color = Color(0xFFE53945),
                trackColor = Color(0xFF343944)
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Button(
                onClick = onToggleDoor,
                enabled = !isOver && state.power > 0f,
                modifier = Modifier.weight(1f).height(54.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (state.doorClosed) Color(0xFF8B1E2D) else Color(0xFF343944)
                )
            ) {
                Text(if (state.doorClosed) "OPEN DOOR" else "CLOSE DOOR", fontSize = 11.sp)
            }
            Button(
                onClick = onToggleCamera,
                enabled = !isOver && state.power > 0f,
                modifier = Modifier.weight(1f).height(54.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF343944))
            ) {
                Text(if (state.cameraActive) "EXIT CAM" else "CAMERAS", fontSize = 11.sp)
            }
        }

        if (isOver) {
            Surface(
                color = Color(0xFF241016),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        if (state.phase == GamePhase.WON) "6 AM — YOU SURVIVED" else "NIGHT OVER",
                        color = if (state.phase == GamePhase.WON) Color(0xFF8FE3A2) else Color(0xFFFF7777),
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Black
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        if (state.phase == GamePhase.WON) "The shift is complete." else "Your shift ended. Try a different strategy.",
                        color = Color.LightGray,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        onRestart?.let {
                            Button(onClick = it) { Text("RETRY") }
                        }
                        onMenu?.let {
                            OutlinedButton(onClick = it) { Text("MAIN MENU") }
                        }
                    }
                }
            }
        }
        Text(
            "TIP: Doors protect you but consume extra power.",
            color = Color(0xFF777F8C),
            fontSize = 11.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
