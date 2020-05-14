package com.example.dotify

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.dotify.data.Song
import com.example.dotify.data.SongList
import com.google.gson.Gson

class ApiManager(context: Context) {
    private val requestQueue = Volley.newRequestQueue(context)
    private val url = "https://raw.githubusercontent.com/echeeUW/codesnippets/master/musiclibrary.json"

    companion object {
        const val FAILED_REQUEST = "failed request"
    }

    fun getSongs(onSongListReady: (SongList) -> Unit) {
        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener { response ->
                val gson = Gson()
                val songList = gson.fromJson(response, SongList::class.java)

                onSongListReady(songList)
        },
        Response.ErrorListener { onSongListReady(
            SongList(
                FAILED_REQUEST,
                0,
                listOf<Song>(Song("", FAILED_REQUEST, "", 0, "", ""))
            )
        )})

        requestQueue.add(stringRequest)
    }
}