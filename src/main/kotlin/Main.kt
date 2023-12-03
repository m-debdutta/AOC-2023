import java.io.File

/*
The newly-improved calibration document consists of lines of text; each line originally contained a
specific calibration value that the Elves now need to recover. On each line, the calibration value
can be found by combining the first digit and the last digit (in that order) to form a single
two-digit number.

For example:

1abc2
pqr3stu8vwx
a1b2c3d4e5f
treb7uchet

In this example, the calibration values of these four lines are 12, 38, 15, and 77. Adding these
together produces 142.

Consider your entire calibration document. What is the sum of all the calibration values?

16566672|3131112|5241|66|12
4965666750 -> 414131112 -> 62523 -> 945 -> 144 -> 54
4997989950 -> 414181617 -> 1121028 -> 9321 -> 105 -> 60
19798992 -> 3181617 -> 102141 -> 243 -> 54
 */

fun main() {
    val text = File("./src/main/resources/data").readText()
    val regex = "\\d".toRegex()
    val strings: List<String> = text.split('\n')
    val listOfCalibrationValues = findCalibrationValues(strings)
    println(listOfCalibrationValues.reduce { acc, i -> acc + i })
}

fun findCalibrationValues(strings: List<String>): List<Int> {
    val matchingList = listOf(
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "one",
        "two",
        "three",
        "four",
        "five",
        "six",
        "seven",
        "eight",
        "nine"
    )
    return strings.map { str ->
        val b = matchingList
            .flatMap { substr ->
                val regex = substr.toRegex()
                val matchResult: Sequence<MatchResult> = regex.findAll(str)
                matchResult.toList().map { Pair(it.value, it.range.first) }
            }
            .sortedWith { i, j -> i.second - j.second }
        calculateCalibrationValue(b)
    }
}

fun calculateCalibrationValue(b: List<Pair<String, Int>>): Int {
    val firstDigit = getValueOf(b[0].first)
    val secondDigit = getValueOf(b[b.size - 1].first)
    return firstDigit*10 + secondDigit
}

fun getValueOf(str: String): Int {
    return when(str){
        "one" -> 1
        "two" -> 2
        "three" -> 3
        "four" -> 4
        "five" -> 5
        "six" -> 6
        "seven" -> 7
        "eight" -> 8
        "nine" -> 9
        else -> str.toInt()
    }
}

