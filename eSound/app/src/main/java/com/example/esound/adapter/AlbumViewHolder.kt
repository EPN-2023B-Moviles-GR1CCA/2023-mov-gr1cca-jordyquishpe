package com.example.esound.adapter

import android.content.Intent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.esound.Album
import com.example.esound.R
import com.example.esound.ViewCanciones
import com.example.esound.databinding.ItemAlbumBinding

class AlbumViewHolder (view: View): RecyclerView.ViewHolder(view){


    val titulo = view.findViewById<TextView>(R.id.tvTitulo)
    val cantante = view.findViewById<TextView>(R.id.tvCantante)
    val photo = view.findViewById<ImageView>(R.id.ivAlbum)

    fun render(albumModel: Album){

        titulo.text =  albumModel.titulo
        cantante.text =  albumModel.cantante

        Glide.with(photo.context).load(albumModel.photo).into(photo)



        itemView.setOnClickListener{

            //movermem a ootra actividad
            // Crear un Intent para iniciar la actividad ViewCanciones
            val intent = Intent(itemView.context, ViewCanciones::class.java)
            // Iniciar la nueva actividad
            itemView.context.startActivity(intent)

        }



    }


}