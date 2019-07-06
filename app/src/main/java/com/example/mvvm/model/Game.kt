package com.example.mvvm.model

import androidx.lifecycle.MutableLiveData

class Game(playerX: String, playerO: String) {
    private val playerX: Player? = Player(playerX, "X")
    private val playerO: Player? = Player(playerO, "O")
    var currentPlayer: Player? = this.playerX
    val cells = Array(3) { Array(3) { Cell(null) } }
    val winner = MutableLiveData<Player>()

    fun switchPlayer() {
        currentPlayer = if (currentPlayer == playerX) playerO else playerX
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
            cells.any { it.all { it.player == playerX } || it.all { it.player == playerO } }

    private fun hasVerticalSolution(): Boolean {
        (0..2).forEach {
            val firstCell = cells[0][it]
            if (!firstCell.isEmpty()
                    && firstCell.player == cells[1][it].player
                    && firstCell.player == cells[2][it].player)
                return true
        }
        return false
    }

    private fun hasDiagonalSolution(): Boolean {
        val centerCell = cells[1][1]
        return !centerCell.isEmpty() &&
                ((centerCell.player == cells[0][0].player && centerCell.player == cells[2][2].player) ||
                        (centerCell.player == cells[0][2].player && centerCell.player == cells[2][0].player))
    }
}