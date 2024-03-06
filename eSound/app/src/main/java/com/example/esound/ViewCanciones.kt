package com.example.esound

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.esound.databinding.MainActivityBinding

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.esound.adapter.AlbumAdapter
import com.example.esound.adapter.CancionesAdapter
import com.example.esound.databinding.ActivityViewCancionesBinding


class ViewCanciones : AppCompatActivity() {

    private lateinit var binding2: ActivityViewCancionesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_canciones)

        CancionesProvider.cancionList

        initRecyclerView()
    }

    fun initRecyclerView(){

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewCanciones)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CancionesAdapter(CancionesProvider.cancionList)

    }
}