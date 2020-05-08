package com.example.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import com.example.dotify.SongListFragment.Companion.ARG_SONG_LIST
import kotlinx.android.synthetic.main.activity_primary.*

class PrimaryActivity : AppCompatActivity(), OnSongClickListener {
    private lateinit var currentSong: Song

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_primary)

        supportActionBar?.title = "All Songs"

        val songList = ArrayList(SongDataProvider.getAllSongs())
        initMiniPlayer(songList[0])

        val songListFragment = SongListFragment()
        val argBundle = Bundle().apply {
            putParcelableArrayList(ARG_SONG_LIST, songList)
        }
        songListFragment.arguments = argBundle
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragContainer, songListFragment)
            .commit()

        llMiniPlayer.setOnClickListener {
            onMiniPlayerClick(currentSong)
        }

        btnShuffle.setOnClickListener {
            songListFragment.shuffleList()
        }

        supportFragmentManager.addOnBackStackChangedListener {
            val hasBackStack = supportFragmentManager.backStackEntryCount > 0
            if(hasBackStack) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.title = "Now Playing"
                llMiniPlayer.visibility = LinearLayout.GONE
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                supportActionBar?.title = "All Songs"
                llMiniPlayer.visibility = LinearLayout.VISIBLE
            }
        }
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
                .add(R.id.fragContainer, nowPlayingFragment, NowPlayingFragment.TAG)
                .addToBackStack(NowPlayingFragment.TAG)
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


