# PageRanker
For this assignment, you need to implement PageRank using a Graph datastructure. Start with a directed, unweighted graph. Each node in the graph then needs to be assigned a PageRank value. This is typically done using an iterative process.

Here are the details in pseudocode form:
        Init PageRank of all nodes to 1 / N, where N = total number of nodes
        dampening = 0.85

        While node PageRanks have not converged:
            For each node, A, in the graph:
                Set incomingSum = 0
                Find all the nodes, Bs, that link to A
                For each node, B, in Bs:
                    incomingSum += B's PageRank / B's number of outgoing links
                A's PageRank = ((1 - dampening) / N) + dampening * incomingSum
 Once you have the PageRank algorithm coded up, then test it to see what happens. Use "small", "big_graph", "bigger_graph" 
