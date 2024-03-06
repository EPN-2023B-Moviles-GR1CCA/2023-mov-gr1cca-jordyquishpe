package com.example.deber_02

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteHelper (
    contexto: Context?, // THIS
) : SQLiteOpenHelper(
    contexto,
    "Cine-Pelicula", // nombre BDD
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaCine =
            """
               CREATE TABLE CINE(
               idCine INTEGER PRIMARY KEY AUTOINCREMENT,
               nombre VARCHAR(50),
               ubicacion VARCHAR(50),
               capacidadSalas INTERGER,
               clasificacion VARCHAR(50)
               )
               
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaCine)

        val scriptSQLCrearTablaPelicula =
            """
       CREATE TABLE PELICULA(
       idPelicula INTEGER PRIMARY KEY AUTOINCREMENT,
       nombre VARCHAR(50),
       fechaEstreno VARCHAR(50),
       duracion INTEGER,
       es3d VARCHAR(50),
       idCine INTEGER, -- Esta es la columna de clave foránea
       FOREIGN KEY (idCine) REFERENCES CINE(idCine) -- Esto define la relación de clave foránea
       )
    """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaPelicula)


    }

    override fun onUpgrade(p0: SQLiteDatabase?,
                           p1: Int,
                           p2: Int)
    {

    }

    fun obtenerTodosLosCines(): List<BCine> {

        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM CINE
        """.trimIndent()

        //val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura, // Consulta
            null, // Parametros
        )

        val existeCine = resultadoConsultaLectura.moveToFirst()
        val cineEncontrado = BCine(0, "" , "", 0, "")
        val listaCines = arrayListOf<BCine>()

        if (existeCine) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1)
                val ubicacion = resultadoConsultaLectura.getString(2)
                val capacidadSalas = resultadoConsultaLectura.getInt(3)
                val clasificacion = resultadoConsultaLectura.getString(4)
                val cineEncontrado = BCine(id, nombre, ubicacion, capacidadSalas, clasificacion) // Crear un nuevo objeto BCine en cada iteración
                listaCines.add(cineEncontrado)
            } while (resultadoConsultaLectura.moveToNext())
        }

        baseDatosLectura.close()
        return listaCines


    }



    fun agregarCine(
        nombre: String,
        ubicacion: String,
        capacidadSalas: Int,
        clasificacion: String

    ): Boolean {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("ubicacion", ubicacion)
        valoresAGuardar.put("capacidadSalas", capacidadSalas)
        valoresAGuardar.put("clasificacion", clasificacion)
        val resultadoGuardar = basedatosEscritura
            .insert(
                "CINE", // Nombre tabla
                null,
                valoresAGuardar // valores
            )
        basedatosEscritura.close()
        return if (resultadoGuardar.toInt() == -1) false else true
    }

    fun consultarCinePorID(id: Int): BCine{
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM CINE WHERE idCine = ?
        """.trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura, // Consulta
            parametrosConsultaLectura, // Parametros
        )
        val existeCine = resultadoConsultaLectura.moveToFirst()
        val cineEncontrado = BCine(0, "" , "", 0, "")
        // val arreglo = arrayListOf<BEntrenador>()
        if(existeCine){
            do{

                val id = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1)
                val ubicacion = resultadoConsultaLectura.getString(2)
                val capacidadSalas = resultadoConsultaLectura.getInt(3)
                val clasificacion = resultadoConsultaLectura.getString(4)

                if(id != null){
                    // val usuarioEncontrado = BEntrenador(0, "" , "")
                    cineEncontrado.idCine = id
                    cineEncontrado.nombre = nombre
                    cineEncontrado.ubicacion = ubicacion
                    cineEncontrado.capacidadSalas = capacidadSalas
                    cineEncontrado.clasificacion = clasificacion
                    // arreglo.add(usuarioEncontrado)
                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return cineEncontrado //arreglo
    }

    fun actualizarCineFormulario(

        id: Int,
        nombre: String,
        ubicacion: String,
        capacidadSalas: Int,
        clasificacion: String
    ):Boolean{
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("ubicacion", ubicacion)
        valoresAActualizar.put("capacidadSalas", capacidadSalas)
        valoresAActualizar.put("clasificacion", clasificacion)
        // where ID = ?
        val parametrosConsultaActualizar = arrayOf( id.toString() )
        val resultadoActualizacion = conexionEscritura
            .update(
                "CINE", // Nombre tabla
                valoresAActualizar, // Valores
                "idCine=?", // Consulta Where
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if(resultadoActualizacion == -1) false else true
    }

    fun eliminarCineFormulario(id:Int):Boolean{
        val conexionEscritura = writableDatabase
        // where ID = ?
        val parametrosConsultaDelete = arrayOf( id.toString() )
        // [1]
        // [1,2,3], "id=? and a=? and b=?", "id=1 and a=2 and b=3"
        // ? = 1
        // "id=?" => "id=1"
        val resultadoEliminacion = conexionEscritura
            .delete(
                "CINE", // Nombre tabla
                "idCine=?", // Consulta Where
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return if(resultadoEliminacion == -1) false else true
    }

    fun borrarTodosLosDatos() {
        val baseDatosEscritura = writableDatabase
        baseDatosEscritura.delete("Cines", null, null) // Elimina todos los registros de la tabla Cines
        //baseDatosEscritura.delete("Peliculas", null, null) // Elimina todos los registros de la tabla Peliculas
        // Puedes agregar más tablas si es necesario
        baseDatosEscritura.close()
    }

    fun obtenerTodasLasPeliculas(idCine: Int): List<BPelicula> {

        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM PELICULA WHERE idCine = ?
        """.trimIndent()


        val parametrosConsultaLectura = arrayOf(idCine.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura, // Consulta
            parametrosConsultaLectura, // Parametros
        )

        val existePelicula = resultadoConsultaLectura.moveToFirst()
        val peliculaEncontrada = BPelicula(0, "" , "", 0, "", 0)
        val listaPeliculas = arrayListOf<BPelicula>()

        if (existePelicula) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1)
                val fechaEstreno = resultadoConsultaLectura.getString(2)
                val duracion = resultadoConsultaLectura.getInt(3)
                val es3d = resultadoConsultaLectura.getString(4)
                val idCine = resultadoConsultaLectura.getInt(5)
                val peliculaEncontrada = BPelicula(id, nombre, fechaEstreno, duracion, es3d, idCine) // Crear un nuevo objeto BCine en cada iteración
                listaPeliculas.add(peliculaEncontrada)
            } while (resultadoConsultaLectura.moveToNext())
        }

        baseDatosLectura.close()
        return listaPeliculas


    }



    fun agregarPelicula(
        nombre: String,
        fechaEstreno: String,
        duracion: Int,
        es3d: String,
        idCine: Int

    ): Boolean {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("fechaEstreno", fechaEstreno)
        valoresAGuardar.put("duracion", duracion)
        valoresAGuardar.put("es3d", es3d)
        valoresAGuardar.put("idCine", idCine)
        val resultadoGuardar = basedatosEscritura
            .insert(
                "PELICULA", // Nombre tabla
                null,
                valoresAGuardar // valores
            )
        basedatosEscritura.close()
        return if (resultadoGuardar.toInt() == -1) false else true
    }


    fun consultarCPeliculaPorID(id: Int): BPelicula{
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM PELICULA WHERE idPelicula = ?
        """.trimIndent()
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura, // Consulta
            parametrosConsultaLectura, // Parametros
        )
        val existePelicula = resultadoConsultaLectura.moveToFirst()
        val peliculaEncontrado = BPelicula(0, "" , "", 0, "", 0)
        // val arreglo = arrayListOf<BEntrenador>()
        if(existePelicula){
            do{

                val id = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1)
                val fechaEstreno = resultadoConsultaLectura.getString(2)
                val duracion = resultadoConsultaLectura.getInt(3)
                val es3d = resultadoConsultaLectura.getString(4)
                val idCine = resultadoConsultaLectura.getInt(5)


                if(id != null){
                    // val usuarioEncontrado = BEntrenador(0, "" , "")
                    peliculaEncontrado.idPelicula = id
                    peliculaEncontrado.nombre = nombre
                    peliculaEncontrado.fechaEstreno = fechaEstreno
                    peliculaEncontrado.duracion = duracion
                    peliculaEncontrado.es3D = es3d
                    peliculaEncontrado.idCine = idCine
                    // arreglo.add(usuarioEncontrado)
                }
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return peliculaEncontrado //arreglo
    }


    fun actualizarPeliculaFormulario(

        id: Int,
        nombre: String,
        fechaEstreno: String,
        duracion: Int,
        es3d: String

    ):Boolean{
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("fechaEstreno", fechaEstreno)
        valoresAActualizar.put("duracio", duracion)
        valoresAActualizar.put("es3d", es3d)
        // where ID = ?
        val parametrosConsultaActualizar = arrayOf( id.toString() )
        val resultadoActualizacion = conexionEscritura
            .update(
                "PELICULA", // Nombre tabla
                valoresAActualizar, // Valores
                "idPelicula=?", // Consulta Where
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if(resultadoActualizacion == -1) false else true
    }

    fun eliminarPeliculaFormulario(id:Int):Boolean{
        val conexionEscritura = writableDatabase
        // where ID = ?
        val parametrosConsultaDelete = arrayOf( id.toString() )
        // [1]
        // [1,2,3], "id=? and a=? and b=?", "id=1 and a=2 and b=3"
        // ? = 1
        // "id=?" => "id=1"
        val resultadoEliminacion = conexionEscritura
            .delete(
                "PELICULA", // Nombre tabla
                "idPelicula=?", // Consulta Where
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return if(resultadoEliminacion == -1) false else true
    }




}