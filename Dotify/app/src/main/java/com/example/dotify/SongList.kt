package com.example.dotify

data class SongList (
    private val title: String,
    private val numOfSongs: Int,
    private val songs: List<Song>
)