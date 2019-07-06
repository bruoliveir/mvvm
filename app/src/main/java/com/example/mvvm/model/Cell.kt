package com.example.mvvm.model

class Cell(var player: Player?) {
    fun isEmpty() = player == null || player?.value.isNullOrEmpty()
}