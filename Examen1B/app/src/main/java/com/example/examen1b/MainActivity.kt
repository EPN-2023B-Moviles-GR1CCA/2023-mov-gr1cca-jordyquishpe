package com.example.examen1b

import BPelicula
import android.app.Activity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.EditText



class MainActivity : AppCompatActivity() {

    val listCine = mutableListOf<BCine>()
    lateinit var adaptador: ArrayAdapter<BCine> // Declarar adaptador como propiedad de la clase
    // Declarar el código de solicitud como una constante
    private val REQUEST_CODE = 123 // Puedes usar cualquier número que desees
    private var nombreCineSeleccionado: String? = null
    // Declarar una variable global para almacenar la posición
    //private var posicionClicLista: Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //asociar la interfaz
        setContentView(R.layout.main_activity)
        // para empezar a mostrar los datos


        // para probar agregar datos quemados
        agregarDatosQuemados(listCine)

        // Mostrar el arreglo en el ListView
        val lstView = findViewById<ListView>(R.id.listViewCine)

        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listCine// Mapear los cines a sus nombres
        )

        lstView.adapter = adaptador
        adaptador.notifyDataSetChanged()


        // Botón para agregar un nuevo cine
        val btnAggCine: Button = findViewById(R.id.btnAgregarCine)
        btnAggCine.setOnClickListener {
            mostrarDialogoAgregarCine()

        }


        lstView.setOnItemClickListener { parent, view, position, id ->
            // Asignar la posición a la variable global
            //posicionClicLista = position
            // Manejar el clic en un elemento de la lista
            //val cineSeleccionado = listCine[position]
            // Aquí puedes realizar acciones específicas para el elemento seleccionado si lo deseas
            // ...

            // Mostrar el menú contextual
            registerForContextMenu(lstView)
            openContextMenu(lstView)
            unregisterForContextMenu(lstView)
        }

    }




    fun agregarDatosQuemados(listCine: MutableList<BCine>) {
        // Crear películas
        val pelicula1 = BPelicula("El exorcista", "2022-01-01", 120, 4.5, false)
        val pelicula2 = BPelicula("Rapidos y furiosos", "2022-02-01", 110, 4.0, true)

        // Crear cine con películas
        val cine1 = BCine("Cinext", "Calderon", 5, "A", mutableListOf(pelicula1, pelicula2))

        // Agregar cine a la lista
        listCine.add(cine1)

        // Crear más cines con películas si es necesario
        val cine2 = BCine("Cinemar", "Brasil", 3, "B", mutableListOf(pelicula2))
        val cine3 = BCine("CNTT", "Floresta", 4, "C", mutableListOf(pelicula1, pelicula2))

        // Agregar más cines a la lista
        listCine.add(cine2)
        listCine.add(cine3)
    }



    fun mostrarDialogoAgregarCine() {

        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle("Agregar Nuevo Cine")

        // Inflar el diseño del diálogo personalizado
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.dialog_agregar_cine, null)

        // Obtener referencias a los EditText en el diseño del diálogo
        val etNombre = view.findViewById<EditText>(R.id.etNombreCine)
        val etUbicacion = view.findViewById<EditText>(R.id.etUbicacionCine)
        val etCapacidad = view.findViewById<EditText>(R.id.etCapacidadCine)
        val etClasificacion = view.findViewById<EditText>(R.id.etClasificacionCine)

        builder.setView(view)

        // Configurar botones del diálogo
        builder.setPositiveButton("Agregar") { _, _ ->

            // Obtener los valores ingresados por el usuario
            val nombre = etNombre.text.toString()
            val ubicacion = etUbicacion.text.toString()
            val capacidad = etCapacidad.text.toString().toIntOrNull() ?: 0
            val clasificacion = etClasificacion.text.toString()

            // Crear una nueva instancia de BCine con los datos ingresados
            val nuevoCine = BCine(nombre, ubicacion, capacidad, clasificacion)
            // Agregar el nuevo cine a la lista
            listCine.add(nuevoCine)
            adaptador.notifyDataSetChanged()

        }

        builder.setNegativeButton("Cancelar") { _, _ ->
            // Cancelar, no hacer nada
        }

        builder.create().show()
    }


    // Métodos para la creación y manejo del menú contextual
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        if (v?.id == R.id.listViewCine) {
            val inflater: MenuInflater = menuInflater
            inflater.inflate(R.menu.menu, menu)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val position = info.position
        val cineSeleccionado = listCine[position]

        when (item.itemId) {
            R.id.menu_modificar -> {
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setTitle("Modificar Cine")

                // Inflar el diseño del diálogo personalizado
                val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val view = inflater.inflate(R.layout.dialog_agregar_cine, null)

                // Obtener referencias a los EditText en el diseño del diálogo
                val etNuevoNombre = view.findViewById<EditText>(R.id.etNombreCine)
                val etNuevaUbicacion = view.findViewById<EditText>(R.id.etUbicacionCine)
                val etNuevaCapacidad = view.findViewById<EditText>(R.id.etCapacidadCine)
                val etNuevaClasificacion = view.findViewById<EditText>(R.id.etClasificacionCine)

                // Configurar los EditText con los datos actuales del cine seleccionado
                etNuevoNombre.setText(cineSeleccionado.nombre)
                etNuevaUbicacion.setText(cineSeleccionado.ubicacion)
                etNuevaCapacidad.setText(cineSeleccionado.capacidadSalas.toString())
                etNuevaClasificacion.setText(cineSeleccionado.clasificacion)

                builder.setView(view)

                // Configurar botones del diálogo
                builder.setPositiveButton("Guardar") { _, _ ->
                    // Obtener los nuevos valores ingresados por el usuario
                    val nuevoNombre = etNuevoNombre.text.toString()
                    val nuevaUbicacion = etNuevaUbicacion.text.toString()
                    val nuevaCapacidad = etNuevaCapacidad.text.toString().toIntOrNull() ?: 0
                    val nuevaClasificacion = etNuevaClasificacion.text.toString()

                    // Modificar el cine seleccionado con los nuevos datos
                    cineSeleccionado.nombre = nuevoNombre
                    cineSeleccionado.ubicacion = nuevaUbicacion
                    cineSeleccionado.capacidadSalas = nuevaCapacidad
                    cineSeleccionado.clasificacion = nuevaClasificacion

                    // Notificar al adaptador que los datos han cambiado
                    adaptador.notifyDataSetChanged()
                }

                builder.setNegativeButton("Cancelar") { _, _ ->
                    // Cancelar, no hacer nada
                }

                builder.create().show()

                return true
            }
            R.id.menu_eliminar -> {
                // Lógica para eliminar el elemento seleccionado
                // Puedes abrir un cuadro de diálogo de confirmación y luego eliminar
                listCine.removeAt(position)
                adaptador.notifyDataSetChanged()
                return true
            }
            R.id.menu_ver -> {

                // Lógica para ver las películas asociadas al cine seleccionado
                abrirActividadPeliculasAsociadas(cineSeleccionado)
                return true
            }
            else -> return super.onContextItemSelected(item)
        }
    }


    private fun abrirActividadPeliculasAsociadas(cine: BCine) {
        nombreCineSeleccionado = cine.nombre // Almacena el nombre del cine seleccionado
        val intent = Intent(this@MainActivity, InPelicula::class.java)
        intent.putExtra("listaPeliculas", cine.peliculas.toTypedArray())
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Obtener la lista de películas devuelta desde la segunda actividad
            val listaPeliculasModificada = data?.getSerializableExtra("listaPeliculasModificada") as? Array<BPelicula>


        }
    }




}


