package com.example.dotify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.ericchee.songdataprovider.Song
import kotlinx.android.synthetic.main.activity_primary.*
import kotlinx.android.synthetic.main.fragment_now_playing.*
import kotlinx.android.synthetic.main.fragment_now_playing.ivAlbumCover
import kotlinx.android.synthetic.main.fragment_now_playing.tvArtistName
import kotlinx.android.synthetic.main.fragment_now_playing.tvPlays
import kotlinx.android.synthetic.main.fragment_now_playing.tvSongTitle
import kotlin.random.Random

class NowPlayingFragment : Fragment() {
    private var numPlays = 0
    private lateinit var song: Song

    companion object {
        const val ARG_SONG = "arg_song"
        const val TAG = "nowplayingfragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { args ->
            val song = args.getParcelable<Song>(ARG_SONG)
            if(song != null) {
                this.song = song
            }
        }

        numPlays = Random.nextInt(1000, 10000000)
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

    fun updateSong(song: Song) {
        this.song = song
        updateViews()
    }

    private fun updateViews() {
        tvSongTitle.text = song.title
        tvArtistName.text = song.artist
        ivAlbumCover.setImageResource(song.largeImageID)
        ivAlbumCover.contentDescription = song.title
    }
}
