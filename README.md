## It's for me to read it and learn 
### is not my project I copy is from
https://www.baeldung.com/java-dijkstra#java-implementation

Here’s a table that summarizes the iterations that were performed during evaluation steps:

Iteration	Unsettled	Settled	EvaluationNode	A	B	C	D	E	F
1	A	–	A	0	A-10	A-15	X-∞	X-∞	X-∞
2	B, C	A	B	0	A-10	A-15	B-22	X-∞	B-25
3	C, F, D	A, B	C	0	A-10	A-15	B-22	C-25	B-25
4	D, E, F	A, B, C	D	0	A-10	A-15	B-22	D-24	D-23
5	E, F	A, B, C, D	F	0	A-10	A-15	B-22	D-24	D-23
6	E	A, B, C, D, F	E	0	A-10	A-15	B-22	D-24	D-23
Final	–	ALL	NONE	0	A-10	A-15	B-22	D-24	D-23


The notation B-22, for example, means that node B is the immediate predecessor, with a total distance of 22 from node A.

Finally, we can calculate the shortest paths from node A are as follows:

Node B : A –> B (total distance = 10)
Node C : A –> C (total distance = 15)
Node D : A –> B –> D (total distance = 22)
Node E : A –> B –> D –> E (total distance = 24)
Node F : A –> B –> D –> F (total distance = 23)
3. Java Implementation
   In this simple implementation we will represent a graph as a set of nodes:

public class Graph {

    private Set<Node> nodes = new HashSet<>();
    
    public void addNode(Node nodeA) {
        nodes.add(nodeA);
    }

    // getters and setters 
}
Copy
A node can be described with a name, a LinkedList in reference to the shortestPath, a distance from the source, and an adjacency list named adjacentNodes:


AD
public class Node {

    private String name;
    
    private List<Node> shortestPath = new LinkedList<>();
    
    private Integer distance = Integer.MAX_VALUE;
    
    Map<Node, Integer> adjacentNodes = new HashMap<>();

    public void addDestination(Node destination, int distance) {
        adjacentNodes.put(destination, distance);
    }
 
    public Node(String name) {
        this.name = name;
    }
    
    // getters and setters
}
Copy
The adjacentNodes attribute is used to associate immediate neighbors with edge length. This is a simplified implementation of an adjacency list, which is more suitable for the Dijkstra algorithm than the adjacency matrix.

As for the shortestPath attribute, it is a list of nodes that describes the shortest path calculated from the starting node.

By default, all node distances are initialized with Integer.MAX_VALUE to simulate an infinite distance as described in the initialization step.

Now, let’s implement the Dijkstra algorithm:

public static Graph calculateShortestPathFromSource(Graph graph, Node source) {
source.setDistance(0);

    Set<Node> settledNodes = new HashSet<>();
    Set<Node> unsettledNodes = new HashSet<>();

    unsettledNodes.add(source);

    while (unsettledNodes.size() != 0) {
        Node currentNode = getLowestDistanceNode(unsettledNodes);
        unsettledNodes.remove(currentNode);
        for (Entry < Node, Integer> adjacencyPair: 
          currentNode.getAdjacentNodes().entrySet()) {
            Node adjacentNode = adjacencyPair.getKey();
            Integer edgeWeight = adjacencyPair.getValue();
            if (!settledNodes.contains(adjacentNode)) {
                calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                unsettledNodes.add(adjacentNode);
            }
        }
        settledNodes.add(currentNode);
    }
    return graph;
}
Copy
The getLowestDistanceNode() method, returns the node with the lowest distance from the unsettled nodes set, while the calculateMinimumDistance() method compares the actual distance with the newly calculated one while following the newly explored path:

private static Node getLowestDistanceNode(Set < Node > unsettledNodes) {
Node lowestDistanceNode = null;
int lowestDistance = Integer.MAX_VALUE;
for (Node node: unsettledNodes) {
int nodeDistance = node.getDistance();
if (nodeDistance < lowestDistance) {
lowestDistance = nodeDistance;
lowestDistanceNode = node;
}
}
return lowestDistanceNode;
}
Copy
private static void CalculateMinimumDistance(Node evaluationNode,
Integer edgeWeigh, Node sourceNode) {
Integer sourceDistance = sourceNode.getDistance();
if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
evaluationNode.setDistance(sourceDistance + edgeWeigh);
LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
shortestPath.add(sourceNode);
evaluationNode.setShortestPath(shortestPath);
}
}
Copy
Now that all the necessary pieces are in place, let’s apply the Dijkstra algorithm on the sample graph being the subject of the article:

Node nodeA = new Node("A");
Node nodeB = new Node("B");
Node nodeC = new Node("C");
Node nodeD = new Node("D");
Node nodeE = new Node("E");
Node nodeF = new Node("F");

nodeA.addDestination(nodeB, 10);
nodeA.addDestination(nodeC, 15);

nodeB.addDestination(nodeD, 12);
nodeB.addDestination(nodeF, 15);

nodeC.addDestination(nodeE, 10);

nodeD.addDestination(nodeE, 2);
nodeD.addDestination(nodeF, 1);

nodeF.addDestination(nodeE, 5);

Graph graph = new Graph();

graph.addNode(nodeA);
graph.addNode(nodeB);
graph.addNode(nodeC);
graph.addNode(nodeD);
graph.addNode(nodeE);
graph.addNode(nodeF);

graph = Dijkstra.calculateShortestPathFromSource(graph, nodeA);
Copy
After calculation, the shortestPath and distance attributes are set for each node in the graph, we can iterate through them to verify that the results match exactly what was found in the previous section.