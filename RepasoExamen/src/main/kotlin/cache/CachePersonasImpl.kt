package cache

import cache.errors.CacheError
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import config.Config
import org.lighthousegames.logging.logging
import persona.models.Persona

private val logger = logging()

class CachePersonasImpl(
    private val cacheSize : Int
) : Cache<Long,Persona> {

    private val cache = mutableMapOf<Long,Persona>()

    override fun get(key: Long): Result<Persona, CacheError> {
        logger.debug { "Obteniendo valor de la cache" }
        return if (cache.containsKey(key)){
            Ok(cache.getValue(key))
        }else{
            Err(CacheError.CacheErrorValid("No existe el valor en la cache"))
        }
    }

    override fun put(key: Long, value: Persona): Result<Persona, Nothing> {
        logger.debug { "Salvando valor en la cache" }
        if (cache.size > cacheSize && !cache.containsKey(key)){
            logger.debug { "Eliminando primer valor de la cache" }
            cache.remove(cache.keys.first())
        }
        cache[key] = value
        return Ok(value)
    }

    override fun remove(key: Long): Result<Persona, CacheError> {
        logger.debug { "Eliminando valor de la cache" }
        return if (cache.containsKey(key)){
            Ok(cache.remove(key)!!)
        }else{
            Err(CacheError.CacheErrorValid("No existe el valor en la cache"))
        }
    }

}