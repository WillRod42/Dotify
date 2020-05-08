package com.example.dotify

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ericchee.songdataprovider.Song
import kotlinx.android.synthetic.main.activity_song_list.*

class SongListFragment : Fragment() {
    private lateinit var songListAdapter: SongListAdapter
    private lateinit var listOfSongs: List<Song>
    private var onSongClick: OnSongClickListener? = null

    companion object {
        const val ARG_SONG_LIST = "arg_song_list"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnSongClickListener) {
            onSongClick = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { args ->
            val list = args.getParcelableArrayList<Song>(ARG_SONG_LIST)
            if(list != null) {
                this.listOfSongs = list
            }
        }
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
