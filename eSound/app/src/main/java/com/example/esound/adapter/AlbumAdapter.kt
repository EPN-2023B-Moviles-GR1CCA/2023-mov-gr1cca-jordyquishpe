package com.example.esound.adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.esound.Album
import com.example.esound.R

class AlbumAdapter (private val AlbumList: List<Album>): RecyclerView.Adapter<AlbumViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AlbumViewHolder(layoutInflater.inflate(R.layout.item_album, parent, false))
    }

    override fun getItemCount(): Int {
        //devolver el tamano
        return AlbumList.size
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {

        val item = AlbumList[position]
        holder.render(item)
    }
}