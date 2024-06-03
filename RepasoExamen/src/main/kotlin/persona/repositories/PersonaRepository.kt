package persona.repositories

import persona.models.Persona

interface PersonaRepository {
    fun getAll(): List<Persona>
    fun getById(id: Int): Persona?
    fun save(persona: Persona): Persona
    fun update(id:Int, persona: Persona): Persona?
    fun deleteById(id:Int): Persona?
}