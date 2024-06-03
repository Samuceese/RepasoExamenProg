package persona.services

import com.github.michaelbull.result.Result
import persona.errors.PersonaError
import persona.models.Persona
import java.io.File

interface PersonasService {
    fun getAll(): Result<List<Persona>,PersonaError>
    fun getById(id: Int): Result<Persona,PersonaError>
    fun save(persona: Persona): Result<Persona,PersonaError>
    fun update(id: Int,persona: Persona): Result<Persona,PersonaError>
    fun deleteById(id: Int): Result<Persona,PersonaError>
    fun load(file: File): Result<List<Persona>, PersonaError>
    fun store(file: File, listPersonas: List<Persona>) : Result<Unit, PersonaError>
}