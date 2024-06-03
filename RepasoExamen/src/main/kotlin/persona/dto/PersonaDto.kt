package persona.dto

import kotlinx.serialization.Serializable

@Serializable
data class PersonaDto(
    val id : String,
    val tipo : String,
    val nombre : String,
    val fechaNacimiento : String,
    val pais : String,
    var ranking: String?=null,
    var ganancias: String? = null
)