package com.example.deber_02

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class ViewCine : AppCompatActivity() {

    var listCine = mutableListOf<BCine>()
    lateinit var adaptador: ArrayAdapter<BCine> // Declarar adaptador como propiedad de la clase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val idCine = intent.getIntExtra("ID_CINE", 0)

        //asociar la interfaz
        setContentView(R.layout.activity_view_cine)

        // Obtener una lista de cines de la base de datos
        listCine = CPBaseDeDatos.tablaCine!!.obtenerTodosLosCines().toMutableList()

        // Mostrar el arreglo en el ListView
        val lstView = findViewById<ListView>(R.id.listView_Cine)

        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listCine// Mapear los cines a sus nombres
        )

        lstView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        val btnAtras = findViewById<Button>(R.id.btn_atras)
        btnAtras
            .setOnClickListener {
                irActividad(MainActivity::class.java)
            }

    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}