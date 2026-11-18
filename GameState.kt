package com.finalnight.game.game

enum class GamePhase {
    MENU,
    PLAYING,
    WON,
    LOST
}

data class GameState(
    val phase: GamePhase = GamePhase.MENU,
    val elapsedSeconds: Int = 0,
    val power: Float = 100f,
    val doorClosed: Boolean = false,
    val cameraActive: Boolean = false,
    val threat: Int = 0,
    val bestTimeSeconds: Int = 0
) {
    val nightTime: String
        get() {
            val hourIndex = (elapsedSeconds / 10).coerceAtMost(6)
            val hour = if (hourIndex == 0) 12 else hourIndex
            return "$hour AM"
        }
}
