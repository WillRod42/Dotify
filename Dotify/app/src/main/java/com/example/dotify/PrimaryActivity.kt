package com.example.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import com.example.dotify.SongListFragment.Companion.ARG_SONG_LIST
import kotlinx.android.synthetic.main.activity_primary.*

class PrimaryActivity : AppCompatActivity(), OnSongClickListener {
    private var nowPlaying = false
    private lateinit var currentSong: Song

    companion object {
        const val SAVE_SONG = "save_song"
        const val SAVE_NOW_PLAYING = "save_now_playing"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_primary)
        supportActionBar?.title = "All Songs"

        val songList = ArrayList(SongDataProvider.getAllSongs())
        if(savedInstanceState != null) {
            val savedSong = savedInstanceState.getParcelable<Song>(SAVE_SONG)
            if(savedSong != null) {
                currentSong = savedSong
            }
            nowPlaying = savedInstanceState.getBoolean(SAVE_NOW_PLAYING)
        } else {
            currentSong = songList[0]
        }

        initMiniPlayer(currentSong)

        val songListFragment = SongListFragment()
        val argBundle = Bundle().apply {
            putParcelableArrayList(ARG_SONG_LIST, songList)
        }
        songListFragment.arguments = argBundle

        if(supportFragmentManager.findFragmentByTag(SongListFragment.TAG) == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragContainer, songListFragment, SongListFragment.TAG)
                .commit()
        }

        llMiniPlayer.setOnClickListener {
            onMiniPlayerClick(currentSong)
        }

        btnShuffle.setOnClickListener {
            songListFragment.shuffleList()
        }

        supportFragmentManager.addOnBackStackChangedListener {
            if(supportFragmentManager.findFragmentByTag(NowPlayingFragment.TAG) != null) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.title = "Now Playing"
                llMiniPlayer.visibility = LinearLayout.GONE
                nowPlaying = true
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                supportActionBar?.title = "All Songs"
                llMiniPlayer.visibility = LinearLayout.VISIBLE
                nowPlaying = false
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putParcelable(SAVE_SONG, currentSong)
            putBoolean(SAVE_NOW_PLAYING, nowPlaying)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return super.onNavigateUp()
    }

    private fun initMiniPlayer(firstSong: Song) {
        onSongClick(firstSong)
    }

    private fun getNowPlayingFragment() = supportFragmentManager.findFragmentByTag(NowPlayingFragment.TAG) as? NowPlayingFragment

    private fun onMiniPlayerClick(song: Song) {
        var nowPlayingFragment = getNowPlayingFragment()

        if(nowPlayingFragment == null) {
            nowPlayingFragment = NowPlayingFragment()
            val argBundle = Bundle().apply {
                putParcelable(NowPlayingFragment.ARG_SONG, song)
            }
            nowPlayingFragment.arguments = argBundle

            supportFragmentManager
                .beginTransaction()
                .addToBackStack(NowPlayingFragment.TAG)
                .add(R.id.fragContainer, nowPlayingFragment, NowPlayingFragment.TAG)
                .commit()
        } else {
            nowPlayingFragment.updateSong(song)
        }


    }

    override fun onSongClick(song: Song) {
        currentSong = song
        tvMiniPlayerTitle.text = getString(R.string.miniPlayerTitle).format(song.title, song.artist)
    }
}


