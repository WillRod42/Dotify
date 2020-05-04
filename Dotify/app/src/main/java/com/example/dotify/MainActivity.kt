package com.example.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var numPlays = Random.nextInt(1000, 10000000)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvPlays = findViewById<TextView>(R.id.tvPlays)
        val playText = "${numPlays.toString()}  plays"
        tvPlays.text = playText
    }
}
