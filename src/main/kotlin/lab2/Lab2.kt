package lab2

import readAdjacencyMatrixFromFile
import java.util.Stack


class Graph(private val matrix: Array<IntArray>) {

    data class MarkerableEdge(
        val origin: Int,
        val destination: Int,
        var used: Boolean = false,
    )

    fun findEulerPath(isGraphDirected: Boolean = false): MutableList<MarkerableEdge> {
        val stack = Stack<Int>()
        val path = mutableMapOf<Int, MutableList<MarkerableEdge>>()
        stack.add(0)
        while (stack.isNotEmpty()) {
            val currentNode = stack.peek()
            var isHaveUnvisited = false
            for (i in 0 until matrix[currentNode].size) {
                if (matrix[currentNode][i] != 0) {
                    path[currentNode] = path[currentNode]?.apply { add(MarkerableEdge(currentNode, i)) }
                        ?: mutableListOf(MarkerableEdge(currentNode, i))
                    matrix[currentNode][i] = 0
                    if (!isGraphDirected) {
                        matrix[i][currentNode] = 0
                    }
                    isHaveUnvisited = true
                    stack.add(i)
                    break
                }
            }
            if (!isHaveUnvisited) {
                stack.pop()
            }
        }
        val numberOfEdges = run {
            var sum = 0
            for (edge in path) {
                sum += edge.value.size
            }
            sum
        }
        val res = buildPath(path, numberOfEdges)
        if (res.size!=numberOfEdges || res.lastOrNull()?.destination!=res.firstOrNull()?.origin)
            throw Exception("Path not found")
        return res
    }

    private fun buildPath(
        edgeList: MutableMap<Int, MutableList<MarkerableEdge>>,
        numberOfEdges: Int,
        currentVertex: Int = 0,
        path: MutableList<MarkerableEdge> = mutableListOf()
    ): MutableList<MarkerableEdge> {
        for (edge in edgeList[currentVertex] ?: listOf()) {
            if (edge.used)
                continue
            edge.used = true
            path.add(edge)
            if (path.size == numberOfEdges)
                return path

            val path = buildPath(edgeList, numberOfEdges, edge.destination, path)
            if (path.size == numberOfEdges)
                return path
            path.removeLast()
            edge.used = false
        }
        return path
    }
}

fun main() {
    val adjacencyMatrix = readAdjacencyMatrixFromFile("input_lab2.txt")
    val graph = Graph(adjacencyMatrix)

    println("Edges: ")
    println(graph.findEulerPath().joinToString("\n") {
        "${it.origin} --> ${it.destination}"
    })
}
