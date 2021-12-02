package `2021`.day1

import java.io.File

fun main(){
    val depths = File("src\\main\\kotlin\\2021\\day1\\2021day1.txt").readLines().map(String::toInt)

    val part1Solution = depths.countIncreases()
    val listElemSum = depths.windowed(3,1){list -> list.sum()}
    val part2Solution = listElemSum.countIncreases()

    println("Part1 Solution: $part1Solution")
    println("Part2 Solution: $part2Solution")
}

fun List<Int>.countIncreases() =
    this.foldIndexed(0){idx, acc, it->
        return@foldIndexed if(idx != this.lastIndex && it < this[idx+1]) acc + 1 else acc
    }
