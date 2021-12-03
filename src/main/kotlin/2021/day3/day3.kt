package `2021`.day3

import java.io.File

fun main(){
    val binNums = File("src\\main\\kotlin\\2021\\day3\\2021day3.txt").readLines()
    val binNumsOrganizedByIndex = binNums.organizeByBit()

    val mostCommonBitsBinRepresentation =
        binNumsOrganizedByIndex.map { index -> index.checkMostOrLeastCommonBit(mostCommon = true) }
    val leastCommonBitBinRepresentation =
        binNumsOrganizedByIndex.map{ index -> index.checkMostOrLeastCommonBit(mostCommon = false) }


    val gamma = mostCommonBitsBinRepresentation.toIntegerValue()
    val epsilon = leastCommonBitBinRepresentation.toIntegerValue()

    val oxygenGenerator =
        binNums.getRating(mostCommonBitsBinRepresentation, mostOrleast = true)
            .toIntListBinRepresentation().toIntegerValue()

    val co2Scruber =
        binNums.getRating(leastCommonBitBinRepresentation, mostOrleast = false).toIntListBinRepresentation()
            .toIntegerValue()

    println("Part1: ${gamma * epsilon}")
    println("Part2: ${oxygenGenerator * co2Scruber}")
}

fun List<String>.toIntListBinRepresentation():List<Int>{
    return this.first().toList().map { numBinChar -> numBinChar.toString().toInt() }
}

fun List<String>.getRating(mostCommon: List<Int>, mostOrleast: Boolean): List<String>{
    return (mostCommon.indices).fold(emptyList()){ acc, idx ->
        if(acc.isEmpty()) acc + this
            .filter{ numBinString -> numBinString in this.checkIfIdxBitIsEqualTo(mostCommon, idx)}
        else
            if(acc.size != 1) {
                val listOrganizedByIdxBits = acc.organizeByBit()
                acc.filter { numBinString ->
                    numBinString in this.checkIfIdxBitIsEqualTo(listOrganizedByIdxBits
                        .map { allNIdxBits -> allNIdxBits.checkMostOrLeastCommonBit(mostOrleast) }, idx
                    )
                }
            }
            else return acc
    }
}

fun List<String>.checkIfIdxBitIsEqualTo(mostCommon: List<Int>, idx: Int): List<String>{
    return this.fold(emptyList()){acc, string->
        return@fold if(string[idx].digitToInt() == mostCommon[idx]) acc + string else acc
    }
}

fun List<String>.organizeByBit(): List<String>{
    return (0 until this.first().length).fold(emptyList()){acc, idx->
        acc + this.getNBits(idx)
    }
}

fun List<String>.getNBits(bitIdx: Int): String{
    return this.fold(""){acc, binNum ->
        acc + binNum[bitIdx]
    }
}

fun String.checkMostOrLeastCommonBit(mostCommon: Boolean): Int{
    val numBitsOne = this.filter { it == '1' }.length
    val numBitsZero = this.length - numBitsOne
    val positiveDiff = numBitsOne - numBitsZero >= 0
    val mostCommonBit = if(positiveDiff)  1 else 0
    val leastCommonBit = if(positiveDiff) 0 else 1
    return if(mostCommon) mostCommonBit else leastCommonBit
}

fun baseTwoPower(times: Int) = (0 until times).fold(1){acc,_ -> acc * 2}

fun List<Int>.toIntegerValue(): Int{
    val binOrderedList = this.reversed()
    return binOrderedList.foldIndexed(0){idx, acc, binValue ->
        return@foldIndexed acc + (binValue * baseTwoPower(idx))
    }
}



