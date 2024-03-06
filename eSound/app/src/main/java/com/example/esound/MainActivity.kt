package com.example.esound

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.esound.adapter.AlbumAdapter
import com.example.esound.databinding.MainActivityBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AlbumProvider.albumList
        setContentView(R.layout.main_activity)

        initRecyclerView()

    }

    fun initRecyclerView(){

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewAlbums)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = AlbumAdapter(AlbumProvider.albumList)

        val recyclerView2 = findViewById<RecyclerView>(R.id.recyclerViewAlbums2)
        recyclerView2.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView2.adapter = AlbumAdapter(AlbumProvider.albumList)

    }

}
