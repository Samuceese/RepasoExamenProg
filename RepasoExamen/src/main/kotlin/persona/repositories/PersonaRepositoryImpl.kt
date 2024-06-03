package persona.repositories

import database.DataQueries
import database.SqlDelightManager
import org.lighthousegames.logging.logging
import persona.mappers.toEntrenador
import persona.mappers.toTenista
import persona.models.Entrenador
import persona.models.Persona
import persona.models.Tenista

private val logger = logging()

class PersonaRepositoryImpl(
    private val dbManager : SqlDelightManager
) : PersonaRepository {

    private val db = dbManager.databaseQueries

    override fun getAll(): List<Persona> {
        logger.debug { "Obteniendo todas las personas" }
        val lista = mutableListOf<Persona>()
        db.selectAllTenista().executeAsList().map { it.toTenista()}.forEach { lista.add(it) }
        db.selectAllEntrenador().executeAsList().map { it.toEntrenador()}.forEach { lista.add(it) }
        return lista
    }

    override fun getById(id: Int): Persona? {
        logger.debug { "Obtendo todas las personas por las $id" }
        val tenista = db.selectTenistaById(id.toLong()).executeAsOneOrNull()?.toTenista()
        val entrenador = db.selectEntrenadorById(id.toLong()).executeAsOneOrNull()?.toEntrenador()
        if (tenista != null) return tenista
        else if (entrenador != null) return entrenador
        else return null
    }

    override fun save(persona: Persona): Persona {
        logger.debug { "Salvando todas por las $persona" }
        when(persona){
            is Tenista -> db.insertTenista(persona.id.toLong(), persona.nombre,persona.fechaNacimiento.toString(), persona.pais,persona.ranking.toLong(),persona.ganancias)
            is Entrenador -> db.insertEntrenador(persona.id.toLong(), persona.nombre,persona.fechaNacimiento.toString(), persona.pais)
        }
        return persona
    }

    override fun update(id: Int, persona: Persona): Persona? {
        logger.debug { "Salvando persona por la $id" }

        if (getById(id) == null) return null

        when(persona){
            is Tenista -> db.updateTenista(id = id.toLong(), nombre = persona.nombre, fechaNacimiento = persona.fechaNacimiento.toString(), pais =  persona.pais, ranking = persona.ranking.toLong(), ganancias = persona.ganancias)
            is Entrenador -> db.updateEntrenador(id = id.toLong(), nombre = persona.nombre, fechaNacimiento = persona.fechaNacimiento.toString(), pais =  persona.pais)

        }
        return persona
    }

    override fun deleteById(id: Int): Persona? {
        logger.debug { "Eliminando persona por la $id" }


        val persona = getById(id)
        if (persona == null) return null
        when(persona){
            is Tenista -> db.deleteByIdEntrenador(id.toLong())
            is Entrenador -> db.deleteByIdTenista(id.toLong())
        }

        return persona
    }

    override fun tenistaMasRango(): Tenista {
        logger.debug { "Obteniendo el tenista con mas rango" }
        return db.obtenerTenistaConMasRango().executeAsOne().toTenista()
    }

}