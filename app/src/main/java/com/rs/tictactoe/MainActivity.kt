package com.rs.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private var currentPlayer = "X"
    private var board = Array(3) { arrayOfNulls<String>(3) }
    private val buttons = mutableListOf<Button>()
    private val resetButton: Button by lazy { findViewById(R.id.buttonReset) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize buttons
        val buttonIds = listOf(
            R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6,
            R.id.button7, R.id.button8, R.id.button9
        )

        for (id in buttonIds) {
            val button = findViewById<Button>(id)
            buttons.add(button)
            button.setOnClickListener { onCellClicked(button) }
        }
        resetButton.visibility = View.INVISIBLE
    }

    private fun onCellClicked(button: Button) {
        if (button.text.isEmpty()) {
            button.text = currentPlayer
            updateBoardState(button)
            if (checkWinner()) {
                Toast.makeText(this, "Player $currentPlayer wins!", Toast.LENGTH_LONG).show()

                resetButton.visibility = View.VISIBLE
                resetButton.setOnClickListener { resetGame() }
            } else {
                currentPlayer = if (currentPlayer == "X") "O" else "X"
            }
        }
    }

    private fun updateBoardState(button: Button) {
        val index = buttons.indexOf(button)
        val row = index / 3
        val col = index % 3
        board[row][col] = currentPlayer
    }

    private fun checkWinner(): Boolean {
        // Check rows, columns, and diagonals
        for (i in 0..2) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) return true
            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer) return true
        }
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) return true
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) return true
        return false
    }

    private fun resetGame() {
        board = Array(3) { arrayOfNulls<String>(3) }
        for (button in buttons) button.text = ""
        currentPlayer = "X"
        resetButton.visibility = View.INVISIBLE
    }
}