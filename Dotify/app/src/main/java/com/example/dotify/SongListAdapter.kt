package com.example.dotify

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ericchee.songdataprovider.Song

class SongListAdapter(private val listOfSongs: List<Song>): RecyclerView.Adapter<SongListAdapter.SongViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val itemViewFromLayout = LayoutInflater.from(parent.context).inflate(R.layout.song, parent, false)
        return SongViewHolder(itemViewFromLayout)
    }

    override fun getItemCount(): Int = listOfSongs.size

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val songTitle = listOfSongs[position]
        holder.bind(songTitle)
    }

    class SongViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tvSongListTitle by lazy { itemView.findViewById<TextView>(R.id.tvSongListTitle) }
        private val tvArtistListName by lazy { itemView.findViewById<TextView>(R.id.tvArtistListName) }
        private val ivSong by lazy { itemView.findViewById<ImageView>(R.id.ivSong) }

        fun bind(song: Song) {
            tvSongListTitle.text = song.title
            tvArtistListName.text = song.artist
            ivSong.setImageResource(song.smallImageID)
            ivSong.contentDescription = song.title

            Log.i("songlistinfo", song.title)
        }
    }
}