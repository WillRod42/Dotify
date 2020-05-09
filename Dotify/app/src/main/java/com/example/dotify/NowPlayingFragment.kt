package com.example.dotify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ericchee.songdataprovider.Song
import kotlinx.android.synthetic.main.fragment_now_playing.*
import kotlinx.android.synthetic.main.fragment_now_playing.ivAlbumCover
import kotlinx.android.synthetic.main.fragment_now_playing.tvArtistName
import kotlinx.android.synthetic.main.fragment_now_playing.tvPlays
import kotlinx.android.synthetic.main.fragment_now_playing.tvSongTitle
import kotlin.random.Random

class NowPlayingFragment : Fragment() {
    private var numPlays = 0
    private lateinit var currentSong: Song

    companion object {
        const val SAVE_SONG = "save_song"
        const val SAVE_PLAYS = "save_plays"
        const val ARG_SONG = "arg_song"
        const val TAG = "nowplayingfragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(savedInstanceState != null) {
            val savedSong = savedInstanceState.getParcelable<Song>(SAVE_SONG)
            if(savedSong != null) {
                currentSong = savedSong
            }

            numPlays = savedInstanceState.getInt(SAVE_PLAYS, 0)
        } else {
            arguments?.let { args ->
                val song = args.getParcelable<Song>(ARG_SONG)
                if(song != null) {
                    this.currentSong = song
                }
            }

            numPlays = Random.nextInt(1000, 10000000)
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
        outState.putParcelable(SAVE_SONG, currentSong)
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
        ivAlbumCover.setImageResource(currentSong.largeImageID)
        ivAlbumCover.contentDescription = currentSong.title
    }
}
