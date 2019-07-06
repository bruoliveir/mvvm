package com.example.mvvm.view

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mvvm.R
import com.example.mvvm.databinding.ActivityMainBinding
import com.example.mvvm.model.Player
import com.example.mvvm.viewmodel.GameViewModel
import kotlinx.android.synthetic.main.view_dialog_names.view.*

class MainActivity : AppCompatActivity() {

    private var gameViewModel: GameViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        askNames()
    }

    private fun askNames() {
        val inputFields = layoutInflater.inflate(R.layout.view_dialog_names, null)
        AlertDialog.Builder(this)
                .setTitle(R.string.dialog_names_title)
                .setView(inputFields)
                .setCancelable(false)
                .setPositiveButton(R.string.dialog_ok) { _, _ ->
                    val playerX = inputFields.etPlayerX.text.toString()
                    val playerO = inputFields.etPlayerO.text.toString()
                    bindData(playerX, playerO)
                }
                .setNegativeButton(R.string.dialog_close) { dialogInterface, _ ->
                    dialogInterface.dismiss()
                }
                .show()
    }

    private fun bindData(playerX: String, playerO: String) {
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        gameViewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)
        gameViewModel?.init(playerX, playerO)
        binding.gameViewModel = gameViewModel
        setListener()
    }

    private fun setListener() {
        gameViewModel?.getWinner()?.observe(this, Observer { winner: Player? ->
            endGame(winner)
        })
    }

    private fun endGame(winner: Player?) {
        val message =
                if (winner == null) getString(R.string.dialog_draw_title)
                else getString(R.string.dialog_winner_title, winner.name)
        AlertDialog.Builder(this)
                .setTitle(message)
                .setCancelable(false)
                .setPositiveButton(R.string.dialog_ok) { dialogInterface, _ ->
                    dialogInterface.dismiss()
                    askNames()
                }
                .setNegativeButton(R.string.dialog_close) { dialogInterface, _ ->
                    dialogInterface.dismiss()
                    finish()
                }
                .show()
    }
}
