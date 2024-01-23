package CRUD

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.File

import Entidades.Cine
import Entidades.Pelicula

class CRUDCine {
    private val listaCines: MutableList<Cine> = mutableListOf()

    fun agregarCine(cine: Cine) {
        listaCines.add(cine)
    }

    fun obtenerCine(nombre: String): Cine? {
        return listaCines.find { it.nombre == nombre }
    }

    fun modificarCine(nombre: String, nuevoCine: Cine) {
        val indice = listaCines.indexOfFirst { it.nombre == nombre }
        if (indice != -1) {
            listaCines[indice] = nuevoCine
        }
    }

    fun eliminarCine(nombre: String) {
        listaCines.removeIf { it.nombre == nombre }
    }

    fun mostrarCines() {
        for (cine in listaCines) {
            println(cine)
        }
    }



    fun agregarPeliculaCine(cine: Cine, nuevaPelicula: Pelicula) {
        cine.peliculas.add(nuevaPelicula)
        println("Película agregada al cine con éxito.")
    }

    fun modificarPeliculaCine(pelicula: Pelicula) {
        println("***** Modificar Datos de la Película *****")
        print("Nuevo Nombre de la Película (Enter para mantener el mismo): ")
        val nuevoNombre = readLine()?.takeIf { it.isNotBlank() } ?: pelicula.nombre

        print("Nueva Fecha de Estreno (Enter para mantener la misma): ")
        val nuevaFechaEstreno = readLine()?.takeIf { it.isNotBlank() } ?: pelicula.fechaEstreno

        print("Nueva Duración en minutos (Enter para mantener la misma): ")
        val nuevaDuracion = readLine()?.toIntOrNull() ?: pelicula.duracion

        print("Nuevo Rating (Enter para mantener el mismo): ")
        val nuevoRating = readLine()?.toDoubleOrNull() ?: pelicula.rating

        print("¿Es 3D? (Enter para mantener el mismo): ")
        val nuevoEs3D = readLine()?.toBoolean() ?: pelicula.es3D

        // Actualizar los datos de la película
        pelicula.nombre = nuevoNombre
        pelicula.fechaEstreno = nuevaFechaEstreno
        pelicula.duracion = nuevaDuracion
        pelicula.rating = nuevoRating
        pelicula.es3D = nuevoEs3D

        println("Datos de la película modificados con éxito.")
    }


    fun eliminarPeliculaCine(cine: Cine, nombrePeliculaEliminar: String) {
        val peliculaAEliminar = cine.peliculas.find { it.nombre == nombrePeliculaEliminar }

        if (peliculaAEliminar != null) {
            cine.peliculas.remove(peliculaAEliminar)
            println("Película eliminada con éxito.")
        } else {
            println("Película no encontrada.")
        }
    }

    fun guardarEnArchivo(nombreArchivo: String) {
        val gson: Gson = GsonBuilder().setPrettyPrinting().create()
        val json: String = gson.toJson(listaCines)

        File(nombreArchivo).writeText(json)
    }

    fun cargarDesdeArchivo(nombreArchivo: String) {
        if (File(nombreArchivo).exists()) {
            val json: String = File(nombreArchivo).readText()
            val cinesDesdeArchivo: List<Cine> = Gson().fromJson(json, Array<Cine>::class.java).toList()
            listaCines.clear()
            listaCines.addAll(cinesDesdeArchivo)
        }
    }
}
