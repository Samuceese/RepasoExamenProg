import com.github.michaelbull.result.mapBoth
import di.appModule
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.GlobalContext.startKoin
import org.koin.fileProperties
import persona.services.PersonasService
import persona.services.PersonasServiceImpl
import java.io.File
import java.nio.file.Files
import kotlin.io.path.Path

@OptIn(KoinExperimentalAPI::class)
fun main() {

    startKoin {
        printLogger()
        fileProperties("/config.properties")
        modules(appModule)
    }

    val app = AtpApp()
    app.run()
}

class AtpApp : KoinComponent {
    fun run() {
        val service: PersonasService by inject()

        val filePersonasCsv = Path("src","main","resources","personas.csv").toFile()


        val listaPersonasCsv = service.loadCsv(filePersonasCsv).mapBoth(
            success = {
                Files.createDirectories(Path("data"))

                val fileJson = Path("data","personas.json").toFile()
                service.storeJson(fileJson,it)
            }
            , failure = {
                println("$it")
            }
        )
        val filePersonasJson = Path("data","personas.json").toFile()
        service.loadJson(filePersonasJson).mapBoth(
            success = {
                Files.createDirectories(Path("data"))

                val fileJson = Path("data","personas.csv").toFile()
                service.storeCsv(fileJson,it)
            }
            , failure = {
                println("$it")
            }
        )
    }
}
