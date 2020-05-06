package com.example.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.ericchee.songdataprovider.Song
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var numPlays = Random.nextInt(1000, 10000000)
    private val tvPlays by lazy { findViewById<TextView>(R.id.tvPlays) }
    private val tvSongTitle by lazy { findViewById<TextView>(R.id.tvSongTitle) }
    private val tvArtistName by lazy { findViewById<TextView>(R.id.tvArtistName) }
    private val ivAlbumCover by lazy { findViewById<ImageView>(R.id.ivAlbumCover) }
    private val ibPrev by lazy { findViewById<ImageButton>(R.id.ibPrev) }
    private val ibPlay by lazy { findViewById<ImageButton>(R.id.ibPlay) }
    private val ibNext by lazy { findViewById<ImageButton>(R.id.ibNext) }
    private val tvUsername by lazy { findViewById<TextView>(R.id.tvUsername) }
    private val etUsername by lazy { findViewById<EditText>(R.id.etUsername) }
    private val btnChangeUser by lazy { findViewById<Button>(R.id.btnChangeUser) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val song = intent.getParcelableExtra<Song>(SONG_KEY)
        val imgID = intent.getIntExtra(IMAGE_KEY, -1)
        initText(song, imgID)

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

    private fun initText(song: Song?, imgID: Int) {
        tvPlays.text = getString(R.string.numPlaysText).format(numPlays.toString())
        tvSongTitle.text = song?.title
        tvArtistName.text = song?.artist
        ivAlbumCover.setImageResource(imgID)
        ivAlbumCover.contentDescription = song?.title
    }

    companion object {
        const val SONG_KEY = "song_key"
        const val IMAGE_KEY = "image_key"
    }
}
