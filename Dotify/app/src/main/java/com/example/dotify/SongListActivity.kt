package com.example.dotify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import com.example.dotify.MainActivity.Companion.SONG_KEY
import kotlinx.android.synthetic.main.activity_song_list.*

class SongListActivity : AppCompatActivity() {

    private val tvMiniPlayerTitle by lazy { findViewById<TextView>(R.id.tvMiniPlayerTitle) }
    private var currentSong = SongDataProvider.getAllSongs()[0]

    override fun onCreate(savedInstanceState: Bundle?) {
        title = "All Songs"

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)

        val listOfSongs = SongDataProvider.getAllSongs()
        val songAdapter = SongListAdapter(listOfSongs)

        songAdapter.onSongClickListener = { song: Song ->
            currentSong = song
            tvMiniPlayerTitle.text = getString(R.string.miniPlayerTitle).format(song.title, song.artist)
        }

        rvSongList.adapter = songAdapter

        val btnShuffle = findViewById<Button>(R.id.btnShuffle)
        btnShuffle.setOnClickListener {
            songAdapter.change(listOfSongs.shuffled())
        }

        val llMiniPlayer = findViewById<LinearLayout>(R.id.llMiniPlayer)
        llMiniPlayer.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(SONG_KEY, currentSong)
            startActivity(intent)
        }
    }
}
