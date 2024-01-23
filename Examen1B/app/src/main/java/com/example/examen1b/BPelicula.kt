import java.io.Serializable

class BPelicula(
    var nombre: String?,
    var fechaEstreno: String?,
    var duracion: Int?,
    var rating: Double?,
    var es3D: Boolean?,
) : Serializable {

    override fun toString(): String {
        return "'$nombre' --'$fechaEstreno'-- duración=$duracion -- rating='$rating'-- 3D='$es3D')"
    }
}