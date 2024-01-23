package com.example.examen1b

import BPelicula

class BCine(

    var nombre: String,
    var ubicacion: String,
    var capacidadSalas: Int,
    var clasificacion: String,
    var peliculas: MutableList<BPelicula> = mutableListOf()

) {

    override fun toString(): String {
        return "'$nombre' --'$ubicacion'-- capacidadSalas=$capacidadSalas -- clasificacion='$clasificacion')"
    }

}