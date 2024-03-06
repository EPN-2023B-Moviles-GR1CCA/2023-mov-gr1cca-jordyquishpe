package com.example.esound.adapter

import android.content.Intent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.esound.Album
import com.example.esound.Cancion
import com.example.esound.R
import com.example.esound.ViewCanciones
import com.example.esound.databinding.ItemAlbumBinding

class CancionesViewHolder (view: View): RecyclerView.ViewHolder(view){


    val titulo = view.findViewById<TextView>(R.id.textView4)

    val photo = view.findViewById<ImageView>(R.id.ivCancion)

    fun render(cancionModel: Cancion){

        titulo.text =  cancionModel.titulo


        Glide.with(photo.context).load(cancionModel.photo).into(photo)



    }



}