package com.example.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var numPlays = Random.nextInt(1000, 10000000)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvPlays = findViewById<TextView>(R.id.tvPlays)
        tvPlays.text = getString(R.string.numPlaysText).format(numPlays.toString())

        // Track Buttons
        val ibPrev = findViewById<ImageButton>(R.id.ibPrev)
        val ibPlay = findViewById<ImageButton>(R.id.ibPlay)
        val ibNext = findViewById<ImageButton>(R.id.ibNext)

        ibPrev.setOnClickListener {
            Toast.makeText(this, "Skipping to previous track", Toast.LENGTH_SHORT).show()
        }

        ibPlay.setOnClickListener {
            numPlays += 1
            tvPlays.text = getString(R.string.numPlaysText).format(numPlays.toString())
        }

        ibNext.setOnClickListener {
            Toast.makeText(this, "Skipping to next track", Toast.LENGTH_SHORT).show()
        }

        // Change username button
        val tvUsername = findViewById<TextView>(R.id.tvUsername)
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val btnChangeUser = findViewById<Button>(R.id.btnChangeUser)
        btnChangeUser.setOnClickListener {
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

    companion object {
        const val SONG_KEY = "song_key"
    }
}
