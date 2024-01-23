package Entidades

data class Cine(
    var nombre: String,
    var ubicacion: String,
    var capacidadSalas: Int,
    var clasificacion: String,
    var peliculas: MutableList<Pelicula> = mutableListOf()
)
