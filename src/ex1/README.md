About the project:

The project is about a data structure called an undirectional weighted graph.
This data structure contains vertexes from the type of node_info and edges that 
connected between the vertexes. Each edge has a weight that has to be a positive number.
The project consists of two classes, WGraph_DS and WGraph_Algo.

The class WGraph_DS implements the interface of weighted_graph and 
support several operations applicable on an undirectional weighted graph, such as: 
adding or removing a vertex or an edge from the graph, returning a pointer to the collection of the vertexes, 
or the collection of the neighbors of a particular vertex, and also, the number of the vertexes 
or the edges in the graph,and more.

The class Wgraph_Algo implements the interface of weighted_graph_algorithms and represents the algorithms 
which applicable on an undirectional weighted graph. The class includes several algorithms such as:
1.copy- a deep copy of the graph.
2.init(graph)- init the graph on which this set of algorithms operates on.
3.getGraph()- returns the underlying graph of which this class works.
4.isConnected()- checking of the graph is one connected component.
5.double shortestPathDist(int src, int dest)- returns the smallest distance between src and dest.
6.List shortestPath(int src, int dest)- returns the shortest path with the lowest distance 
between src and dest.
7.save (file).
8.load(file).

attached herein two tests, one for every class
The WGraph_DSTest examines the integrity of the functions that this class support, in basic cases, such as: 
checking if the number of vertexes or edges in the graph is correct, etc. Before every function,
 a small graph has been built, a graph with 9 vertexes and 19 edges with weights.
The WGraph_AlgoTest examines the integrity of the algorithms that this class support, 
on both basic and extreme cases, and also builds a graph with a million vertexes and ten million edges, 
with the timeout of 4 seconds.
To use those tests, you need to have the version of JUnit5. 
For using my codes, write on your terminal the command:
<<<<<<< HEAD
(your new folder’s address) git clone https://github.com/ShiraAnaki130/Ex1_OOP.git 
=======
(your new folder's address) git clone https://github.com/ShiraAnaki130/Ex1_OOP.git 
>>>>>>> f6202694a633ad3c8cb8e83329760ac08f7ccc52
In addition, need to have eclipse, because the programming language is Java.
 Good Luck!
