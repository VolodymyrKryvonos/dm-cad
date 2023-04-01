package lab2

import readAdjacencyMatrixFromFile
import java.io.File
import java.util.Stack


class Graph(private val matrix: Array<IntArray>) {

    fun findEulerPath(isGraphDirected: Boolean = false): List<Pair<Int, Int>> {
        val stack = Stack<Int>()
        val path = mutableMapOf<Int, MutableList<Int>>()
        stack.add(0)
        while (stack.isNotEmpty()) {
            val currentNode = stack.peek()
            var isHaveUnvisited = false
            for (i in 0 until matrix[currentNode].size) {
                if (matrix[currentNode][i] != 0) {
                    path[currentNode] = path[currentNode]?.apply { add(i) } ?: mutableListOf(i)
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
        return sortPath(path)
    }

    private fun sortPath(path: MutableMap<Int, MutableList<Int>>): List<Pair<Int, Int>> {
        val sortedPath = mutableListOf<Pair<Int, Int>>()
        var currentNode = 0
        while (path.isNotEmpty()) {
            val newNode = path[currentNode]?.removeLast() ?: throw Exception("Path not found")
            sortedPath.add(Pair(currentNode + 1, newNode + 1))
            if (path[currentNode]?.size == 0) {
                path.remove(currentNode)
            }
            currentNode = newNode
        }
        return sortedPath
    }
}
fun main() {
    val adjacencyMatrix = readAdjacencyMatrixFromFile("input_lab2.txt")
    val graph = Graph(adjacencyMatrix)

    println("Edges: ")
    println(graph.findEulerPath().joinToString("\n") {
        "${it.first} --> ${it.second}"
    })
}
