package com.example.dotify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dotify.data.Song
import com.squareup.picasso.Picasso

class SongListAdapter(initialListOfSongs: List<Song>): RecyclerView.Adapter<SongListAdapter.SongViewHolder>() {

    private var listOfSongs = initialListOfSongs.toList()
    var onSongClickListener: ((song: Song) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val itemViewFromLayout = LayoutInflater.from(parent.context).inflate(R.layout.song, parent, false)
        return SongViewHolder(itemViewFromLayout)
    }

    override fun getItemCount(): Int = listOfSongs.size

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val songTitle = listOfSongs[position]
        holder.bind(songTitle)
    }

    fun change(newSongs: List<Song>) {
        val callback = SongDiffCallback(listOfSongs, newSongs)
        val diffResult = DiffUtil.calculateDiff(callback)
        diffResult.dispatchUpdatesTo(this)

        // We update the list
        listOfSongs = newSongs
    }

    inner class SongViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tvSongListTitle by lazy { itemView.findViewById<TextView>(R.id.tvSongListTitle) }
        private val tvArtistListName by lazy { itemView.findViewById<TextView>(R.id.tvArtistListName) }
        private val ivSong by lazy { itemView.findViewById<ImageView>(R.id.ivSong) }

        fun bind(song: Song) {
            tvSongListTitle.text = song.title
            tvArtistListName.text = song.artist
            ivSong.contentDescription = song.title
            Picasso.get().load(song.largeImageURL).into(ivSong)

            itemView.setOnClickListener {
                onSongClickListener?.invoke(song)
            }
        }
    }
}