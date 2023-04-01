package lab5

import readAdjacencyMatrixFromFile

fun isIsomorphic(graph1: Array<IntArray>, graph2: Array<IntArray>): Boolean {
    if (graph1.size != graph2.size) {
        return false
    }

    val n = graph1.size

    // Compare the degree of each vertex in both graphs
    val degree1 = IntArray(n)
    val degree2 = IntArray(n)
    var edges1 = 0
    var edges2 = 0
    for (i in 0 until n) {
        for (j in 0 until n) {
            degree1[i] += graph1[i][j]
            degree2[i] += graph2[i][j]
        }
        edges1+=degree1[i]
        edges2+=degree2[i]
    }
    if (edges1!=edges2) {
        return false
    }

    // Check if the graphs have the same number of triangles
    val triangles1 = countTriangles(graph1)
    val triangles2 = countTriangles(graph2)
    if (triangles1 != triangles2) {
        return false
    }

    return checkBijections(graph1,graph2)!=null
}

fun countTriangles(graph: Array<IntArray>): Int {
    val n = graph.size
    var count = 0
    for (i in 0 until n) {
        for (j in i + 1 until n) {
            for (k in j + 1 until n) {
                if (graph[i][j] == 1 && graph[j][k] == 1 && graph[k][i] == 1) {
                    count++
                }
            }
        }
    }
    return count
}

fun checkBijectionForIsometric(bijection: MutableList<Int>, graph1: Array<IntArray>, graph2: Array<IntArray>):Boolean {
    for (i in graph1.indices){
        for (j in i until graph2.size){
            if (graph1[i][j]!=graph2[bijection[i]][bijection[j]]){
                return false
            }
        }
    }
    return true
}

fun checkBijections(graph1: Array<IntArray>, graph2: Array<IntArray>): List<Int>? {
    val result = mutableListOf<Array<Int>>()
    val used = BooleanArray(graph1.size)


    fun generateBijections(depth: Int, bijection: MutableList<Int>): List<Int>? {
        if (depth == graph1.size) {
            result.add(bijection.toTypedArray())
            if(checkBijectionForIsometric(bijection, graph1, graph2)){
                return bijection
            }
            return null
        }
        for (i in graph1.indices) {
            if (!used[i]) {
                used[i] = true
                bijection.add(i)
                val res = generateBijections(depth + 1, bijection)
                if(res!=null){
                    return res
                }
                used[i] = false
                bijection.removeLast()
            }
        }
        return null
    }
    return generateBijections(0, mutableListOf())
}


fun main(){

    val adjacencyMatrix1 = readAdjacencyMatrixFromFile("input_lab5_first.txt")
    val adjacencyMatrix2 = readAdjacencyMatrixFromFile("input_lab5_second.txt")
    print(isIsomorphic(adjacencyMatrix1, adjacencyMatrix2))
}

//0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0,
//1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0,
//1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0,
//1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0,
//0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0,
//0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0,
//0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1,
//0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1,
//0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1,
//0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1,
//0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0,