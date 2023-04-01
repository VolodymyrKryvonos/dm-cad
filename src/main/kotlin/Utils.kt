import java.io.File

fun readAdjacencyMatrixFromFile(fileName: String): Array<IntArray> {
    val lines = File(fileName).readLines()
    val size = lines[0].toInt()
    val matrix = Array(size) { IntArray(size) }
    for (i in 0 until size) {
        val row = lines[i + 1].split(" ").map { it.toInt() }.toIntArray()
        matrix[i] = row
    }
    return matrix
}
