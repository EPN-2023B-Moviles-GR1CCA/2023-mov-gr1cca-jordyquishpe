package com.example.examen1b

import BPelicula
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView

class InPelicula : AppCompatActivity() , View.OnCreateContextMenuListener{

    var listaPelicula = mutableListOf<BPelicula>()
    lateinit var adaptador: ArrayAdapter<BPelicula> // Declarar adaptador como propiedad de la clase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_in_pelicula)


        // Recibir el nombre del cine desde la actividad anterior
        //val nombreCine = intent.getStringExtra("nombreCine")

        // Recibir la lista de películas desde la actividad anterior
        listaPelicula = (intent.getSerializableExtra("listaPeliculas") as Array<BPelicula>).toMutableList()

        // Configurar el adaptador y mostrar las películas en un ListView
        val lstView = findViewById<ListView>(R.id.listViewPelicula)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listaPelicula
        )
        lstView.adapter = adaptador
        adaptador.notifyDataSetChanged()


        val btnAggCine: Button = findViewById(R.id.btnAgregarPelicula)
        btnAggCine.setOnClickListener {
            mostrarDialogoAgregarPelicula()

        }

        registerForContextMenu(lstView)

        lstView.setOnItemClickListener { parent, view, position, id ->
            // Manejar el clic en un elemento de la lista
            val peliculaseleccionada = listaPelicula[position]
            // Aquí puedes realizar acciones específicas para el elemento seleccionado si lo deseas
            // ...

            // Registrar la vista para el menú contextual sin abrirlo inmediatamente
            //registerForContextMenu(lstView)
            // Mostrar el menú contextual
            //registerForContextMenu(lstView)
            openContextMenu(lstView)
            //unregisterForContextMenu(lstView)

        }

        //registerForContextMenu(lstView)


        // Botón pararegresar
        val btnRegresar: Button = findViewById(R.id.btn_regresar)
        btnRegresar.setOnClickListener {

            val intent = Intent()
            intent.putExtra("listaPeliculasModificada", listaPelicula.toTypedArray())
            setResult(Activity.RESULT_OK, intent)
            finish()
        }


    }



    fun mostrarDialogoAgregarPelicula() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Agregar Nueva Pelicula")

        // Inflar el diseño del diálogo personalizado
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.dialog_agregar_pelicula, null)

        // Obtener referencias a los EditText en el diseño del diálogo
        val etNombrePelicula = view.findViewById<EditText>(R.id.etNombrePelicula)
        val etFechaEstreno = view.findViewById<EditText>(R.id.etFechaEstreno)
        val etDuracion = view.findViewById<EditText>(R.id.etDuracion)
        val etRating = view.findViewById<EditText>(R.id.etRating)
        val etEs3D = view.findViewById<EditText>(R.id.etEs3D)

        builder.setView(view)

        // Configurar botones del diálogo
        builder.setPositiveButton("Agregar") { _, _ ->

            // Obtener los valores ingresados por el usuario
            val nombrePelicula = etNombrePelicula.text.toString()
            val fechaEstreno = etFechaEstreno.text.toString()
            val duracion = etDuracion.text.toString().toIntOrNull() ?: 0
            val rating = etRating.text.toString().toDoubleOrNull() ?: 0.0
            val es3D = etEs3D.text.toString().toBoolean()


            // Crear una nueva instancia de BPelicula con los datos ingresados
            val nuevaPelicula = BPelicula(nombrePelicula, fechaEstreno, duracion, rating, es3D) // Aquí debes proporcionar el nombre del cine asociado
            // Agregar la nueva película a la lista
            listaPelicula.add(nuevaPelicula)
            adaptador.notifyDataSetChanged()


        }

        builder.setNegativeButton("Cancelar") { _, _ ->
            // Cancelar, no hacer nada
        }

        builder.create().show()
    }

    // Métodos para la creación y manejo del menú contextual
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        if (v?.id == R.id.listViewPelicula) {
            val inflater: MenuInflater = menuInflater
            inflater.inflate(R.menu.menu_pelicula_contextual, menu)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val position = info.position
        val peliculaSeleccionada = listaPelicula[position]

        when (item.itemId) {
            R.id.menuModificar -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Modificar Película")

                // Inflar el diseño del diálogo personalizado
                val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val view = inflater.inflate(R.layout.dialog_agregar_pelicula, null)

                // Obtener referencias a los EditText en el diseño del diálogo
                val etNuevoNombre = view.findViewById<EditText>(R.id.etNombrePelicula)
                val etNuevaFechaEstreno = view.findViewById<EditText>(R.id.etFechaEstreno)
                val etNuevaDuracion = view.findViewById<EditText>(R.id.etDuracion)
                val etNuevoRating = view.findViewById<EditText>(R.id.etRating)
                val etNuevoEs3D = view.findViewById<EditText>(R.id.etEs3D)

                // Configurar los EditText con los datos actuales de la película seleccionada
                etNuevoNombre.setText(peliculaSeleccionada.nombre)
                etNuevaFechaEstreno.setText(peliculaSeleccionada.fechaEstreno)
                etNuevaDuracion.setText(peliculaSeleccionada.duracion.toString())
                etNuevoRating.setText(peliculaSeleccionada.rating.toString())
                etNuevoEs3D.setText(peliculaSeleccionada.es3D.toString())

                builder.setView(view)

                // Configurar botones del diálogo
                builder.setPositiveButton("Guardar") { _, _ ->
                    // Obtener los nuevos valores ingresados por el usuario
                    val nuevoNombre = etNuevoNombre.text.toString()
                    val nuevaFechaEstreno = etNuevaFechaEstreno.text.toString()
                    val nuevaDuracion = etNuevaDuracion.text.toString().toIntOrNull() ?: 0
                    val nuevoRating = etNuevoRating.text.toString().toDoubleOrNull() ?: 0.0
                    val nuevoEs3D = etNuevoEs3D.text.toString().toBoolean()

                    // Modificar la película seleccionada con los nuevos datos
                    peliculaSeleccionada.nombre = nuevoNombre
                    peliculaSeleccionada.fechaEstreno = nuevaFechaEstreno
                    peliculaSeleccionada.duracion = nuevaDuracion
                    peliculaSeleccionada.rating = nuevoRating
                    peliculaSeleccionada.es3D = nuevoEs3D

                    // Notificar al adaptador que los datos han cambiado
                    adaptador.notifyDataSetChanged()
                }

                builder.setNegativeButton("Cancelar") { _, _ ->
                    // Cancelar, no hacer nada
                }

                builder.create().show()

                return true
            }
            R.id.menuEliminar -> {
                // Lógica para eliminar el elemento seleccionado
                // Puedes abrir un cuadro de diálogo de confirmación y luego eliminar
                listaPelicula.removeAt(position)
                adaptador.notifyDataSetChanged()
                return true
            }
            else -> return super.onContextItemSelected(item)
        }
    }



}