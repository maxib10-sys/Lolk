package com.finalnight.game.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainMenuScreen(
    bestTimeSeconds: Int,
    onStart: () -> Unit
) {
    val pulse by rememberInfiniteTransition(label = "title_pulse").animateFloat(
        initialValue = 0.72f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1_400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "title_alpha"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF21090E), Color(0xFF090A0F), Color(0xFF050609))
                )
            )
            .padding(28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "FNAF",
            color = Color(0xFFE53945),
            fontSize = 54.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 8.sp,
            modifier = Modifier.alpha(pulse)
        )
        Text(
            text = "FINAL NIGHT",
            color = Color.White,
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 5.sp
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = "ONE SHIFT. SIX HOURS. STAY ALERT.",
            color = Color(0xFFB8AEB0),
            fontSize = 11.sp,
            letterSpacing = 2.sp,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(64.dp))
        Button(
            onClick = onStart,
            modifier = Modifier.width(230.dp).height(54.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFB71C2C),
                contentColor = Color.White
            )
        ) {
            Text("START NIGHT", fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
        }
        Spacer(Modifier.height(24.dp))
        Text(
            text = "BEST SURVIVAL: ${bestTimeSeconds}s",
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 12.sp,
            letterSpacing = 1.sp
        )
        Spacer(Modifier.height(72.dp))
        Text(
            text = "HEADPHONES RECOMMENDED  •  KEEP THE LIGHTS ON",
            color = Color(0xFF71696B),
            fontSize = 9.sp,
            textAlign = TextAlign.Center,
            letterSpacing = 1.sp
        )
    }
}
