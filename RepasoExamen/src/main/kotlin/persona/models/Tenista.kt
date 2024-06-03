package persona.models

import java.time.LocalDate

class Tenista(
    id:Int,
    nombre:String,
    fechaNacimiento:LocalDate,
    pais: String,
    val ranking: Int,
    val ganancias: Double
): Persona(id,nombre,fechaNacimiento,pais), Entrenar, JugarPartidos {

    override fun entrenar() {
        println("El tenista $nombre está entrenando")
    }

    override fun jugarPartidos() {
        println("El tenista $nombre está jugando un partido")
    }
}