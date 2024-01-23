package Entidades

data class Pelicula(
    var nombre: String,
    var fechaEstreno: String,
    var duracion: Int,
    var rating: Double,
    var es3D: Boolean
)