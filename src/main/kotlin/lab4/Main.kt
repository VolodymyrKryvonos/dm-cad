package lab4

import readAdjacencyMatrixFromFile
import java.lang.Integer.min

fun getMaxFlow(capacity: Array<IntArray>, source: Int, sink: Int): Int {
    val n = capacity.size
    var flow = 0
    while (true) {
        val parent = IntArray(n) { -1 }
            val queue = ArrayDeque<Int>()
        parent[source] = source
        queue.addLast(source)
        while (queue.isNotEmpty()) {
            val u = queue.removeLast()
            for (v in 0 until n) {
                if (parent[v] == -1 && capacity[u][v] > 0) {
                    parent[v] = u
                    queue.addLast(v)
                }
            }
        }
        if (parent[sink] == -1) break
        var pathFlow = Int.MAX_VALUE
        var v = sink
        while (v != source) {
            val u = parent[v]
            pathFlow = min(pathFlow, capacity[u][v])
            capacity[u][v] -= pathFlow
            capacity[v][u] += pathFlow
            v = u
        }
        flow += pathFlow
    }
    return flow
}


fun main() {
    val adjacencyMatrix = readAdjacencyMatrixFromFile("input_lab4.txt")
    print(getMaxFlow(adjacencyMatrix,0,7))
}