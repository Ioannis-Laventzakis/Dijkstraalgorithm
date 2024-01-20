import java.util.HashSet;
import java.util.Set;

public class Graph {

    private Set<Node> nodes = new HashSet<>(); // HashSet is a Set that does not allow duplicate elements

    public void addNode(Node nodeA) { // addNode() method
        nodes.add(nodeA);
    }

    // getters and setters

    public Set<Node> getNodes() {
        return nodes;
    }

    public void setNodes(Set<Node> nodes) {
        this.nodes = nodes;

    }
}