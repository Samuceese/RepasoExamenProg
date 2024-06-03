package persona.services

import cache.CachePersonasImpl
import com.github.michaelbull.result.*
import org.lighthousegames.logging.logging
import persona.errors.PersonaError
import persona.models.Persona
import persona.repositories.PersonaRepository
import persona.storage.StorageCsv
import persona.storage.StorageJson
import java.io.File

private val logger = logging()

class PersonasServiceImpl(
    private val repository: PersonaRepository,
    private val cache: CachePersonasImpl,
    private val storageCsv: StorageCsv,
    private val storageJson: StorageJson
):PersonasService {

    override fun getAll(): Result<List<Persona>, PersonaError> {
        logger.debug { "Obteniendo todos las personas" }
        return Ok(repository.getAll())
    }

    override fun getById(id: Int): Result<Persona, PersonaError> {
        logger.debug { "Obtendo las personas por id $id" }
        return repository.getById(id)?.let {
            cache.put(it.id.toLong(),it)
            Ok(it) }?:
            Err(PersonaError.PersonaErrorValida("Error al obtener la por id $id"))
    }

    override fun save(persona: Persona): Result<Persona, PersonaError> {
        logger.debug { "Guardando persona: $persona" }
        return Ok(repository.save(persona)).andThen { s ->
            logger.debug { "Guardando en la cache" }
            cache.put(persona.id.toLong(),s)
        }
    }

    override fun update(id: Int, persona: Persona): Result<Persona, PersonaError> {
        logger.debug { "Actualizando producto por id $id" }
        return repository.update(id,persona)?.let {
            cache.put(id.toLong(),it)
            Ok(it)}?:
            Err(PersonaError.PersonaErrorValida("Error al actualizando por id $id"))
    }

    override fun deleteById(id: Int): Result<Persona, PersonaError> {
        logger.debug { "Eliminando persona por id $id" }
        return repository.deleteById(id)?.let {
            cache.remove(id.toLong())
            Ok(it)
        }
            ?: Err(PersonaError.PersonaErrorValida("Persona no eliminada por id $id"))
    }

    override fun loadJson(file: File): Result<List<Persona>, PersonaError> {
        logger.debug { "Cargando fichero $file" }
        return storageJson.load(file)
            .onSuccess {
                //it.forEach { repository.save(it) }
                Ok(it)
            }
    }

    override fun storeJson(file: File, listPersonas: List<Persona>): Result<Unit, PersonaError> {
        logger.debug { "Salvando en json" }
        return storageJson.store(file, listPersonas)
    }

    override fun loadCsv(file: File): Result<List<Persona>, PersonaError> {
        logger.debug { "Cargando fichero" }
        return storageCsv.load(file)
            .onSuccess {
                it.forEach { repository.save(it) }
                Ok(it)
            }
    }

    override fun storeCsv(file: File, listPersonas: List<Persona>): Result<Unit, PersonaError> {
        logger.debug { "Salvando en csv" }
        return storageCsv.store(file, listPersonas)
    }


}