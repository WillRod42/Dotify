package com.example.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import com.example.dotify.SongListFragment.Companion.ARG_SONG_LIST

class PrimaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_primary)

        val songList = ArrayList(SongDataProvider.getAllSongs())
        val songListFragment = SongListFragment()
        val argBundle = Bundle().apply {
            putParcelableArrayList(ARG_SONG_LIST, songList)
        }
        songListFragment.arguments = argBundle
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragContainer, songListFragment)
            .commit()
    }
}
