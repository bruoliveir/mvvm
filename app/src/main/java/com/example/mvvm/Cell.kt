package com.example.mvvm

class Cell(var player: Player?) {
    fun isEmpty() = player == null || player?.value.isNullOrEmpty()
}