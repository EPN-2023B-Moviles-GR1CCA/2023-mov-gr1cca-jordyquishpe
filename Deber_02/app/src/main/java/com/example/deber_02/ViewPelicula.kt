package com.example.deber_02

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class ViewPelicula : AppCompatActivity() {

    var listPelicula = mutableListOf<BPelicula>()
    lateinit var adaptador: ArrayAdapter<BPelicula> // Declarar adaptador como propiedad de la clase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pelicula)

        val idCine = intent.getIntExtra("ID_CINE", 0)

        // Obtener una lista de peliculas de la base de datos
        listPelicula = CPBaseDeDatos.tablaCine!!.obtenerTodasLasPeliculas(idCine).toMutableList()

        // Mostrar el arreglo en el ListView
        val lstView = findViewById<ListView>(R.id.listView_pelicula)

        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listPelicula// Mapear los cines a sus nombres
        )

        lstView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        val btnAtrasPelicula = findViewById<Button>(R.id.btn_atraspelicula)
        btnAtrasPelicula
            .setOnClickListener {
                // Crear un Intent para iniciar la nueva actividad
                val intent = Intent(this, CrudPelicula::class.java)
                // Agregar el ID del cine como extra al Intent
                intent.putExtra("ID_CINE", idCine)
                // Iniciar la nueva actividad con el Intent
                startActivity(intent)

            }



    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}