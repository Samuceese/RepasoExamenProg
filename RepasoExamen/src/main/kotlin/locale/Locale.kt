package locale

import java.time.LocalDate

fun String.returnDateTimeString(): LocalDate {
    val cadena=this.split("-")
    return LocalDate.of(cadena[0].toInt(),cadena[1].toInt(),cadena[2].toInt())
}