package com.example.deber_02

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    var listCine = mutableListOf<BCine>()
    lateinit var adaptador: ArrayAdapter<BCine> // Declarar adaptador como propiedad de la clase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Base de datos sqlite
        CPBaseDeDatos.tablaCine = SqliteHelper(this)

        // para probar agregar datos quemados
        agregarDatosQuemados()

        // Obtener una lista de cines de la base de datos
        listCine = CPBaseDeDatos.tablaCine!!.obtenerTodosLosCines().toMutableList()


        //asociar la interfaz
        setContentView(R.layout.main_activity2)

        val btnMostrarCines = findViewById<Button>(R.id.btn_mostrar)
        btnMostrarCines
            .setOnClickListener {
                irActividad(ViewCine::class.java)
            }

        val botonCrearBDD = findViewById<Button>(R.id.btn_crear_bdd)
        botonCrearBDD
            .setOnClickListener {
                val nombre = findViewById<EditText>(R.id.input_nombre)
                val ubicacion = findViewById<EditText>(R.id.input_ubicacion)
                val capacidadSalas = findViewById<EditText>(R.id.input_capacidadSalas)
                val clasificacion = findViewById<EditText>(R.id.input_clasificacion)
                val respuesta = CPBaseDeDatos
                    .tablaCine!!.agregarCine(

                        nombre.text.toString(),
                        ubicacion.text.toString(),
                        capacidadSalas.text.toString().toIntOrNull() ?: 0,
                        clasificacion.text.toString()

                    )

            }

        // Logica Buscar Cine
        val botonBuscarBDD = findViewById<Button>(R.id.btn_buscar_bdd)
        botonBuscarBDD
            .setOnClickListener {
                // Obtener componentes visuales
                val idCine = findViewById<EditText>(R.id.input_id)
                val nombre = findViewById<EditText>(R.id.input_nombre)
                val ubicacion = findViewById<EditText>(R.id.input_ubicacion)
                val capacidadSalas = findViewById<EditText>(R.id.input_capacidadSalas)
                val clasificacion = findViewById<EditText>(R.id.input_clasificacion)

                // Busqueda en la BDD Sqlite
                val cine = CPBaseDeDatos.tablaCine!!
                    .consultarCinePorID(
                        idCine.text.toString().toInt()
                    )

                // Setear los valores en los comp visuales
                if(cine.idCine == 0){
                    //mostrarSnackbar("Cine no encontrado")
                }

                idCine.setText(cine.idCine.toString())
                nombre.setText(cine.nombre)
                ubicacion.setText(cine.ubicacion)
                capacidadSalas.setText(cine.capacidadSalas.toString())
                clasificacion.setText(cine.clasificacion)
                //mostrarSnackbar("Cine encontrado")

            }

        val botonActualizarBDD = findViewById<Button>(R.id.btn_actualizar_bdd)
        botonActualizarBDD
            .setOnClickListener {
                // Obtener componentes visuales
                val idCine = findViewById<EditText>(R.id.input_id)
                val nombre = findViewById<EditText>(R.id.input_nombre)
                val ubicacion = findViewById<EditText>(R.id.input_ubicacion)
                val capacidadSalas = findViewById<EditText>(R.id.input_capacidadSalas)
                val clasificacion = findViewById<EditText>(R.id.input_clasificacion)
                val respuesta = CPBaseDeDatos.tablaCine!!.actualizarCineFormulario(

                    idCine.text.toString().toInt(),
                    nombre.text.toString(),
                    ubicacion.text.toString(),
                    capacidadSalas.text.toString().toIntOrNull() ?: 0,
                    clasificacion.text.toString()
                )

            }


        val botonEliminarBDD = findViewById<Button>(
            R.id.btn_eliminar_bdd
        )
        botonEliminarBDD.setOnClickListener {
            val id = findViewById<EditText>(R.id.input_id)
            val respuesta = CPBaseDeDatos
                .tablaCine!!
                .eliminarCineFormulario(
                    id.text.toString().toInt()
                )

        }

        val btnPeliculas = findViewById<Button>(R.id.btn_peliculas)
        btnPeliculas.setOnClickListener {

            botonBuscarBDD

            val idCine = findViewById<EditText>(R.id.input_id).text.toString().toInt()

            if (idCine!=0){

                // Crear un Intent para iniciar la nueva actividad
                val intent = Intent(this, CrudPelicula::class.java)

                // Agregar el ID del cine como extra al Intent
                intent.putExtra("ID_CINE", idCine)

                // Iniciar la nueva actividad con el Intent
                startActivity(intent)

            }


        }




        /*
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
        */
/*
        // Botón para agregar un nuevo cine
        val btnBorrarTodo: Button = findViewById(R.id.btnBorrarTodo)
        btnBorrarTodo.setOnClickListener {
            CPBaseDeDatos.tablaCine!!.borrarTodosLosDatos()
        }
*/
    }


    fun agregarDatosQuemados() {

        // Obtener una lista de cines de la base de datos
        listCine = CPBaseDeDatos.tablaCine!!.obtenerTodosLosCines().toMutableList()

        if (listCine != null && listCine.isNotEmpty()) {
            // La lista no es null y tiene al menos un elemento
            // Realiza las operaciones necesarias con listCine
        } else {
            // La lista es null o está vacía
            // Realiza alguna acción apropiada, como mostrar un mensaje de que no hay datos disponibles
            aggCine("Cinemar", "Calderon", 5, "A")
            aggCine("Multicines", "Condado", 2, "B")
            aggCine("Cinext", "La colon", 10, "A")

            aggPelicula("Piratas", "2024", 45, "T", 1)
            aggPelicula("Rapidos", "2025", 60, "F", 1)

            aggPelicula("Piratas", "2024", 45, "T", 2)

            aggPelicula("Piratas", "2024", 45, "T", 3)
            aggPelicula("Rapidos", "2025", 45, "T", 3)

        }

    }

    fun aggCine( nombre: String , ubicacion: String, capacidadSalas: Int, clasificacion: String){

        val respuesta = CPBaseDeDatos
            .tablaCine!!.agregarCine(
                nombre,
                ubicacion,
                capacidadSalas,
                clasificacion
            )

        if (respuesta) {
            //mostrarSnackbar("Se agrego el cine")
        }


    }

    fun aggPelicula(nombre: String , fechaEstreno: String, duracion: Int, es3d: String, idCine: Int){

        val respuesta = CPBaseDeDatos
            .tablaCine!!.agregarPelicula(
                nombre,
                fechaEstreno,
                duracion,
                es3d,
                idCine
            )

        if (respuesta) {
            //mostrarSnackbar("Se agrego el cine")
        }

    }







/*
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


            // Agregar cine al sqlit
            aggCine(nombre, ubicacion, capacidad, clasificacion)
            //adaptador.notifyDataSetChanged()

        }

        builder.setNegativeButton("Cancelar") { _, _ ->
            // Cancelar, no hacer nada
        }

        builder.create().show()
    }


    fun aggDatos(nuevosCines: List<BCine>) {
        adaptador.clear()
        adaptador.addAll(nuevosCines)
        adaptador.notifyDataSetChanged()
    }
*/

    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }



}
