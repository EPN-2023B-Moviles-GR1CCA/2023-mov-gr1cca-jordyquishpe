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

class VerPelicula : AppCompatActivity() {

    companion object{
        var idSeleccionadopelicula = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_pelicula)

        showListViewPelicula()

        val nombre = findViewById<EditText>(R.id.editTextText_nombre_pelicula)
        nombre.requestFocus()
        val fechaEstreno = findViewById<EditText>(R.id.editTextText_FechaEstreno)
        val duracion = findViewById<EditText>(R.id.editTextText_Duracion)
        val es3d = findViewById<EditText>(R.id.editTextText_es3d)

        val btncrearCine = findViewById<Button>(R.id.btn_crear_pelicula)
        btncrearCine.setOnClickListener {
            val pelicula = Pelicula(
                null,
                nombre.text.toString(),
                fechaEstreno.text.toString(),
                duracion.text.toString(),
                es3d.text.toString(),
                this
            )
            val idInsertado = pelicula.insertPelicula()

            if(idInsertado != null){
                Toast.makeText(this,"Registro Guardado", Toast.LENGTH_LONG).show()
                cleanEditText()
                showListViewPelicula()
            } else {
                Toast.makeText(this,"Error al guardar el registro", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun showListViewPelicula() {
        val pelicula = Pelicula(null, "", "", "", "", this)


        pelicula.showPeliculas { listaPeliculas ->
            val listView = findViewById<ListView>(R.id.lvView_Pelicula)
            val adaptador = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                listaPeliculas
            )
            listView.adapter = adaptador
            registerForContextMenu(listView)
        }
    }

    private fun cleanEditText(){

        val nombre = findViewById<EditText>(R.id.editTextText_nombre_pelicula)
        val fechaEstreno = findViewById<EditText>(R.id.editTextText_FechaEstreno)
        val duracion = findViewById<EditText>(R.id.editTextText_Duracion)
        val es3d = findViewById<EditText>(R.id.editTextText_es3d)

        nombre.text.clear()
        fechaEstreno.text.clear()
        duracion.text.clear()
        es3d.text.clear()

        nombre.requestFocus()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_pelicula, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        BCine.idSeleccionado = info.position

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_editarPelicula -> {
                irActividad(ActualizarPelicula::class.java)
                true
            }
            R.id.mi_eliminarPelicula -> {
                abrirDialogo()
                true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    private fun abrirDialogo() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Desea eliminar la pelicula?")

        builder.setPositiveButton("SI") { dialog, which ->
            if (VerPelicula.idSeleccionadopelicula >= 0) {
                val pelicula = Pelicula(null, "", "", "", "", this)
                pelicula.deletePelicula { resultado ->
                    if (resultado) {
                        // Registro eliminado correctamente
                        Toast.makeText(this, "REGISTRO ELIMINADO", Toast.LENGTH_LONG).show()
                        runOnUiThread {
                            showListViewPelicula()
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