package com.finalnight.game.game

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.finalnight.game.data.GameRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = GameRepository(application)
    private val _uiState = MutableStateFlow(GameState())
    val uiState = _uiState.asStateFlow()

    private var nightJob: Job? = null

    init {
        viewModelScope.launch {
            repository.bestTime.collect { best ->
                _uiState.update { it.copy(bestTimeSeconds = best) }
            }
        }
    }

    fun startNight() {
        nightJob?.cancel()
        _uiState.update {
            it.copy(
                phase = GamePhase.PLAYING,
                elapsedSeconds = 0,
                power = 100f,
                doorClosed = false,
                cameraActive = false,
                threat = 0
            )
        }

        nightJob = viewModelScope.launch {
            while (_uiState.value.phase == GamePhase.PLAYING) {
                delay(1_000)
                advanceNight()
            }
        }
    }

    fun returnToMenu() {
        nightJob?.cancel()
        _uiState.update { it.copy(phase = GamePhase.MENU, cameraActive = false) }
    }

    fun toggleDoor() {
        _uiState.update { state ->
            if (state.phase == GamePhase.PLAYING && state.power > 0f) {
                state.copy(doorClosed = !state.doorClosed)
            } else state
        }
    }

    fun toggleCamera() {
        _uiState.update { state ->
            if (state.phase == GamePhase.PLAYING && state.power > 0f) {
                state.copy(cameraActive = !state.cameraActive)
            } else state
        }
    }

    private fun advanceNight() {
        val current = _uiState.value
        if (current.phase != GamePhase.PLAYING) return

        val drain = 0.45f +
            (if (current.doorClosed) 0.9f else 0f) +
            (if (current.cameraActive) 0.7f else 0f)

        val newPower = (current.power - drain).coerceAtLeast(0f)
        val newThreat = if (current.doorClosed) {
            (current.threat - 3).coerceAtLeast(0)
        } else {
            (current.threat + if (current.cameraActive) 1 else 3).coerceAtMost(100)
        }
        val newElapsed = current.elapsedSeconds + 1

        val newPhase = when {
            newPower <= 0f || newThreat >= 100 -> GamePhase.LOST
            newElapsed >= 60 -> GamePhase.WON
            else -> GamePhase.PLAYING
        }

        _uiState.update {
            it.copy(
                elapsedSeconds = newElapsed,
                power = newPower,
                threat = newThreat,
                phase = newPhase
            )
        }

        if (newPhase == GamePhase.WON) {
            viewModelScope.launch {
                repository.saveBestTime(newElapsed)
            }
        }

        if (newPhase != GamePhase.PLAYING) {
            nightJob?.cancel()
        }
    }
}
