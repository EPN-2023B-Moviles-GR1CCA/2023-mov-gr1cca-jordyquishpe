package com.example.examen2b

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class ActualizarPelicula : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_pelicula)

        val idPadre = VerPelicula.idSeleccionadopelicula
        var pelicula = Pelicula(null, "", "", "", "", this)
        pelicula.getPeliculaById(idPadre) { peliculaResult ->
            if (peliculaResult != null) {
                var idCine = findViewById<EditText>(R.id.tv_updCodifoEs)
                idCine.setText(peliculaResult.getidPelicula()?.toString() ?: "")

                var nombre = findViewById<EditText>(R.id.tv_updNombreCine)
                nombre.setText(peliculaResult.getnombre() ?: "")

                var fechaEstreno = findViewById<EditText>(R.id.tv_updFechaEstreno)
                fechaEstreno.setText(peliculaResult.getfechaEstreno() ?: "")

                var duracion = findViewById<EditText>(R.id.tv_updDuracion)
                duracion.setText(peliculaResult.getduracion())

                var es3d = findViewById<EditText>(R.id.tv_updEs3d)
                es3d.setText(peliculaResult.getes3d())
            } else {
                Toast.makeText(this, "No se pudo obtener la pelicula", Toast.LENGTH_SHORT).show()
            }
        }

        val btnActualizarPelicula = findViewById<Button>(R.id.btn_actualizarPelicula)
        btnActualizarPelicula.setOnClickListener {

            val nombre = findViewById<EditText>(R.id.tv_updNombre).text.toString()
            val fechaEstreno = findViewById<EditText>(R.id.tv_updFechaEstreno).text.toString()
            val duracion = findViewById<EditText>(R.id.tv_updDuracion).text.toString()
            val es3d = findViewById<EditText>(R.id.tv_updEs3d).text.toString()

            pelicula.setnombre(nombre)
            pelicula.setfechaEstreno(fechaEstreno)
            pelicula.setduracion(duracion)
            pelicula.setes3d(es3d)

            pelicula.updatePelicula { isSuccess ->
                if (isSuccess) {
                    Toast.makeText(this, "Registro actualizado", Toast.LENGTH_SHORT).show()
                    cleanEditText()
                    val intent = Intent(this, BCine::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Error al actualizar el registro", Toast.LENGTH_SHORT).show()
                }
            }
        }





    }

    private fun cleanEditText() {

        val nombre = findViewById<EditText>(R.id.tv_updNombre)
        val fechaEstreno = findViewById<EditText>(R.id.tv_updFechaEstreno)
        val duracion = findViewById<EditText>(R.id.tv_updDuracion)
        val es3d = findViewById<EditText>(R.id.tv_updEs3d)

        nombre.setText("")
        fechaEstreno.setText("")
        duracion.setText("")
        es3d.setText("")
    }
}