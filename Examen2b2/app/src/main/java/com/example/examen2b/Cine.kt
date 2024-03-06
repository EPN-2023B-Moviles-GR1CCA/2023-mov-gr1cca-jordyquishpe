package com.example.examen2b


import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class Cine (
    var idCine: Int?,
    var nombre: String?,
    var ubicacion: String?,
    var capacidadSalas: String?,
    var clasificacion: String?,
    val context: Context?
){

    override fun toString(): String {
        return "'$nombre' --'$ubicacion'-- capacidadSalas=$capacidadSalas -- clasificacion='$clasificacion')"
    }

    companion object {
        private const val TAG = "Cine"
    }
    fun setidCine(idCine: Int) {
        this.idCine = idCine
    }

    fun setnombre(nombre: String?) {
        this.nombre = nombre
    }

    fun setubicacion(ubicacion: String?) {
        this.ubicacion = ubicacion
    }

    fun setcapacidadSalas(capacidadSalas: String?) {
        this.capacidadSalas = capacidadSalas
    }


    fun setclasificacion(clasificacion: String?) {
        this.clasificacion = clasificacion
    }

    // Métodos get

    fun getidCine(): Int? {
        return idCine
    }

    fun getnombre(): String? {
        return nombre
    }

    fun getubicacion(): String? {
        return ubicacion
    }

    fun getcapacidadSalas(): String? {
        return capacidadSalas
    }

    fun getclasificacion(): String? {
        return clasificacion
    }



    // Función para mostrar todos los cines en Firestore
    fun showCines(callback: (ArrayList<Cine>) -> Unit) {
        val firestore = FirebaseFirestore.getInstance()
        val listaCines = ArrayList<Cine>()

        firestore.collection("cines")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val cine = Cine(

                        document["idCine"] as Int?,
                        document["nombre"] as String?,
                        document["ubicacion"] as String?,
                        document["capacidadSalas"] as String?,
                        document["clasificacion"] as String?,
                        context
                    )
                    listaCines.add(cine)
                }
                callback(listaCines)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error al obtener cines", exception)
            }
    }


    // Función para insertar un cine en Firestore
    fun insertCine() {
        val firestore = FirebaseFirestore.getInstance()
        val cine = hashMapOf(
            "idCine" to idCine,
            "nombre" to nombre,
            "ubicacion" to ubicacion,
            "capacidadSalas" to capacidadSalas,
            "clasificacion" to clasificacion
        )

        firestore.collection("cines")
            .add(cine)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "Cine agregado con ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error al agregar cine", e)
            }
    }

    fun getCineById(id: Int, callback: (Cine?) -> Unit) {
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("cines")
            .whereEqualTo("idCine", id)
            .get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty) {
                    val document = result.documents[0] // Suponiendo que solo hay un cine con ese ID
                    val cine = Cine(
                        document["idCine"] as Int?,
                        document["nombre"] as String?,
                        document["ubicacion"] as String?,
                        document["capacidadSalas"] as String,
                        document["clasificacion"] as String,
                        context
                    )
                    callback(cine)
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error al obtener cine por ID", exception)
                callback(null)
            }
    }

    // Función para actualizar un estudiante en Firestore
    fun updateCine(callback: (Boolean) -> Unit) {
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("cines")
            .whereEqualTo("idCine", idCine)
            .get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty) {
                    for (document in result) {
                        val data = hashMapOf(
                            "idCine" to idCine,
                            "nombre" to nombre,
                            "ubicacion" to ubicacion,
                            "capacidadSalas" to capacidadSalas,
                            "clasificacion" to clasificacion
                        )

                        firestore.collection("cines")
                            .document(document.id)
                            .update(data as Map<String, Any>)
                            .addOnSuccessListener {
                                callback(true)
                            }
                            .addOnFailureListener { exception ->
                                Log.w(TAG, "Error al actualizar cine", exception)
                                callback(false)
                            }
                        return@addOnSuccessListener
                    }
                } else {
                    callback(false)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error al actualizar cine", exception)
                callback(false)
            }
    }

    // Función para eliminar un estudiante en Firestore
    fun deleteCine(callback: (Boolean) -> Unit){
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("cines")
            .whereEqualTo("idCine", idCine)
            .get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty) {
                    for (document in result) {
                        firestore.collection("cine")
                            .document(document.id)
                            .delete()
                            .addOnSuccessListener {
                                callback(true)
                            }
                            .addOnFailureListener { exception ->
                                Log.w(TAG, "Error al eliminar cine", exception)
                                callback(false)
                            }
                        return@addOnSuccessListener
                    }
                } else {
                    callback(false)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error al eliminar cine", exception)
                callback(false)
            }
    }


}