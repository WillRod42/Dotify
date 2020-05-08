package com.example.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import com.example.dotify.SongListFragment.Companion.ARG_SONG_LIST
import kotlinx.android.synthetic.main.activity_primary.*

class PrimaryActivity : AppCompatActivity(), OnSongClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_primary)

        //val nowPlayingFragment

        val songList = ArrayList(SongDataProvider.getAllSongs())
        val songListFragment = SongListFragment()
        val argBundle = Bundle().apply {
            putParcelableArrayList(ARG_SONG_LIST, songList)
        }
        songListFragment.arguments = argBundle
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragContainer, songListFragment)
            .commit()
    }

    private fun getNowPlayingFragment() = supportFragmentManager.findFragmentByTag(NowPlayingFragment.TAG) as? NowPlayingFragment

    override fun onSongClick(song: Song) {
        tvMiniPlayerTitle.text = getString(R.string.miniPlayerTitle).format(song.title, song.artist)
    }
}

//var nowPlayingFragment = getNowPlayingFragment()
//
//if(nowPlayingFragment == null) {
//    nowPlayingFragment = NowPlayingFragment()
//    val argBundle = Bundle().apply {
//        putParcelable(NowPlayingFragment.ARG_SONG, song)
//    }
//    nowPlayingFragment.arguments = argBundle
//} else {
//    nowPlayingFragment.updateSong(song)
//}
