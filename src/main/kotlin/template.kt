import kotlinx.coroutines.*
import java.io.File
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.text.SimpleDateFormat

/**
 * Advent of code template builder for Intellij project
 *
 * How to get your AOC session ID:
 *
 * 1º login to advent of code in chrome.
 *
 * 2º Open dev tools.
 *
 * 3º Navigate to the network tab
 *
 * 4º Press Ctrl+R to record requests
 *
 * 5º Enter any puzzle input URI
 *
 * 6º open input resource
 *
 * 7º Your session Id is in the header "cookie", copy the whole line.
 *
 * How to use:
 *
 * Create an environment variable named TOKEN with your advent of code session ID.
 *
 * Run this script.
 *
 * To use it in other years change the YEAR property constant inside AOCTemplateBuilder.
 *
 * @author Francisco Costa 2021, o craque
 */
object AOCTemplateBuilder{

    private const val YEAR = 2021

    private fun createEntries(folderName: String){
        createFolder(folderName)
        createFile("$folderName/$folderName.kt")
        createFile("$folderName/2021$folderName.txt")
    }

    private suspend fun tryFetchOnChallengeInput(day: Int): String{
        check(checkChallengeTime(day)){"Challenge input not available yet."}
        return fetch("https://adventofcode.com/$YEAR/day/$day/input") ?: error("Could not reach AOC.")
    }

    fun buildTemplate(day:Int){
        val folder = "day$day"
        createEntries(folder)
        runBlocking {
            writeToFile(readFromTemplate().replace("%d", "$day"), "$folder/$folder.kt")
            writeToFile(tryFetchOnChallengeInput(day), "$folder/2021$folder.txt")
        }
    }

    private fun checkChallengeTime(day:Int): Boolean{
        val format = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
        val timeZoneOffset = format.timeZone.rawOffset
        val date = "$YEAR/12/$day ${(5+timeZoneOffset)%24}:00:00"// UTC Timezone
        val challengeTimeMilis = format.parse(date).time
        val currentMilis = System.currentTimeMillis()
        return currentMilis > challengeTimeMilis
    }

}

private const val CODE_PATH = "src/main/kotlin/2021"

private fun createFolder(name: String){
    val folder = File(CODE_PATH, name)
    check(!folder.exists()){"Folder $name already exists in $CODE_PATH."}
    folder.mkdir()
}

private fun createFile(name: String){
    check(File(CODE_PATH, name).createNewFile()){"File $name already exists in"}
}

private fun writeToFile(text: String, name: String) = File(CODE_PATH, name).writeText(text)

private fun readFromTemplate() = File(CODE_PATH, "aoc-template").readText()

private suspend fun fetch(uri: String): String?{
    val client = HttpClient.newBuilder().build();
    val request = HttpRequest.newBuilder()
        .uri(URI.create(uri)).setHeader("cookie", System.getenv("TOKEN"))
        .build();
    return runBlocking{
        val response = withContext(Dispatchers.IO) { client.send(request, HttpResponse.BodyHandlers.ofString()) }
        response.body()
    }
}

fun main(){
    try {
        AOCTemplateBuilder.buildTemplate(7)
    }catch (error: IllegalStateException){
        println("Error: ${error.message}")
    }
}