package `2021`.day5


import getFile
import mapLines

import kotlin.math.abs
import kotlin.math.max


typealias Grid = List<MutableList<Int>>

data class LineVent(val from: Position, val to: Position) {
    fun isHorizontal() = from.y == to.y
    fun isVertical() = from.x == to.x
}

fun LineVent(string: String): LineVent {
    val (initialPos, finalPos) = string.split(" -> ")
    return LineVent(Position(initialPos), Position(finalPos))
}

data class Position(val x: Int, val y: Int)

fun Position(string: String): Position {
    val (x, y) = string.split(",").map { it.toInt() }
    return Position(x, y)
}



fun main() {
    val input = getFile(5).mapLines { LineVent(it) }

    val yMax = input.maxOf { max(it.from.y, it.to.y) }
    val xMax = input.maxOf { max(it.from.x, it.to.x) }
    val grid = buildGrid(xMax+2, yMax+2)

    val (part1Lines, part2Lines) = input.partition { it.isVertical() || it.isHorizontal() }

    val part1Grid = part1Lines.getVentMap(grid)
    val overlappedLinesPart1 = part1Grid.countOverlappedLines()
    println("Part1: $overlappedLinesPart1")

    val part2Grid = part2Lines.getVentMap(part1Grid)
    val overlappedLinesPart2 = part2Grid.countOverlappedLines()
    println("Part2: $overlappedLinesPart2")
}

fun buildGrid(width: Int, height: Int): Grid = List(width) { MutableList(height) { 0 } }


fun LineVent.markLine(grid: Grid){
    val xFactor = if (from.x > to.x) -1 else if (from.x == to.x) 0 else 1
    val yFactor = if (from.y > to.y) -1 else if (from.y == to.y) 0 else 1
    val absDx = abs(from.x - to.x)
    val absDy = abs(from.y - to.y)
    var currX = 0
    var currY = 0
    while (currX <= absDx || currY <= absDy) {
        grid[from.y + currY * yFactor][from.x + currX * xFactor]++
        currY++
        currX++
    }
}

fun List<LineVent>.getVentMap(grid: Grid): Grid {
    val mutableGrid = grid.toMutableList()
    forEach { it.markLine(grid) }
    return mutableGrid
}

fun Grid.countOverlappedLines(): Int = sumOf{ line -> line.count {value -> value >= 2 } }