package com.example.dotify.data

data class SongList (
    val title: String,
    val numOfSongs: Int,
    val songs: List<Song>
)