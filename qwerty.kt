fun build(N: Int, M: Int) : Array<IntArray> {
    var adjacencyMatrix = Array(N + 1, { IntArray(N + 1) })
    var edges : HashSet<Pair<Int, Int>> = HashSet()
    while (edges.size != M) {
        var begin = (1..N).random()
        var end = (1..N).random()
        if (begin > end) {
            begin = end.also { end = begin }
        }
        if (edges.add(Pair(begin, end))) {
            var weight = (1..100).random()
            adjacencyMatrix[begin][end] = weight
            adjacencyMatrix[end][begin] = weight
        }
    }
    return adjacencyMatrix
}

fun main(args : Array<String>) {
    val N = readLine()!!.toInt()
    val M = readLine()!!.toInt()
    val adjacencyMatrix : Array<IntArray> = build(N, M)
    for (i in 1..N) {
        for (j in 1..N) {
            print("${adjacencyMatrix[i][j]} ")
        }
        println()
    }
}