package lab3

import readAdjacencyMatrixFromFile

class HamiltonianCycle(private val adjacencyMatrix: Array<IntArray>) {
    private val numVertices = adjacencyMatrix.size
    private val hamiltonianPath = IntArray(numVertices)

    fun findHamiltonianCycle(): IntArray {
        for (i in 0 until numVertices) {
            println("Added $i vertex")
            hamiltonianPath[0] = i
            if (findFeasibleSolution((i + 1) % numVertices)) {
                return hamiltonianPath
            }
        }
        throw Exception("No Hamiltonian cycle exists")
    }

    private fun findFeasibleSolution(position: Int): Boolean {
        if (position == numVertices) {
            return adjacencyMatrix[hamiltonianPath[position - 1]][hamiltonianPath[0]] != 0
        }
        for (vertex in 1 until numVertices) {
            if (isFeasible(vertex, position)) {
                println("Added $vertex vertex")
                hamiltonianPath[position] = vertex
                if (findFeasibleSolution(position + 1)) {
                    return true
                }
                println("Back to ${hamiltonianPath[position-1]} vertex")
            }
        }
        return false
    }

    private fun isFeasible(vertex: Int, actualPosition: Int): Boolean {
        if (adjacencyMatrix[hamiltonianPath[actualPosition - 1]][vertex] == 0) {
            return false
        }
        for (i in 0 until actualPosition) {
            if (hamiltonianPath[i] == vertex) {
                return false
            }
        }
        return true
    }
}

fun main() {
    val adjacencyMatrix = readAdjacencyMatrixFromFile("input_lab3.txt")
    val graph = HamiltonianCycle(adjacencyMatrix)
    val path = graph.findHamiltonianCycle()
    println(path.joinToString(" "))
}