package com.example.dotify

data class Song (
    private val id: String,
    private val title: String,
    private val artist: String,
    private val durationMillis: Int,
    private val smallImageURL: String,
    private val largeImageURL: String
)