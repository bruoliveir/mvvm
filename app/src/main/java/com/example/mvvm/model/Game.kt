package com.example.mvvm.model

import androidx.lifecycle.MutableLiveData

object Game {
    var player1: Player? = null
    var player2: Player? = null
    var currentPlayer: Player? = null
    var cells = Array(3) { Array(3) { Cell(null) } }
    val winner = MutableLiveData<Player>()

    fun start(playerOne: String, playerTwo: String) {
        player1 = Player(playerOne, "x")
        player2 = Player(playerTwo, "o")
        currentPlayer = player1
        cells.flatten().forEach { it.player = null }
    }

    fun switchPlayer() {
        currentPlayer = if (currentPlayer == player1) player2 else player1
    }

    fun hasEnded() = when {
        hasHorizontalSolution() || hasVerticalSolution() || hasDiagonalSolution() -> {
            winner.value = currentPlayer
            true
        }
        isBoardFull() -> {
            winner.value = null
            true
        }
        else -> false
    }

    private fun isBoardFull() = cells.flatten().none { it.isEmpty() }

    private fun hasHorizontalSolution() =
        cells.any { it.all { it.player == player1 } || it.all { it.player == player2 } }

    private fun hasVerticalSolution(): Boolean {
        (0..2).forEach {
            if (cells[0][it].player == cells[1][it].player && cells[1][it].player == cells[2][it].player)
                return true
        }
        return false
    }

    private fun hasDiagonalSolution() =
        (cells[0][0].player == cells[1][1].player && cells[1][1].player == cells[2][2].player
                || cells[0][2].player == cells[1][1].player && cells[1][1].player == cells[2][0].player)
}