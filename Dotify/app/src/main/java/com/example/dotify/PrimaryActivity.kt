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
    private lateinit var currentSong: Song

    companion object {
        const val SAVE_SONG = "save_song"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_primary)

        val songList = ArrayList(SongDataProvider.getAllSongs())
        if(savedInstanceState != null) {
            val savedSong = savedInstanceState.getParcelable<Song>(SAVE_SONG)
            if(savedSong != null) {
                currentSong = savedSong
            }
        } else {
            currentSong = songList[0]
        }

        if(supportFragmentManager.findFragmentByTag(SongListFragment.TAG) == null) {
            supportActionBar?.title = "All Songs"

            val songListFragment = SongListFragment()
            val argBundle = Bundle().apply {
                putParcelableArrayList(ARG_SONG_LIST, songList)
            }
            songListFragment.arguments = argBundle

            supportFragmentManager
                .beginTransaction()
                .addToBackStack(SongListFragment.TAG)
                .add(R.id.fragContainer, songListFragment, SongListFragment.TAG)
                .commit()
        }

        initMiniPlayer(currentSong)
        changeLayout()

        llMiniPlayer.setOnClickListener {
            onMiniPlayerClick(currentSong)
        }

        btnShuffle.setOnClickListener {
            val listFragment = supportFragmentManager.findFragmentByTag(SongListFragment.TAG) as SongListFragment
            listFragment.shuffleList()
        }

        supportFragmentManager.addOnBackStackChangedListener {
            changeLayout()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putParcelable(SAVE_SONG, currentSong)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return super.onNavigateUp()
    }

    private fun changeLayout() {
        if(supportFragmentManager.findFragmentByTag(NowPlayingFragment.TAG) != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "Now Playing"
            llMiniPlayer.visibility = LinearLayout.GONE
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            supportActionBar?.title = "All Songs"
            llMiniPlayer.visibility = LinearLayout.VISIBLE
        }
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