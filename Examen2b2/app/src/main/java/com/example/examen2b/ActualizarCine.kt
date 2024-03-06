package com.example.examen2b

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class ActualizarCine : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_cine)

        val idPadre = BCine.idSeleccionado
        var cine = Cine(null, "", "", "", "", this)
        cine.getCineById(idPadre) { cineResult ->
            if (cineResult != null) {
                var idCine = findViewById<EditText>(R.id.tv_updCodifoEs)
                idCine.setText(cineResult.getidCine()?.toString() ?: "")

                var nombre = findViewById<EditText>(R.id.tv_updNombreCine)
                nombre.setText(cineResult.getnombre() ?: "")

                var ubicacion = findViewById<EditText>(R.id.tv_updUbicacion)
                ubicacion.setText(cineResult.getubicacion() ?: "")

                var capacidadSalas = findViewById<EditText>(R.id.tv_updcapacidadSalas)
                capacidadSalas.setText(cineResult.getcapacidadSalas())

                var clasificacion = findViewById<EditText>(R.id.tv_updclasificacion)
                clasificacion.setText(cineResult.getclasificacion())
            } else {
                //Toast.makeText(this, "No se pudo obtener el cine", Toast.LENGTH_SHORT).show()
            }
        }

        val btnActualizarCine = findViewById<Button>(R.id.btn_updCine)
        btnActualizarCine.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.tv_updNombreCine).text.toString()
            val ubicacion = findViewById<EditText>(R.id.tv_updUbicacion).text.toString()
            val capacidadSalas = findViewById<EditText>(R.id.tv_updcapacidadSalas).text.toString()
            val clasificacion = findViewById<EditText>(R.id.tv_updclasificacion).text.toString()

            cine.setnombre(nombre)
            cine.setubicacion(ubicacion)
            cine.setcapacidadSalas(capacidadSalas)
            cine.setclasificacion(clasificacion)

            cine.updateCine { isSuccess ->
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

        val nombre = findViewById<EditText>(R.id.tv_updNombreCine)
        val ubicacion = findViewById<EditText>(R.id.tv_updUbicacion)
        val capacidadSalas = findViewById<EditText>(R.id.tv_updcapacidadSalas)
        val clasificacion = findViewById<EditText>(R.id.tv_updclasificacion)

        nombre.setText("")
        ubicacion.setText("")
        capacidadSalas.setText("")
        clasificacion.setText("")
    }
}