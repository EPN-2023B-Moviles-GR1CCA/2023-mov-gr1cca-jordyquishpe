package com.example.examen2b

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.persistentCacheSettings

class Pelicula (

    var idPelicula: Int?,
    var nombre: String?,
    var fechaEstreno: String?,
    var duracion: String?,
    var es3d: String?,
    val context: Context?

){
    override fun toString(): String {
        return "'$nombre' --'$fechaEstreno'-- duración=$duracion --  3D='$es3d')"
    }

    companion object {
        private const val TAG = "Pelicula"
    }
    fun setidPeliula(idPelicula: Int) {
        this.idPelicula = idPelicula
    }

    fun setnombre(nombre: String?) {
        this.nombre = nombre
    }

    fun setfechaEstreno(fechaEstreno: String?) {
        this.fechaEstreno = fechaEstreno
    }

    fun setduracion(duracion: String?) {
        this.duracion = duracion
    }


    fun setes3d(es3d: String?) {
        this.es3d = es3d
    }

    // Métodos get

    fun getidPelicula(): Int? {
        return idPelicula
    }

    fun getnombre(): String? {
        return nombre
    }

    fun getfechaEstreno(): String? {
        return fechaEstreno
    }

    fun getduracion(): String? {
        return duracion
    }

    fun getes3d(): String? {
        return es3d
    }


    // Función para mostrar todos los cines en Firestore
    fun showPeliculas(callback: (ArrayList<Pelicula>) -> Unit) {
        val firestore = FirebaseFirestore.getInstance()
        val listaPeliculas = ArrayList<Pelicula>()

        firestore.collection("peliculas")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val pelicula = Pelicula(

                        document["idPelicula"] as Int?,
                        document["nombre"] as String?,
                        document["fechaEstreno"] as String?,
                        document["duracion"] as String?,
                        document["es3d"] as String?,
                        context
                    )
                    listaPeliculas.add(pelicula)
                }
                callback(listaPeliculas)
            }
            .addOnFailureListener { exception ->
                Log.w(Pelicula.TAG, "Error al obtener peliculas", exception)
            }
    }

    // Función para insertar un cine en Firestore
    fun insertPelicula() {
        val firestore = FirebaseFirestore.getInstance()
        val pelicula = hashMapOf(
            "idPelicula" to idPelicula,
            "nombre" to nombre,
            "fechaEstreno" to fechaEstreno,
            "duracion" to duracion,
            "es3d" to es3d
        )

        firestore.collection("peliculas")
            .add(pelicula)
            .addOnSuccessListener { documentReference ->
                Log.d(Pelicula.TAG, "Pelicula agregado con ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(Pelicula.TAG, "Error al agregar cine", e)
            }
    }

    fun getPeliculaById(id: Int, callback: (Pelicula?) -> Unit) {
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("peliculas")
            .whereEqualTo("idPelicula", id)
            .get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty) {
                    val document = result.documents[0] // Suponiendo que solo hay un estudiante con ese ID
                    val pelicula = Pelicula(
                        document["idPelicula"] as Int?,
                        document["nombre"] as String?,
                        document["fechaEstreno"] as String?,
                        document["duracion"] as String,
                        document["es3d"] as String,
                        context
                    )
                    callback(pelicula)
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(Pelicula.TAG, "Error al obtener pelicula por ID", exception)
                callback(null)
            }
    }

    // Función para actualizar un estudiante en Firestore
    fun updatePelicula(callback: (Boolean) -> Unit) {
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("peliculas")
            .whereEqualTo("idPelicula", idPelicula)
            .get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty) {
                    for (document in result) {
                        val data = hashMapOf(
                            "idPelicula" to idPelicula,
                            "nombre" to nombre,
                            "fechaEstreno" to fechaEstreno,
                            "duracion" to duracion,
                            "es3d" to es3d
                        )

                        firestore.collection("peliculas")
                            .document(document.id)
                            .update(data as Map<String, Any>)
                            .addOnSuccessListener {
                                callback(true)
                            }
                            .addOnFailureListener { exception ->
                                Log.w(TAG, "Error al actualizar pelicula", exception)
                                callback(false)
                            }
                        return@addOnSuccessListener
                    }
                } else {
                    callback(false)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error al actualizar pelicula", exception)
                callback(false)
            }
    }

    // Función para eliminar un pelicula en Firestore
    fun deletePelicula(callback: (Boolean) -> Unit){
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("peliculas")
            .whereEqualTo("idPelicula", idPelicula)
            .get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty) {
                    for (document in result) {
                        firestore.collection("peliculas")
                            .document(document.id)
                            .delete()
                            .addOnSuccessListener {
                                callback(true)
                            }
                            .addOnFailureListener { exception ->
                                Log.w(Pelicula.TAG, "Error al eliminar pelicula", exception)
                                callback(false)
                            }
                        return@addOnSuccessListener
                    }
                } else {
                    callback(false)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(Pelicula.TAG, "Error al eliminar pelicula", exception)
                callback(false)
            }
    }


}