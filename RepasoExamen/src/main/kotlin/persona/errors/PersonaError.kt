package persona.errors

sealed class PersonaError(message : String) {
    class FileError(message: String) : PersonaError(message)
    class PersonaErrorValida(message: String) : PersonaError(message)
}