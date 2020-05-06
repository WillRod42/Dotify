package com.example.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import kotlinx.android.synthetic.main.activity_song_list.*

class SongListActivity : AppCompatActivity() {

    private val tvMiniPlayerTitle by lazy { findViewById<TextView>(R.id.tvMiniPlayerTitle) }

    override fun onCreate(savedInstanceState: Bundle?) {
        title = "All Songs"

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)

        val listOfSongs = SongDataProvider.getAllSongs()
        val songAdapter = SongListAdapter(listOfSongs)

        songAdapter.onSongClickListener = { song: Song ->
            tvMiniPlayerTitle.text = getString(R.string.miniPlayerTitle).format(song.title, song.artist)
        }

        rvSongList.adapter = songAdapter
    }

    private fun onClickSongListener() {

    }
}
