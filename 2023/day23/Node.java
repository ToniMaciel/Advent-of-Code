import java.util.ArrayList;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;


public class Node {
    public int x;
    public int y;
    public List<SimpleEntry<Node, Integer>> edges = new ArrayList<>();

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void addEdge(Node path, int distance) {
        SimpleEntry<Node, Integer> edge = new SimpleEntry<>(path, distance);
        if (!edges.contains(edge)) {
            edges.add(edge);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        final Node other = (Node) obj;
        if (this.x != other.x || this.y != other.y) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return x * 1000 + y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
