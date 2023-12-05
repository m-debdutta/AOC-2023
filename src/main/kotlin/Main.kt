import java.io.File


fun main() {
//    dayOne(readFile("dayOneData"))
    dayTwo(readFile("dayTwoData"))
}

fun readFile(fileName: String): String {
    return File("./src/main/resources/$fileName").readText()
}
