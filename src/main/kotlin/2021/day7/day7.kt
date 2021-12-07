package day7

import getFile
import kotlin.math.abs

fun main(){
    val crabPositions = getFile(7).readText().split(",").map { it.toInt() }

    val maxValue = crabPositions.maxOrNull()
    requireNotNull(maxValue)
    val rangeOfDestPositions = (0..maxValue)

    val part1 = getMinFuel(part1 = true, rangeOfDestPositions, crabPositions)
    val part2 = getMinFuel(part1 = false, rangeOfDestPositions, crabPositions)

    println("Part1: $part1")
    println("Part1: $part2")

}

fun getMinFuel(part1: Boolean, rangeOfDestPositions: IntRange, crabPositions: List<Int>): Int{
    return rangeOfDestPositions.fold(0){ acc, finalPos->
        val fuel = crabPositions.fold(0){accm, xPos ->
            val absValueOfCrabPosAndDestPos = abs(xPos - finalPos)
            accm + if(!part1) (0..absValueOfCrabPosAndDestPos).sum() else absValueOfCrabPosAndDestPos
        }
        if(acc!= 0 && acc < fuel) acc else fuel
    }
}


