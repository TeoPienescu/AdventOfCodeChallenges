package day7

import getFile
import kotlin.math.abs

typealias CrabPositions = List<Int>

fun main(){
    val crabPositions = getFile(7).readText().split(",").map { it.toInt() }

    val maxValue = crabPositions.maxOrNull()
    requireNotNull(maxValue)
    val rangeOfDestPositions = (0..maxValue)

    val part1 = crabPositions.getMinFuel(part1 = true, rangeOfDestPositions)
    val part2 = crabPositions.getMinFuel(part1 = false, rangeOfDestPositions)

    println("Part1: $part1")
    println("Part1: $part2")

}

fun CrabPositions.getMinFuel(part1: Boolean, rangeOfDestPositions: IntRange): Int{
    return rangeOfDestPositions.fold(0){ acc, finalPos->
        val fuel = this.fold(0){accm, xPos ->
            val absValueOfCrabPosAndDestPos = abs(xPos - finalPos)
            accm + if(!part1) (0..absValueOfCrabPosAndDestPos).sum() else absValueOfCrabPosAndDestPos
        }
        if(acc!= 0 && acc < fuel) acc else fuel
    }
}


