package com.example.dotify

import android.app.Application
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider

class DotifyApplication: Application() {
    lateinit var listOfSongs: List<Song>
    lateinit var currentSong: Song

    override fun onCreate() {
        super.onCreate()

        listOfSongs = SongDataProvider.getAllSongs()
        currentSong = listOfSongs[0]
    }
}