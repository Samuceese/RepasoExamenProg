package di

import cache.CachePersonasImpl
import config.Config
import database.SqlDelightManager
import org.koin.dsl.module
import persona.repositories.PersonaRepositoryImpl
import persona.services.PersonasService
import persona.services.PersonasServiceImpl
import persona.storage.Storage
import persona.storage.StorageCsv
import persona.storage.StorageJson

val appModule = module{

    single<CachePersonasImpl> {CachePersonasImpl(5)}
    single<PersonaRepositoryImpl>{PersonaRepositoryImpl(get())}
    single<Storage>{StorageCsv()}
    single<Storage>{StorageJson()}
    single<PersonasService>{PersonasServiceImpl(get(),get(),get())}

    single <Config>{Config}

    single<SqlDelightManager> { SqlDelightManager(get()) }


}