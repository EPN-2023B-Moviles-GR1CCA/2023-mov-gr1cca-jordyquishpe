package com.example.esound.adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.esound.Album
import com.example.esound.Cancion
import com.example.esound.R

class CancionesAdapter (private val CancionList: List<Cancion>): RecyclerView.Adapter<CancionesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CancionesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CancionesViewHolder(layoutInflater.inflate(R.layout.item_canciones, parent, false))
    }

    override fun getItemCount(): Int {
        //devolver el tamano
        return CancionList.size
    }

    override fun onBindViewHolder(holder: CancionesViewHolder, position: Int) {
        val item = CancionList[position]
        holder.render(item)
    }
}