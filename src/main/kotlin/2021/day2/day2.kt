package `2021`.day2

import java.io.File

fun main(){
    val commands = File("src\\main\\kotlin\\2021\\day2\\2021day2.txt").readLines().map{
        val (command,delta) = it.split(" ")
        Instruction(command, delta.toInt())
    }.toCommand()

    val part1 = commands.executeAll()
    val part2 = commands.executeAll(part2 = true)

    println("Part1 : ${part1.x * part1.depth}")
    println("Part2 : ${part2.x * part2.depth}")
}

data class Submarine(val x: Int, val depth: Int, val aim: Int)

typealias Instruction = Pair<String, Int>

sealed class Command(val action: (Submarine, Boolean) -> Submarine)

fun List<Pair<String,Int>>.toCommand(): List<Command>{
    return this.map{ pair ->
        when(pair.first) {
            "forward" -> Forward(pair.second)
            "down" -> Down(pair.second)
            "up" -> Up(pair.second)
            else -> throw Exception("Invalid Command")
        }
    }
}

class Forward(delta: Int): Command({submarine, part2 ->
    submarine.copy(x = submarine.x + delta,
        depth = if(part2) submarine.depth + submarine.aim * delta else submarine.depth)
})

class Up(delta: Int): Command({submarine, part2 ->
    if(!part2) submarine.copy(depth = submarine.depth - delta)
    else submarine.copy(aim = submarine.aim - delta)
})

class Down(delta: Int): Command({submarine, part2 ->
    if(!part2) submarine.copy(depth = submarine.depth + delta)
    else submarine.copy(aim = submarine.aim + delta)
})

fun List<Command>.executeAll(part2: Boolean = false): Submarine{
    return this.fold(Submarine(0,0,0)){ submarine, command ->
        command.action(submarine, part2)
    }
}
