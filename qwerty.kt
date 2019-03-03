fun build(N: Int, M: Int) : Array<IntArray> {
    val adjacencyMatrix = Array(N, { IntArray(N) })
    val edges : HashSet<Pair<Int, Int>> = HashSet()
    while (edges.size != M) {
        var begin = (1..N).random()
        var end = (1..N).random()
        if (begin > end) {
            begin = end.also { end = begin }
        }
        if (begin != end) {
            if (edges.add(Pair(begin, end))) {
                val weight = (1..100).random()
                adjacencyMatrix[begin - 1][end - 1] = weight
                adjacencyMatrix[end - 1][begin - 1] = weight
            }
        }
    }
    return adjacencyMatrix
}

fun statistic(adjacencyMatrix : Array<IntArray>, N : Int, M: Int) {
    println("There are $N vertexes and $M edges in graph")
    var minEdge = 101
    var maxEdge = 0
    for(i in 0..(N - 1)) {
        for(j in i..(N - 1)) {
            if(adjacencyMatrix[i][j] > maxEdge)
                maxEdge = adjacencyMatrix[i][j]
            if (adjacencyMatrix[i][j] < minEdge && adjacencyMatrix[i][j] != 0)
                minEdge = adjacencyMatrix[i][j]
        }
    }
    println("The lightest edge is $minEdge and the heaviest is $maxEdge")
}

fun dijkstra(adjacencyMatrix: Array<IntArray>, N: Int, K: Int) {
    val lengths : Array<Int> = Array(N, { Int.MAX_VALUE })
    val used : Array<Boolean> = Array(N, { false })
    lengths[K] = 0
    for (i in 0..(N - 1)) {
        var v = -1
        for (j in 0..(N - 1)) {
            if (used[j] == false && (v == -1 || lengths[j] < lengths[v])) {
                v = j
            }
        }
        if (lengths[v] == Int.MAX_VALUE) break
        used[v] = true
        for (j in 0..(N - 1)) {
            if (adjacencyMatrix[v][j] == 0) continue
            if (lengths[v] + adjacencyMatrix[v][j] < lengths[j]) {
                lengths[j] = lengths[v] + adjacencyMatrix[v][j]
            }
        }
    }
    for( length in lengths) {
        println(length)
    }
}

fun main(args : Array<String>) {
    val (N, M) = readLine()!!.split(' ').map(String::toInt)
    val adjacencyMatrix : Array<IntArray> = build(N, M)
    for (row in adjacencyMatrix) {
        for (element in row) {
            print(element)
            print (" ")
        }
        println()
    }
    statistic(adjacencyMatrix, N, M)
    val K = readLine()!!.toInt()
    dijkstra(adjacencyMatrix, N, K - 1)
}