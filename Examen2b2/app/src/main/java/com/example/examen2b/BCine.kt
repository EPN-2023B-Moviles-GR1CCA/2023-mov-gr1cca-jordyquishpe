package com.example.examen2b

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog


class BCine : AppCompatActivity() {

    companion object{
        var idSeleccionado = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bcine)
        showListViewCine()

        val nombre = findViewById<EditText>(R.id.editTextText_NombreCine)
        nombre.requestFocus()
        val ubicacion = findViewById<EditText>(R.id.editTextText_Ubicacion)
        val capacidadSalas = findViewById<EditText>(R.id.editTex_CapacidadSala)
        val clasificacion = findViewById<EditText>(R.id.editTextText_Clasificacion)

        val btncrearCine = findViewById<Button>(R.id.btnCrearCine)
        btncrearCine.setOnClickListener {
            val cine = Cine(
                null,
                nombre.text.toString(),
                ubicacion.text.toString(),
                capacidadSalas.text.toString(),
                clasificacion.text.toString(),
                this
            )
            val idInsertado = cine.insertCine()

            if(idInsertado != null){
                Toast.makeText(this,"Registro Guardado", Toast.LENGTH_LONG).show()
                cleanEditText()
                showListViewCine()
            } else {
                Toast.makeText(this,"Error al guardar el registro", Toast.LENGTH_LONG).show()
            }
        }


    }


    private fun showListViewCine() {
        val cine = Cine(null, "", "", "", "", this)


        cine.showCines { listaCines ->
            val listView = findViewById<ListView>(R.id.lvView_Cine)
            val adaptador = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                listaCines
            )
            listView.adapter = adaptador
            registerForContextMenu(listView)
        }
    }

    private fun cleanEditText(){
        val nombre = findViewById<EditText>(R.id.editTextText_NombreCine)
        val ubicacion = findViewById<EditText>(R.id.editTextText_Ubicacion)
        val capacidadSalas = findViewById<EditText>(R.id.editTex_CapacidadSala)
        val clasificacion = findViewById<EditText>(R.id.editTextText_Clasificacion)

        nombre.text.clear()
        ubicacion.text.clear()
        capacidadSalas.text.clear()
        clasificacion.text.clear()

        nombre.requestFocus()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_cine, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        idSeleccionado = info.position
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_editarcine -> {
                irActividad(ActualizarCine::class.java)
                true
            }
            R.id.mi_eliminarcine -> {
                abrirDialogo()
                true
            }
            R.id.mi_verpeliculas -> {
                irActividad(VerPelicula::class.java)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun abrirDialogo() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Desea eliminar el cine?")

        builder.setPositiveButton("SI") { dialog, which ->
            if (idSeleccionado >= 0) {
                val cine = Cine(null, "", "", "", "", this)
                cine.deleteCine { resultado ->
                    if (resultado) {
                        // Registro eliminado correctamente
                        Toast.makeText(this, "REGISTRO ELIMINADO", Toast.LENGTH_LONG).show()
                        runOnUiThread {
                            showListViewCine()
                        }
                    } else {
                        // Error al eliminar el registro
                        Toast.makeText(this, "ERROR AL ELIMINAR REGISTRO", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(this, "Selección no válida", Toast.LENGTH_LONG).show()
            }
        }

        builder.setNegativeButton("NO") { dialog, which ->
            Toast.makeText(this, "Operación cancelada", Toast.LENGTH_LONG).show()
        }

        val dialogo = builder.create()
        dialogo.show()
    }


    //Ir a la actividad
    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }





}