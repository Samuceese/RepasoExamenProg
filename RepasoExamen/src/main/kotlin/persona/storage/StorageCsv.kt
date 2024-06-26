package persona.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import locale.returnDateTimeString
import org.lighthousegames.logging.logging
import persona.models.Entrenador
import persona.models.Persona
import persona.models.Tenista
import persona.errors.PersonaError
import java.io.File

private val logger = logging()

class StorageCsv : Storage {
    override fun load(file: File): Result<List<Persona>, PersonaError> {
        logger.debug { "Cargando el fichero $file" }
        return try {
            Ok(
                file.readLines().drop(1).map { it.split(",") }.map {

                   when(it[1]){
                       "tenista" ->{
                           Tenista(
                               id = it[0].toInt(),
                               nombre = it[2],
                               fechaNacimiento = it[3].returnDateTimeString(),
                               pais = it[4],
                               ranking = it[5].toInt(),
                               ganancias = it[6].toDouble()
                           )
                       }
                       "entrenador" ->{
                           Entrenador(
                               id = it[0].toInt(),
                               nombre = it[2],
                               fechaNacimiento = it[3].returnDateTimeString(),
                               pais = it[4],
                           )
                       }
                       else -> throw Exception("Tipo no detectado maricón")
                   }
                }
            )
        }catch (e:Exception){
            logger.error(e) { "Error al cargar el fichero : ${e.message}" }
            Err(PersonaError.FileError("Error al leer el fichero"))
        }
    }


    override fun store(file: File, listPersonas: List<Persona>): Result<Unit, PersonaError> {
        logger.debug { "Cargando el fichero ${listPersonas.size}" }
        return try {
            file.appendText("id,tipo,nombre,fechaNacimiento,pais,ranking,ganancias\n")
            listPersonas.forEach{
                when(it){
                    is Tenista -> file.appendText("${it.id},tenista,${it.nombre},${it.fechaNacimiento},${it.pais},${it.ranking},${it.ganancias}\n")
                    is Entrenador -> file.appendText("${it.id},entrenador,${it.nombre},${it.fechaNacimiento},${it.pais},,\n")
                }
            }
            Ok(Unit)
        }catch (e:Exception){
            logger.error { "Error al guardar el fichero" }
            Err(PersonaError.FileError("Error al guardar el fichero"))
        }
    }
}