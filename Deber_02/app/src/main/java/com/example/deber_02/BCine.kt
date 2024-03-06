package com.example.deber_02

class BCine(

    var idCine: Int?,
    var nombre: String,
    var ubicacion: String,
    var capacidadSalas: Int,
    var clasificacion: String
) {

    override fun toString(): String {
        return "'$nombre' --'$ubicacion'-- capacidadSalas=$capacidadSalas -- clasificacion='$clasificacion')"
    }

}