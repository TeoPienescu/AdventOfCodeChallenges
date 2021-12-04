package `2021`.day4

import java.io.File

data class BingoCard(val values: List<List<Int>>, val markedValues: List<Int> = emptyList()) {

    fun mark(number: Int) = if (win()) this else BingoCard(values, markedValues + number)

    fun bingo() = (values.flatten() - markedValues.toList()).sum() * markedValues.last()

    private fun win() = values.any { row -> markedValues.containsAll(row) }
            || (0..4).any { line -> (0..4).all { column -> values[column][line] in markedValues } }
}

fun main(){
    val input = File("src\\main\\kotlin\\2021\\day4\\2021day4.txt").readLines()
    val chosenNumbers = input.first().split(",").map{number -> number.toInt()}
    val bingoCards = input.drop(2).windowed(5,6)
        .map { board -> board.map { row -> row.split(" ")
            .mapNotNull { value -> value.toIntOrNull() } } }
        .map { values -> BingoCard(values) }

    val play = chosenNumbers.fold(bingoCards) { bingoList, number ->
        bingoList.map { bingoCard -> bingoCard.mark(number) }
    }

    val winFirst = play.minByOrNull { bingoCard -> bingoCard.markedValues.size }
    val winLast =  play.maxByOrNull { bingoCard -> bingoCard.markedValues.size }

    requireNotNull(winFirst)
    requireNotNull(winLast)

    val part1 = winFirst.bingo()
    val part2 = winLast.bingo()

    println("Part1: $part1")
    println("Part2: $part2")
}
