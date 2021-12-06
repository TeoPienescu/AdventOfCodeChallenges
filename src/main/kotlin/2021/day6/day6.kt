package day6

import getFile
import mapLines

fun main(){
    val values = getFile(6).readText().split(",").map{it.toInt()}

    val part1 = execute(values, 80)
    val part2 = execute(values, 256)
    println("Part1: $part1")
    println("Part2: $part2")
}

fun execute(values: List<Int>, days: Int): Long {
    val fish = values.groupBy { it }.map { (age, noOfFishesWithSameDays) -> age to noOfFishesWithSameDays.size.toLong()}
        .toMap().toMutableMap()
    repeat(days) {
        val updates = fish.map { (age, noOfFishesWithSameDays) ->
            if (age == 0) 6 to noOfFishesWithSameDays else age - 1 to noOfFishesWithSameDays
        } + (8 to (fish[0] ?: 0))
        fish.clear()
        updates.map { (age, noOfFishesWithSameDays) -> fish[age] = (fish[age] ?: 0) + noOfFishesWithSameDays }
    }
    return fish.values.sum()
}
