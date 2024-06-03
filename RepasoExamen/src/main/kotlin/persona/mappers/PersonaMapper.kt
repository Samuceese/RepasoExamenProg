package persona.mappers

import database.EntrenadorEntity
import database.TenistaEntity
import locale.returnDateTimeString
import org.lighthousegames.logging.logging
import persona.dto.PersonaDto
import persona.models.Entrenador
import persona.models.Persona
import persona.models.Tenista

private val logger = logging()

fun PersonaDto.toPersona() : Persona {
    logger.debug { "Mappeando PersonaDto to Persona" }
    return when(this.tipo){
        "tenista" -> Tenista(
            id = this.id.toInt(),
            nombre = this.nombre,
            fechaNacimiento = this.fechaNacimiento.returnDateTimeString(),
            pais = this.pais,
            ranking = this.ranking!!.toInt(),
            ganancias = this.ganancias!!.toDouble()
        )
        "entrenador" -> Entrenador(
            id = this.id.toInt(),
            nombre = this.nombre,
            fechaNacimiento = this.fechaNacimiento.returnDateTimeString(),
            pais = this.pais
        )
        else -> throw Exception("Tipo no válido mariquita")
    }
}


fun Tenista.toPersonaDto() : PersonaDto  {
    logger.debug { "Mappeando Tenista to PersonaDto" }
    return PersonaDto(
        id = this.id.toString(),
        tipo = "tenista",
        nombre = this.nombre,
        fechaNacimiento = this.fechaNacimiento.toString(),
        pais = this.pais,
        ranking = this.ranking.toString(),
        ganancias = this.ganancias.toString()
    )
}

fun Entrenador.toPersonaDto() : PersonaDto  {
    logger.debug { "Mappeando Entrenador to PersonaDto" }
    return PersonaDto(
        id = this.id.toString(),
        tipo = "entrenador",
        nombre = this.nombre,
        fechaNacimiento = this.fechaNacimiento.toString(),
        pais = this.pais,
        ranking = null,
        ganancias = null
    )
}

fun Persona.toPersonaDto() : PersonaDto  {
    logger.debug { "Mappeando PersonaDto to PersonaDto" }
    return when(this){
        is Tenista -> PersonaDto(
            id = this.id.toString(),
            tipo = "tenista",
            nombre = this.nombre,
            fechaNacimiento = this.fechaNacimiento.toString(),
            pais = this.pais,
            ranking = this.ranking.toString(),
            ganancias = this.ganancias.toString()
        )
        is Entrenador -> PersonaDto(
            id = this.id.toString(),
            tipo = "entrenador",
            nombre = this.nombre,
            fechaNacimiento = this.fechaNacimiento.toString(),
            pais = this.pais,
            ranking = null,
            ganancias = null
        )
        else -> throw Exception("Tipo de Entrenador mariconazo")
    }
}

fun EntrenadorEntity.toEntrenador(): Entrenador{
    logger.debug { "Mappeando EntrenadorEntity to Entrenador" }
    return Entrenador(
        id = this.id.toInt(),
        nombre = this.nombre,
        fechaNacimiento = this.fechaNacimiento.returnDateTimeString(),
        pais = this.pais
    )
}

fun Entrenador.toEntrenadorEntity(): EntrenadorEntity{
    logger.debug { "Mappeando Entrenador to EntrenadorEntity" }
    return EntrenadorEntity(
        id = this.id.toLong(),
        nombre = this.nombre,
        fechaNacimiento = this.fechaNacimiento.toString(),
        pais = this.pais
    )
}

fun TenistaEntity.toTenista(): Tenista{
    logger.debug { "Mappeando TenistaEntity to Tenista" }
    return Tenista(
        id = this.id.toInt(),
        nombre = this.nombre,
        fechaNacimiento = this.fechaNacimiento.returnDateTimeString(),
        pais = this.pais,
        ranking = this.ranking.toInt(),
        ganancias = this.ganancias
    )
}

fun Tenista.toTenistaEntity(): TenistaEntity{
    logger.debug { "Mappeando TenistaEntity to Tenista" }
    return TenistaEntity(
        id = this.id.toLong(),
        nombre = this.nombre,
        fechaNacimiento = this.fechaNacimiento.toString(),
        pais = this.pais,
        ranking = this.ranking.toLong(),
        ganancias = this.ganancias
    )
}
