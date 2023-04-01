package lab1

import Edge
import java.io.File

class KruskalAlgorithm(private val n: Int, private val edges: List<Edge>) {
    private val parent = IntArray(n + 1) { it }

    private fun find(u: Int): Int {
        if (u != parent[u]) parent[u] = find(parent[u])

        return parent[u]
    }

    private fun union(u: Int, v: Int) {
        parent[find(u)] = find(v)
    }

    fun minimumSpanningTree(): List<Edge> {
        val sorted = edges.sortedBy { it.weight }
        val mst = mutableListOf<Edge>()
        var edgeCount = 0

        for (edge in sorted) {
            if (find(edge.source) != find(edge.destination)) {
                union(edge.source, edge.destination)
                mst.add(edge)
                edgeCount++
                println("Added edge: $edge")
                if (edgeCount == n - 1) break
            } else {
                println("Skipped edge: $edge")
            }
        }
        return mst
    }

    fun maximumSpanningTree(): List<Edge> {
        val sorted = edges.sortedByDescending { it.weight }
        val mst = mutableListOf<Edge>()
        var edgeCount = 0

        for (edge in sorted) {
            if (find(edge.source) != find(edge.destination)) {
                union(edge.source, edge.destination)
                mst.add(edge)
                edgeCount++
                println("Added edge: $edge")
                if (edgeCount == n - 1) break
            } else {
                println("Skipped edge: $edge")
            }
        }
        return mst
    }
}

fun main() {
    val input = File("input_lab1.txt").readLines()
    val n = input[0].toInt()
    val matrix = input.subList(1, input.size).map { it.split(" ").map { it.toInt() } }
    println(matrix.joinToString("\n") { it.toString() })
    val edges = mutableListOf<Edge>()
    for (i in 0 until n) {
        for (j in i + 1 until n) {
            if (matrix[i][j] != 0) {
                edges.add(Edge(i, j, matrix[i][j]))
            }
        }
    }
    println(edges.sortedByDescending { it.weight }.joinToString("\n") { it.toString() })

    val kruskal = KruskalAlgorithm(n, edges)
    val mst = kruskal.minimumSpanningTree()

    println("Minimum Spanning Tree:")
    for (edge in mst) {
        println("${edge.source} - ${edge.destination} : ${edge.weight}")
    }
}
