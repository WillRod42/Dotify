package com.example.dotify

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ericchee.songdataprovider.Song
import kotlinx.android.synthetic.main.fragment_song_list.*

class SongListFragment : Fragment() {
    private lateinit var songListAdapter: SongListAdapter
    private lateinit var listOfSongs: List<Song>
    private var onSongClick: OnSongClickListener? = null

    companion object {
        const val TAG = "song_list_tag"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnSongClickListener) {
            onSongClick = context
        }

        val dotifyApplication = context.applicationContext as DotifyApplication
        this.listOfSongs = dotifyApplication.listOfSongs
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_song_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        songListAdapter = SongListAdapter(listOfSongs)
        songListAdapter.onSongClickListener = { song ->
            onSongClick?.onSongClick(song)
        }
        rvSongList.adapter = songListAdapter

    }

    fun shuffleList() {
        songListAdapter.change(listOfSongs.shuffled())
    }
}

interface OnSongClickListener {
    fun onSongClick(song: Song)
}
