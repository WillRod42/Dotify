package com.example.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var numPlays = Random.nextInt(1000, 10000000)
    //private var username = getString(R.string.username)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvPlays = findViewById<TextView>(R.id.tvPlays)
        var playText = "${numPlays.toString()}  plays"
        tvPlays.text = playText

        // Track Buttons
        val ibPrev = findViewById<ImageButton>(R.id.ibPrev)
        val ibPlay = findViewById<ImageButton>(R.id.ibPlay)
        val ibNext = findViewById<ImageButton>(R.id.ibNext)

        ibPrev.setOnClickListener { ibPrev: View ->
            Toast.makeText(this, "Skipping to previous track", Toast.LENGTH_SHORT).show()
        }

        ibPlay.setOnClickListener { ibPlay: View ->
            numPlays += 1
            playText = "${numPlays.toString()}  plays"
            tvPlays.text = playText
        }

        ibNext.setOnClickListener { ibNext: View ->
            Toast.makeText(this, "Skipping to next track", Toast.LENGTH_SHORT).show()
        }

        // Change username button
        val tvUsername = findViewById<TextView>(R.id.tvUsername)
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val btnChangeUser = findViewById<Button>(R.id.btnChangeUser)
        btnChangeUser.setOnClickListener { view: View ->
            if(tvUsername.visibility == TextView.VISIBLE) {
                tvUsername.visibility = TextView.INVISIBLE
                etUsername.visibility = EditText.VISIBLE
                btnChangeUser.text = getString(R.string.btnChangeUserAltText)
                etUsername.text.clear()
            } else if(!etUsername.text.isBlank()) {
                tvUsername.visibility = TextView.VISIBLE
                etUsername.visibility = EditText.INVISIBLE
                tvUsername.text = etUsername.text.toString()
                btnChangeUser.text = getString(R.string.btnChangeUserText)
            }
        }
    }
}
