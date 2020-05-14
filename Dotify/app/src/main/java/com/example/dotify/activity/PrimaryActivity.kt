package com.example.dotify.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import com.example.dotify.*
import com.example.dotify.fragment.NowPlayingFragment
import com.example.dotify.fragment.OnSongClickListener
import com.example.dotify.fragment.SongListFragment
import kotlinx.android.synthetic.main.activity_primary.*
import com.example.dotify.data.Song

class PrimaryActivity : AppCompatActivity(),
    OnSongClickListener {
    private lateinit var dotifyApplication: DotifyApplication
    private lateinit var apiManager: ApiManager

    companion object {
        const val SAVE_SONG = "save_song"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_primary)
        supportActionBar?.title = "All Songs"

        dotifyApplication = this.applicationContext as DotifyApplication
        apiManager = dotifyApplication.apiManager

        fetchSongList()
        llMiniPlayer.setOnClickListener {
            onMiniPlayerClick(dotifyApplication.currentSong)
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
            putParcelable(SAVE_SONG, dotifyApplication.currentSong)
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

    private fun fetchSongList() {
        apiManager.getSongs { songList ->
            val songListFragment = getSongListFragment()
            if(songListFragment == null) {
                dotifyApplication.currentSong = songList.songs[0]
            }
            dotifyApplication.listOfSongs = songList.songs

            updateSongList()
        }
    }

    private fun updateSongList() {
        if(dotifyApplication.listOfSongs[0].title == ApiManager.FAILED_REQUEST) {
            //tvVolleyError.visibility = TextView.VISIBLE
            Toast.makeText(this, "Volley Request Error:\nUnable to retrieve song list", Toast.LENGTH_LONG).show()
        } else {
            var songListFragment = getSongListFragment()
            if(songListFragment == null) {
                songListFragment = SongListFragment()
                supportFragmentManager
                    .beginTransaction()
                    .addToBackStack(SongListFragment.TAG)
                    .add(
                        R.id.fragContainer, songListFragment,
                        SongListFragment.TAG
                    )
                    .commit()
            } else {
                songListFragment.updateList(dotifyApplication.listOfSongs)
            }

            initMiniPlayer(dotifyApplication.currentSong)
            changeLayout()
        }
    }

    private fun initMiniPlayer(firstSong: Song) {
        onSongClick(firstSong)
    }

    private fun getSongListFragment() = supportFragmentManager.findFragmentByTag(
        SongListFragment.TAG
    ) as? SongListFragment

    private fun getNowPlayingFragment() = supportFragmentManager.findFragmentByTag(
        NowPlayingFragment.TAG
    ) as? NowPlayingFragment

    private fun onMiniPlayerClick(song: Song) {
        var nowPlayingFragment = getNowPlayingFragment()

        if(nowPlayingFragment == null) {
            nowPlayingFragment = NowPlayingFragment()
            supportFragmentManager
                .beginTransaction()
                .addToBackStack(NowPlayingFragment.TAG)
                .add(
                    R.id.fragContainer, nowPlayingFragment,
                    NowPlayingFragment.TAG
                )
                .commit()
        } else {
            nowPlayingFragment.updateSong(song)
        }
    }

    override fun onSongClick(song: Song) {
        dotifyApplication.currentSong = song
        tvMiniPlayerTitle.text = getString(R.string.miniPlayerTitle).format(song.title, song.artist)
    }
}