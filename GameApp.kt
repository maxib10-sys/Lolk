package com.finalnight.game.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.finalnight.game.game.GamePhase
import com.finalnight.game.game.GameViewModel
import com.finalnight.game.ui.screens.MainMenuScreen
import com.finalnight.game.ui.screens.OfficeScreen

@Composable
fun GameApp(gameViewModel: GameViewModel = viewModel()) {
    val state by gameViewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        AnimatedContent(
            targetState = state.phase,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "game_phase_transition"
        ) { phase ->
            when (phase) {
                GamePhase.MENU -> MainMenuScreen(
                    bestTimeSeconds = state.bestTimeSeconds,
                    onStart = gameViewModel::startNight
                )
                GamePhase.PLAYING -> OfficeScreen(
                    state = state,
                    onToggleDoor = gameViewModel::toggleDoor,
                    onToggleCamera = gameViewModel::toggleCamera
                )
                GamePhase.WON, GamePhase.LOST -> OfficeScreen(
                    state = state,
                    onToggleDoor = gameViewModel::toggleDoor,
                    onToggleCamera = gameViewModel::toggleCamera,
                    onRestart = gameViewModel::startNight,
                    onMenu = gameViewModel::returnToMenu
                )
            }
        }
    }
}
