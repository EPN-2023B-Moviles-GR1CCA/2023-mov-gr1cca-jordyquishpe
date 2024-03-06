package com.example.deber_02

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CrudPelicula : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_pelicula)

        val idCine = intent.getIntExtra("ID_CINE", 0)

        // Busqueda en la BDD Sqlite
        val cine = CPBaseDeDatos.tablaCine!!
                    .consultarCinePorID(
                        idCine
                    )

        val textoView = findViewById<TextView>(R.id.textView_cine)

        textoView.setText(cine.nombre)



        val btnMostrarPeliculas = findViewById<Button>(R.id.btn_mostrar_pelicula)
        btnMostrarPeliculas
            .setOnClickListener {


                    // Crear un Intent para iniciar la nueva actividad
                    val intent = Intent(this, ViewPelicula::class.java)

                    // Agregar el ID del cine como extra al Intent
                    intent.putExtra("ID_CINE", idCine)

                    // Iniciar la nueva actividad con el Intent
                    startActivity(intent)



                irActividad(ViewPelicula::class.java)
            }

        val botonCrearBDD = findViewById<Button>(R.id.btn_crear_pelicula)
        botonCrearBDD
            .setOnClickListener {
                val nombre = findViewById<EditText>(R.id.input_nombrepelicula)
                val fechaestreno = findViewById<EditText>(R.id.input_fechaestreno)
                val duracion = findViewById<EditText>(R.id.input_duracion)
                val es3d = findViewById<EditText>(R.id.input_es3d)
                val respuesta = CPBaseDeDatos

                    .tablaCine!!.agregarPelicula(

                        nombre.text.toString(),
                        fechaestreno.text.toString(),
                        duracion.text.toString().toIntOrNull() ?: 0,
                        es3d.text.toString(),
                        idCine
                    )

            }

        // Logica Buscar Cine
        val botonBuscarBDDPelicula = findViewById<Button>(R.id.btn_buscar_pelicula)
        botonBuscarBDDPelicula
            .setOnClickListener {
                // Obtener componentes visuales
                val idPelicula = findViewById<EditText>(R.id.input_idPelicula)
                val nombre = findViewById<EditText>(R.id.input_nombrepelicula)
                val fechaestreno = findViewById<EditText>(R.id.input_fechaestreno)
                val duracion = findViewById<EditText>(R.id.input_duracion)
                val es3d = findViewById<EditText>(R.id.input_es3d)

                // Busqueda en la BDD Sqlite
                val pelicula = CPBaseDeDatos.tablaCine!!
                    .consultarCPeliculaPorID(
                        idPelicula.text.toString().toInt()
                    )

                // Setear los valores en los comp visuales
                if(pelicula.idPelicula == 0){
                    //mostrarSnackbar("Cine no encontrado")
                }

                idPelicula.setText(pelicula.idPelicula.toString())
                nombre.setText(pelicula.nombre)
                fechaestreno.setText(pelicula.fechaEstreno)
                duracion.setText(pelicula.duracion.toString())
                es3d.setText(pelicula.es3D)


            }


        val botonActualizarBDDPelicula = findViewById<Button>(R.id.btn_actualizar)
        botonActualizarBDDPelicula
            .setOnClickListener {
                // Obtener componentes visuales
                val idPelicula = findViewById<EditText>(R.id.input_idPelicula)
                val nombre = findViewById<EditText>(R.id.input_nombrepelicula)
                val fechaestreno = findViewById<EditText>(R.id.input_fechaestreno)
                val duracion = findViewById<EditText>(R.id.input_duracion)
                val es3d = findViewById<EditText>(R.id.input_es3d)
                val respuesta = CPBaseDeDatos.tablaCine!!.actualizarPeliculaFormulario(

                    idPelicula.text.toString().toInt(),
                    nombre.text.toString(),
                    fechaestreno.text.toString(),
                    duracion.text.toString().toIntOrNull() ?: 0,
                    es3d.text.toString()
                )

            }


        val botonEliminarBDDPelicula = findViewById<Button>(
            R.id.btn_eliminar_pelicula
        )
        botonEliminarBDDPelicula.setOnClickListener {
            val id = findViewById<EditText>(R.id.input_id)
            val respuesta = CPBaseDeDatos
                .tablaCine!!
                .eliminarPeliculaFormulario(
                    id.text.toString().toInt()
                )

        }





        val btnRegresar = findViewById<Button>(R.id.btn_regresar)
        btnRegresar
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