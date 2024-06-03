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
                file.readLines().drop(1).map { it.split(",") }.map { data ->

                   when(data[1]){
                       "tenista" ->{
                           Tenista(
                               id = data[0].toInt(),
                               nombre = data[2],
                               fechaNacimiento = data[3].returnDateTimeString(),
                               pais = data[4],
                               ranking = data[5].toInt(),
                               ganancias = data[6].toDouble()
                           )
                       }
                       "entrenador" ->{
                           Entrenador(
                               id = data[0].toInt(),
                               nombre = data[2],
                               fechaNacimiento = data[3].returnDateTimeString(),
                               pais = data[4],
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


    override fun save(file: File, listPersonas: List<Persona>): Result<Unit, PersonaError> {
        logger.debug { "Cargando el fichero ${listPersonas.size}" }
        return try {
            file.appendText("id,nombre,fechaNacimiento,pais,ranking,ganancias\n")
            listPersonas.forEach{
                when(it){
                    is Tenista -> file.appendText("${it.id},${it.nombre},${it.fechaNacimiento},${it.pais},${it.ranking},${it.ganancias}\n")
                    is Entrenador -> file.appendText("${it.id},${it.nombre},${it.fechaNacimiento},${it.pais},,\n")
                }
            }
            Ok(Unit)
        }catch (e:Exception){
            logger.error { "Error al guardar el fichero" }
            Err(PersonaError.FileError("Error al guardar el fichero"))
        }
    }
}