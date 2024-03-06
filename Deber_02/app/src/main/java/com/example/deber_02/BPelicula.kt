package com.example.deber_02

import java.io.Serializable

class BPelicula(
    var idPelicula: Int?,
    var nombre: String?,
    var fechaEstreno: String?,
    var duracion: Int?,
    var es3D: String?,
    var idCine: Int? // Clave foránea que hace referencia al cine al que pertenece la película
) : Serializable {

    override fun toString(): String {
        return "'$nombre' --'$fechaEstreno'-- duración=$duracion -- 3D='$es3D')"
    }
}