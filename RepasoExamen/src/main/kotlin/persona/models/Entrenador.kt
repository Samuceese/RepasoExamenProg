package persona.models

import java.time.LocalDate

class Entrenador(
    id: Int,
    nombre: String,
    fechaNacimiento: LocalDate,
    pais: String
): Persona(id,nombre,fechaNacimiento,pais), Entrenar {

    override fun entrenar() {
        println("El entrenador $nombre está entrenando")
    }
}