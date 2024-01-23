import Entidades.Cine
import Entidades.Pelicula
import CRUD.*


val crudCine = CRUDCine()

fun main() {
    //val manejadorCines = ManejadorArchivo("//D:/Javier/Universidad/AplicacionesMoviles/GitHub/2023B/2023B-mov-gr1cca-quishpe-carvajal-jordy-javier/Deber_01_JordyQuishpe/Deber01_JordyQuishpe/src/main/kotlin/cines.txt", Cine::class.java)


    crudCine.cargarDesdeArchivo("//D:/Javier/Universidad/AplicacionesMoviles/GitHub/2023B/2023B-mov-gr1cca-quishpe-carvajal-jordy-javier/Deber_01_JordyQuishpe/Deber01_JordyQuishpe/src/main/kotlin/cines.json")

    var opcion: Int

    while (true) {
        println("***** Menú Principal *****")
        println("1. Agregar Cine")
        println("2. Modificar Cine")
        println("3. Eliminar Cine")
        println("4. Ver todos los Cines")
        println("5. Salir")
        print("Seleccione una opción: ")
        opcion = readLine()?.toIntOrNull() ?: 0

        when (opcion) {
            1 -> {
                // Agregar Cine
                val nuevoCine = crearCine()
                crudCine.agregarCine(nuevoCine)

            }
            2 -> {
                // Modificar Cine
                print("Ingrese el nombre del cine a modificar: ")
                val nombreCine = readLine().orEmpty()
                val cineSeleccionado = crudCine.obtenerCine(nombreCine)

                if (cineSeleccionado != null) {
                    println("1. Modificar Datos del Cine")
                    println("2. Modificar Películas del Cine")
                    print("Seleccione una opción: ")

                    when (readLine()?.toIntOrNull()) {
                        1 -> {
                            // Modificar Datos del Cine
                            var nuevoCine: Cine = crearCine()
                            crudCine.modificarCine(nombreCine, nuevoCine)
                        }
                        2 -> {
                            // Modificar Películas del Cine
                            modificarPeliculasCine(cineSeleccionado)

                        }
                        else -> println("Opción no válida.")
                    }
                } else {
                    println("Cine no encontrado.")
                }
            }
            3 -> {
                // Eliminar cine
                print("Ingrese el nombre del cine a eliminar: ")
                val nombreCine = readLine().orEmpty()
                val cineAEliminar = crudCine.obtenerCine(nombreCine)

                if (cineAEliminar != null) {

                    crudCine.eliminarCine(nombreCine)
                    println("Cine eliminado.")

                } else {
                    println("Cine no encontrado.")
                }


            }
            4 -> {
                // Ver todos los Cines
                println("***** Lista de Cines *****")
                crudCine.mostrarCines();
            }
            5 -> {
                // Salir
                crudCine.guardarEnArchivo("//D:/Javier/Universidad/AplicacionesMoviles/GitHub/2023B/2023B-mov-gr1cca-quishpe-carvajal-jordy-javier/Deber_01_JordyQuishpe/Deber01_JordyQuishpe/src/main/kotlin/cines.json")
                println("Saliendo del programa. ¡Hasta luego!")
                return
            }
            else -> println("Opción no válida. Inténtelo de nuevo.")
        }
    }
}

fun crearCine(): Cine {
    println("***** Agregar Cine *****")
    print("Nombre del Cine: ")
    val nombre = readLine().toString()

    print("Ubicación: ")
    val ubicacion = readLine().toString()

    print("Capacidad de Salas: ")
    val capacidadSalas = readLine()?.toIntOrNull() ?: 0

    print("Clasificación: ")
    val clasificacion = readLine().toString()

    return Cine(nombre, ubicacion, capacidadSalas, clasificacion)
}



fun modificarPeliculasCine(cine: Cine) {
    while (true) {
        println("***** Modificar Películas del Cine *****")
        println("1. Agregar Película")
        println("2. Modificar Película")
        println("3. Eliminar Película")
        println("4. Ver todas las Películas")
        println("5. Volver al Menú Principal")
        print("Seleccione una opción: ")

        when (readLine()?.toIntOrNull()) {
            1 -> {
                // Agregar Película
                var nuevaPelicula: Pelicula= crearPelicula()
                crudCine.agregarPeliculaCine(cine, nuevaPelicula)
            }

            2 -> {


                // Modificar Película
                println("Ingrese el nombre de la película a modificar:")
                val nombrePeliculaModificar = readLine().toString()

                // Buscar la película por nombre
                val peliculaSeleccionada = cine.peliculas.find { it.nombre == nombrePeliculaModificar }

                if (peliculaSeleccionada != null) {

                    // Llamar a la función para modificar la película
                    crudCine.modificarPeliculaCine(peliculaSeleccionada)
                    println("Película modificada con éxito.")
                } else {
                    println("Película no encontrada en el cine.")
                }

            }
            3 -> {
                //Eliminar pelicula
                println("Ingrese el nombre de la película a eliminar:")
                val nombrePeliculaEliminar = readLine().toString()

                crudCine.eliminarPeliculaCine(cine, nombrePeliculaEliminar)

            }
            4 -> {
                // Ver todas las Películas del Cine
                println("***** Lista de Películas del Cine ${cine.nombre} *****")
                cine.peliculas.forEach { pelicula ->
                    println("Película: ${pelicula.nombre}, Fecha estreno: ${pelicula.fechaEstreno}, Duración: ${pelicula.duracion} minutos, Rating: ${pelicula.rating}, es3D: ${pelicula.es3D}" )
                }
                println()
            }
            5 -> {
                // Volver al Menú Principal
                return
            }
            else -> println("Opción no válida. Inténtelo de nuevo.")
        }
    }
}

fun crearPelicula(): Pelicula {
    println("***** Agregar Película *****")
    print("Nombre de la Película: ")
    val nombre = readLine().toString()

    print("Fecha de Estreno: ")
    val fechaEstreno = readLine().toString()

    print("Duración en minutos: ")
    val duracion = readLine()?.toIntOrNull() ?: 0

    print("Rating: ")
    val rating = readLine()?.toDoubleOrNull() ?: 0.0

    print("¿Es 3D? (true/false): ")
    val es3D = readLine()?.toBoolean() ?: false

    return Pelicula(nombre, fechaEstreno, duracion, rating, es3D)
}



