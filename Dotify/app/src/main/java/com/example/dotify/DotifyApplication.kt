package com.example.dotify

import android.app.Application
import com.example.dotify.data.Song

class DotifyApplication: Application() {
    lateinit var apiManager: ApiManager
    lateinit var currentSong: Song
    lateinit var listOfSongs: List<Song>


    override fun onCreate() {
        super.onCreate()

        apiManager = ApiManager(this)
    }
}