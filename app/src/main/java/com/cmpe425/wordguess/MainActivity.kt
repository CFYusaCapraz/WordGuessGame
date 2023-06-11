package com.cmpe425.wordguess

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var edLetter: EditText
    private lateinit var edWord: EditText
    private lateinit var btnGuessLetter: Button
    private lateinit var btnGuessWord: Button
    private lateinit var btnRetry: Button
    private lateinit var word: String
    private lateinit var databaseHelper : DatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        databaseHelper = DatabaseHelper(this)

        initComponents()
        word = databaseHelper.randomWord
        databaseHelper.close()
        var position = 0

        btnGuessLetter.setOnClickListener {
            if (edLetter.text.isNotEmpty()) {
                if (onClickGuessLetter()) {
                    Toast.makeText(
                        applicationContext,
                        "You guessed a correct letter",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    if (position >= 4) {
                        Toast.makeText(
                            applicationContext,
                            "You run out of guess",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        restartGame()
                        position = 0
                    } else {
                        when (position) {
                            0 -> findViewById<TextView>(R.id.textView_heart1).visibility = View.GONE
                            1 -> findViewById<TextView>(R.id.textView_heart2).visibility = View.GONE
                            2 -> findViewById<TextView>(R.id.textView_heart3).visibility = View.GONE
                            3 -> findViewById<TextView>(R.id.textView_heart4).visibility = View.GONE
                            4 -> findViewById<TextView>(R.id.textView_heart5).visibility = View.GONE
                        }
                        position++
                    }
                }
                if (foundCorrectWord()) {
                    Toast.makeText(
                        applicationContext,
                        "You have found the correct word",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    restartGame()
                    position = 0
                }
            } else {
                Toast.makeText(applicationContext, "Please type a letter", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        btnGuessWord.setOnClickListener {
            if (edWord.text.isNotEmpty()) {
                if (onClickGuessWord()) {
                    Toast.makeText(
                        applicationContext,
                        "You have found the correct word",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    restartGame()
                    position = 0
                } else {
                    Toast.makeText(
                        applicationContext,
                        "You could not found the correct word. Please try again",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    restartGame()
                    position = 0
                }
            } else if (edWord.text.length < 4) {
                Toast.makeText(
                    applicationContext,
                    "Please type a 5 letter word",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }

        btnRetry.setOnClickListener {
            restartGame()
            position = 0
        }
    }

    private fun initComponents() {
        edLetter = findViewById(R.id.editText_Letter)
        edWord = findViewById(R.id.editText_Word)
        btnGuessLetter = findViewById(R.id.button_guess_letter)
        btnGuessWord = findViewById(R.id.button_guess_word)
        btnRetry = findViewById(R.id.button_retry)
    }

    private fun onClickGuessLetter(): Boolean {
        val letter = edLetter.text[0]
        edLetter.setText("")
        var position = 0
        var found = false
        word.forEach { ch: Char ->
            if (ch == letter) {
                when (position) {
                    0 -> findViewById<TextView>(R.id.textView_1).text = letter.toString()
                    1 -> findViewById<TextView>(R.id.textView_2).text = letter.toString()
                    2 -> findViewById<TextView>(R.id.textView_3).text = letter.toString()
                    3 -> findViewById<TextView>(R.id.textView_4).text = letter.toString()
                    4 -> findViewById<TextView>(R.id.textView_5).text = letter.toString()
                }
                found = true
            }
            position++
        }
        return found
    }

    private fun onClickGuessWord(): Boolean {
        val word = edWord.text.toString()
        var found = false
        var position = 0
        if (word == this.word) {
            found = true
            this.word.forEach { ch: Char ->
                when (position) {
                    0 -> findViewById<TextView>(R.id.textView_1).text = ch.toString()
                    1 -> findViewById<TextView>(R.id.textView_2).text = ch.toString()
                    2 -> findViewById<TextView>(R.id.textView_3).text = ch.toString()
                    3 -> findViewById<TextView>(R.id.textView_4).text = ch.toString()
                    4 -> findViewById<TextView>(R.id.textView_5).text = ch.toString()
                }
                position++
            }
        }
        return found
    }

    private fun foundCorrectWord(): Boolean {
        var result = false
        val ch1 = findViewById<TextView>(R.id.textView_1).text.toString()
        val ch2 = findViewById<TextView>(R.id.textView_2).text.toString()
        val ch3 = findViewById<TextView>(R.id.textView_3).text.toString()
        val ch4 = findViewById<TextView>(R.id.textView_4).text.toString()
        val ch5 = findViewById<TextView>(R.id.textView_5).text.toString()

        val guess = ch1 + ch2 + ch3 + ch4 + ch5
        if (guess == word)
            result = true
        return result
    }

    private fun restartGame() {
        findViewById<TextView>(R.id.textView_1).text = ""
        findViewById<TextView>(R.id.textView_2).text = ""
        findViewById<TextView>(R.id.textView_3).text = ""
        findViewById<TextView>(R.id.textView_4).text = ""
        findViewById<TextView>(R.id.textView_5).text = ""
        databaseHelper = DatabaseHelper(this)
        word = databaseHelper.randomWord;
        databaseHelper.close()
        findViewById<TextView>(R.id.textView_heart1).visibility = View.VISIBLE
        findViewById<TextView>(R.id.textView_heart2).visibility = View.VISIBLE
        findViewById<TextView>(R.id.textView_heart3).visibility = View.VISIBLE
        findViewById<TextView>(R.id.textView_heart4).visibility = View.VISIBLE
        findViewById<TextView>(R.id.textView_heart5).visibility = View.VISIBLE

    }
}