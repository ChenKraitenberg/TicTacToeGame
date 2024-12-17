package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.example.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{
    enum class Turn
    {
        O,
        X
    }

    private var firstTurn = Turn.X
    private var currentTurn = Turn.X

    private var xScore = 0
    private var oScore = 0


    private var boardList = mutableListOf<Button>()

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBoard()
    }

    private fun initBoard()
    {
        boardList.add(binding.a1)
        boardList.add(binding.a2)
        boardList.add(binding.a3)
        boardList.add(binding.b1)
        boardList.add(binding.b2)
        boardList.add(binding.b3)
        boardList.add(binding.c1)
        boardList.add(binding.c2)
        boardList.add(binding.c3)
    }
    
    private fun fullBoard(): Boolean {
        for(button in boardList)
        {
            if(button.text == "")
                return false
        }
        return true
    }

    fun boardTapped(view: View)
    {
        if(view !is Button || view.text != "") return

        addToBoard(view)

        if (checkForVictory("X")) {
            xScore++
            result("X Wins!")
        } else if (checkForVictory("O")) {
            oScore++
            result("O Wins!")
        } else if (fullBoard()) {
            // Draw
            result("It's a Draw!")
        }
    }




    private fun checkForVictory(s: String): Boolean {
        // Horizontal Wins
        if(much(binding.a1, s) && much(binding.a2, s) && much(binding.a3, s))
            return true
        if(much(binding.b1, s) && much(binding.b2, s) && much(binding.b3, s))
            return true
        if(much(binding.c1, s) && much(binding.c2, s) && much(binding.c3, s))
            return true

        // Vertical Wins
        if(much(binding.a1, s) && much(binding.b1, s) && much(binding.c1, s))
            return true
        if(much(binding.a2, s) && much(binding.b2, s) && much(binding.c2, s))
            return true
        if(much(binding.a3, s) && much(binding.b3, s) && much(binding.c3, s))
            return true

        // Diagonal Wins
        if(much(binding.a1, s) && much(binding.b2, s) && much(binding.c3, s))
            return true
        if(much(binding.a3, s) && much(binding.b2, s) && much(binding.c1, s))
            return true

        return false
    }

    private fun much(button: Button, symbol: String): Boolean
    {
        return button.text == symbol
    }


    private fun result(title: String) {
        val message = "\nX Score: $xScore\nO Score: $oScore"
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("play again")
            { _,_ ->
                resetBoard()
            }
            .setCancelable(false)
            .show()
    }

    private fun resetBoard() {
        for(button in boardList)
        {
            button.text = ""
        }

        if(firstTurn == Turn.O)
            firstTurn = Turn.X
        else if(firstTurn == Turn.X)
            firstTurn = Turn.O

        currentTurn = firstTurn
        setTurnLabel()
    }


    private fun addToBoard(button: Button)
    {
        if(button.text != "")
            return

        if(currentTurn == Turn.O)
        {
            button.text = O
            currentTurn = Turn.X
        }
        else if(currentTurn == Turn.X)
        {
            button.text = X
            currentTurn = Turn.O
        }
        setTurnLabel()
    }

    private fun setTurnLabel()
    {
        binding.turnTV.text = if (currentTurn == Turn.X) "Turn X" else "Turn O"
        binding.turnTV.setTextColor(if (currentTurn == Turn.X) getColor(R.color.purple_500) else getColor(R.color.teal_700))
    }


    companion object
    {
        const val O = "O"
        const val X = "X"
    }

}








