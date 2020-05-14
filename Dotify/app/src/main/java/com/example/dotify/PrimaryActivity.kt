package com.example.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import com.ericchee.songdataprovider.Song
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_primary.*

class PrimaryActivity : AppCompatActivity(), OnSongClickListener {
    private lateinit var dotifyApplication: DotifyApplication

    companion object {
        const val SAVE_SONG = "save_song"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_primary)



        val json = """
            {
              "title": "Dotify",
              "numOfSongs": 47,
              "songs": [
                {
                  "id": "1588825540885InTheEnd_LinkinPark",
                  "title": "In The End",
                  "artist": "Linkin Park",
                  "durationMillis": 193790,
                  "smallImageURL": "https://picsum.photos/seed/InTheEnd/50",
                  "largeImageURL": "https://picsum.photos/seed/InTheEnd/256"
                },
                {
                  "id": "1588825540953MaskDefinitelyOn_Future",
                  "title": "Mask Definitely On",
                  "artist": "Future",
                  "durationMillis": 92949,
                  "smallImageURL": "https://picsum.photos/seed/MaskDefinitelyOn/50",
                  "largeImageURL": "https://picsum.photos/seed/MaskDefinitelyOn/256"
                }
              ]
            }
        """.trimIndent()

        val gson = Gson()
        val testList = gson.fromJson(json, SongList::class.java)

        Log.i("TESTING", testList.toString())





        dotifyApplication = this.applicationContext as DotifyApplication

        if(supportFragmentManager.findFragmentByTag(SongListFragment.TAG) == null) {
            supportActionBar?.title = "All Songs"

            val songListFragment = SongListFragment()
            supportFragmentManager
                .beginTransaction()
                .addToBackStack(SongListFragment.TAG)
                .add(R.id.fragContainer, songListFragment, SongListFragment.TAG)
                .commit()
        }

        initMiniPlayer(dotifyApplication.currentSong)
        changeLayout()

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

    private fun initMiniPlayer(firstSong: Song) {
        onSongClick(firstSong)
    }

    private fun getNowPlayingFragment() = supportFragmentManager.findFragmentByTag(NowPlayingFragment.TAG) as? NowPlayingFragment

    private fun onMiniPlayerClick(song: Song) {
        var nowPlayingFragment = getNowPlayingFragment()

        if(nowPlayingFragment == null) {
            nowPlayingFragment = NowPlayingFragment()
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
        dotifyApplication.currentSong = song
        tvMiniPlayerTitle.text = getString(R.string.miniPlayerTitle).format(song.title, song.artist)
    }
}