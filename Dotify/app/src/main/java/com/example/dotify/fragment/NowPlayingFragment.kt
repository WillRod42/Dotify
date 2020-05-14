package com.example.dotify.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.dotify.DotifyApplication
import com.example.dotify.R
import kotlinx.android.synthetic.main.fragment_now_playing.*
import kotlin.random.Random
import com.example.dotify.data.Song
import com.squareup.picasso.Picasso

class NowPlayingFragment : Fragment() {
    private var numPlays = 0
    private lateinit var currentSong: Song

    companion object {
        const val SAVE_PLAYS = "save_plays"
        const val TAG = "nowplayingfragment"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val dotifyApplication = context.applicationContext as DotifyApplication
        this.currentSong = dotifyApplication.currentSong
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        numPlays = Random.nextInt(1000, 10000000)
        if(savedInstanceState != null) {
            numPlays = savedInstanceState.getInt(SAVE_PLAYS, 0)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_now_playing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvPlays.text = getString(R.string.numPlaysText).format(numPlays.toString())
        updateViews()

        ibPrev.setOnClickListener {
            Toast.makeText(context, "Skipping to previous track", Toast.LENGTH_SHORT).show()
        }

        ibPlay.setOnClickListener {
            numPlays += 1
            tvPlays.text = getString(R.string.numPlaysText).format(numPlays.toString())
        }

        ibNext.setOnClickListener {
            Toast.makeText(context, "Skipping to next track", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(SAVE_PLAYS, numPlays)
        super.onSaveInstanceState(outState)
    }

    fun updateSong(song: Song) {
        this.currentSong = song
        updateViews()
    }

    private fun updateViews() {
        tvSongTitle.text = currentSong.title
        tvArtistName.text = currentSong.artist
        ivAlbumCover.contentDescription = currentSong.title
        Picasso.get().load(currentSong.largeImageURL).into(ivAlbumCover)
    }
}
