fun build(N: Int, M: Int) : Array<IntArray> {
    val adjacencyMatrix = Array(N) { IntArray(N) }
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

fun dijkstra(adjacencyMatrix: Array<IntArray>, N: Int, K: Int) : Array<Int> {
    val distances: Array<Int> = Array(N) { Int.MAX_VALUE }
    val used : Array<Boolean> = Array(N) { false }
    distances[K] = 0
    for (i in 0..(N - 1)) {
        var v = -1
        for (j in 0..(N - 1)) {
            if (used[j] == false && (v == -1 || distances[j] < distances[v])) {
                v = j
            }
        }
        if (distances[v] == Int.MAX_VALUE) break
        used[v] = true
        for (j in 0..(N - 1)) {
            if (adjacencyMatrix[v][j] == 0) continue
            if (distances[v] + adjacencyMatrix[v][j] < distances[j]) {
                distances[j] = distances[v] + adjacencyMatrix[v][j]
            }
        }
    }
    for(i in 0..N - 1) {
        if(distances[i] == Int.MAX_VALUE)
            println("There's no way from vertex number ${K + 1} to vertex number ${i + 1}")
        else
            println("From ${K + 1} to ${i + 1}: ${distances[i]}")
    }
    return distances
}

fun fordBellman(adjacencyMatrix: Array<IntArray>, N: Int, K: Int) : Array<Int> {
    data class Edge(val from: Int, val to: Int, val weight: Int)
    var edges: Array<Edge> = emptyArray()
    for(i in 0..(N - 1)) {
        for (j in i..(N - 1)) {
           // val edge = Edge(i, j, adjacencyMatrix[i][j])
            edges = edges.plus(Edge(i, j, adjacencyMatrix[i][j]))
            edges = edges.plus(Edge(j, i, adjacencyMatrix[i][j]))
        }
    }
    val distances : Array<Int> = Array(N, { Int.MAX_VALUE })
    distances[K] = 0
    while(true) {
        var any = false
        for (edge in edges) {
            if(distances[edge.from] < Int.MAX_VALUE) {
                if (distances[edge.to] > distances[edge.from] + edge.weight) {
                    distances[edge.to] = distances[edge.from] + edge.weight
                    any = true
                }
            }
        }
        if(!any) break
    }
    for(i in 0..N - 1) {
        if(distances[i] == Int.MAX_VALUE)
            println("There's no way from vertex number ${K + 1} to vertex number ${i + 1}")
        else
            println("From ${K + 1} to ${i + 1}: ${distances[i]}")
    }
    return distances
}

fun main(args : Array<String>) {
    val (N, M) = readLine()!!.split(' ').map(String::toInt)
    val fordBellmanArray : Array<Int>
    val dijkstraArray : Array<Int>
    val adjacencyMatrix : Array<IntArray> = build(N, M)
    for (row in adjacencyMatrix) {
        for (element in row) {
            print(element)
            print (" ")
        }
        println()
    }
    statistic(adjacencyMatrix, N, M)
    println("Please, enter K:")
    val K = readLine()!!.toInt()
    dijkstraArray = dijkstra(adjacencyMatrix, N, K - 1)
    fordBellmanArray = fordBellman(adjacencyMatrix, N, K - 1)
    if (dijkstraArray.size != fordBellmanArray.size) {
        println("Algorithms are incorrect 1")
    } else {
        for (i in 0..(dijkstraArray.size - 1)) {
            if (dijkstraArray[i] != fordBellmanArray[i]) {
                println("Algorithms are incorrect 2")
                break
            }
        }
    }
}