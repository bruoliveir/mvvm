package com.example.mvvm.viewmodel

import androidx.databinding.ObservableArrayMap
import androidx.lifecycle.ViewModel
import com.example.mvvm.model.Game

class GameViewModel : ViewModel() {
    private lateinit var game: Game
    val cells = ObservableArrayMap<String, String?>()
    var playerX = ""
    var playerO = ""

    fun init(playerX: String, playerO: String) {
        this.playerX = playerX
        this.playerO = playerO
        cells.clear()
        game = Game(playerX, playerO)
    }

    fun onClickedCellAt(row: Int, col: Int) {
        if (game.cells[row][col].isEmpty()) {
            game.cells[row][col].player = game.currentPlayer
            cells["$row$col"] = game.currentPlayer?.value
            if (!game.hasEnded()) game.switchPlayer()
        }
    }

    fun getWinner() = game.winner
}