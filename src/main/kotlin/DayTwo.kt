/*
As you walk, the Elf shows you a small bag and some cubes which are either red, green, or blue. Each
time you play this game, he will hide a secret number of cubes of each color in the bag, and your
goal is to figure out information about the number of cubes.

To get information, once a bag has been loaded with cubes, the Elf will reach into the bag, grab a
handful of random cubes, show them to you, and then put them back in the bag. He'll do this a few
times per game.

You play several games and record the information from each game (your puzzle input). Each game is
listed with its ID number (like the 11 in Game 11: ...) followed by a semicolon-separated list of
subsets of cubes that were revealed from the bag (like 3 red, 5 green, 4 blue).

For example, the record of a few games might look like this:

Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green

In game 1, three sets of cubes are revealed from the bag (and then put back again). The first set
is 3 blue cubes and 4 red cubes; the second set is 1 red cube, 2 green cubes, and 6 blue cubes; the
third set is only 2 green cubes.

The Elf would first like to know which games would have been possible if the bag contained
only 12 red cubes, 13 green cubes, and 14 blue cubes?

In the example above, games 1, 2, and 5 would have been possible if the bag had been loaded with
that configuration. However, game 3 would have been impossible because at one point the Elf showed
you 20 red cubes at once; similarly, game 4 would also have been impossible because the Elf showed
you 15 blue cubes at once. If you add up the IDs of the games that would have been possible, you get 8.

Determine which games would have been possible if the bag had been loaded with only 12 red cubes,
13 green cubes, and 14 blue cubes. What is the sum of the IDs of those games?
 */

/*
    { // list
        { // map
            game: 1, red: 12, green: 4, blue: 3 // pairs
        }
        {
            game: 2, red: 4, green: 5, blue: 6
        }
    }
*/

fun dayTwo(text: String) {
    val gameData = parseGameData(text)
    val loadedBalls = mapOf(Pair("red", 12),Pair("green", 13), Pair("blue", 14))
    println(calulateSumOfPossibleGameIds(gameData, loadedBalls))
    println(sumOfPowerOf(calculateMinimumSetOfCubes(gameData)))
}

fun sumOfPowerOf(minimumSetOfCubes: List<List<Int>>): Int {
    val list = minimumSetOfCubes.map {
        it.reduce { acc, i -> acc * i }
    }

    return list.reduce { acc, i -> acc + i }
}

fun calculateMinimumSetOfCubes(gameData: List<Pair<String, List<Pair<String, String>>>>): List<List<Int>> {
    val g = gameData.map{ it ->
        val setOfMinimumCube = mutableMapOf(Pair("red", 0), Pair("green", 0), Pair("blue", 0))
        it.second.forEach {
            if(setOfMinimumCube.getValue(it.first) < it.second.toInt()) {
                setOfMinimumCube[it.first] = it.second.toInt()
            }
        }
        setOfMinimumCube.map{it.value}
    }
    return g
}

fun calulateSumOfPossibleGameIds(
    gameData: List<Pair<String, List<Pair<String, String>>>>,
    loadedBalls: Map<String, Int>
): Int {
    return gameData
        .map { it ->
            val t = it.second.map { it ->
                when (it.first) {
                    "red" -> it.second.toInt() <= loadedBalls.get("red")!!
                    "green" -> it.second.toInt() <= loadedBalls.get("green")!!
                    "blue" -> it.second.toInt() <= loadedBalls.get("blue")!!
                    else -> false
                }
            }
            if(t.all{ it }) {
                it.first.toInt()
            } else 0
        }.reduce { acc, i -> acc + i }
}

fun parseGameData(text: String): List<Pair<String, List<Pair<String, String>>>> {
    return text
        .split("\n")
        .map{ game ->
            val g = game.split(": ")
            val key = g[0].split(" ")[1]
            val value = g[1].split("; ")
                .flatMap{ it.split(", ")
                .map {
                    val a = it.split(" ")
                    Pair(a[1], a[0])
                }
            }
            Pair(key, value)
        }
}
