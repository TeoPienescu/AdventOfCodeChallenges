import java.io.File
import java.util.ArrayList


fun <T>File.mapLines(transform: (String) -> T ): List<T> {
    val result = ArrayList<T>()
    forEachLine(Charsets.UTF_8) { result.add(transform(it)); }
    return result
}

fun getFile(day:Int) = File("src/main/kotlin/2021/day$day/2021day$day.txt")