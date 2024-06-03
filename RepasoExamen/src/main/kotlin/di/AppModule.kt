package di

import cache.CachePersonasImpl
import config.Config
import database.SqlDelightManager
import org.koin.dsl.module
import persona.repositories.PersonaRepository
import persona.repositories.PersonaRepositoryImpl
import persona.services.PersonasService
import persona.services.PersonasServiceImpl
import persona.storage.StorageCsv
import persona.storage.StorageJson

val appModule = module{

    single<CachePersonasImpl> {CachePersonasImpl(5)}
    single<PersonaRepository>{PersonaRepositoryImpl(get())}
    single<PersonasService>{PersonasServiceImpl(get(),get(),get(),get())}
    single<StorageCsv>{StorageCsv()}
    single<StorageJson>{StorageJson()}

    single <Config>{Config}

    single<SqlDelightManager> { SqlDelightManager(get()) }


}