package persona.storage

import com.github.michaelbull.result.Result
import persona.models.Persona
import persona.errors.PersonaError
import java.io.File

interface Storage {

    fun load(file: File): Result<List<Persona>, PersonaError>
    fun store(file: File, listPersonas: List<Persona>) : Result<Unit, PersonaError>

}