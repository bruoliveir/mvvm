package com.example.mvvm.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mvvm.model.Game

class GameViewModel : ViewModel() {
    fun init(playerOne: String, playerTwo: String) {
        Game.start(playerOne, playerTwo)
    }

    fun onClickedCellAt(row: Int, col: Int) {
        if (Game.cells[row][col].isEmpty()) {
            Game.cells[row][col].player = Game.currentPlayer
            if (!Game.hasEnded()) Game.switchPlayer()
        }
    }

    fun getWinner() = Game.winner
}