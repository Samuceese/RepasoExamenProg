package persona.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.lighthousegames.logging.logging
import persona.dto.PersonaDto
import persona.mappers.toPersona
import persona.models.Persona
import persona.errors.PersonaError
import persona.mappers.toPersonaDto
import java.io.File

private val logger = logging()

class StorageJson : Storage {
    override fun load(file: File): Result<List<Persona>, PersonaError> {
        logger.debug { "Leyendo el fichero: $file" }
        return try {
            val json = Json { ignoreUnknownKeys = true }
            Ok(json.decodeFromString<List<PersonaDto>>(file.readText()).map { it.toPersona() })
        }catch (e:Exception){
            logger.error { "Error al leer el fichero ${e.message}" }
            Err(PersonaError.FileError("Error al leer el fichero ${e.message}"))
        }
    }

    override fun save(file: File, listPersonas: List<Persona>): Result<Unit, PersonaError> {
        logger.debug { "Guardando el fichero: $file" }
        return try {
            val json = Json {
                ignoreUnknownKeys= true;
                prettyPrint = true}
            Ok(
                file.writeText(
                    Json.encodeToString<List<PersonaDto>>(listPersonas.map { it.toPersonaDto() })
                )
            )
        }catch (e:Exception){
            logger.error { "Error al escribir" }
            Err(PersonaError.FileError("Error al escribir"))
        }
    }
}