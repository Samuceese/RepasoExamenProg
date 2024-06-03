package persona.models

import java.time.LocalDate

abstract class Persona(
    val id: Int,
    val nombre: String,
    val fechaNacimiento: LocalDate,
    val pais: String
)