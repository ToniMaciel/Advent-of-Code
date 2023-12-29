import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.AbstractMap.SimpleEntry;
import java.util.Set;
import java.util.HashSet;

public class LongWalk2 {
    private static List<char[]> map = new ArrayList<>();
    private static Map<String, Node> paths = new HashMap<>();
    private static int longestPath = -1;
    private static final int X = 0;
    private static final int Y = 1;
    private static final int[] xDirections = { 1, -1, 0, 0 };
    private static final int[] yDirections = { 0, 0, 1, -1 };

    public static void main(String[] args) {
        String filePath = "input.txt";
        File file = new File(filePath);

        readInput(file, filePath);

        Node initialNode = new Node(X, Y);
        Connection initialConnection = new Connection(X, Y, X + 1, Y, 1);
        createGraph(initialNode, initialConnection);

        findLongestPath(initialNode, new HashSet<>(), 0);
        System.out.println(longestPath);
    }

    private static void readInput(File file, String filePath) {

        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                map.add(line.toCharArray());
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        }

    }

    private static void createGraph(Node source, Connection nodeSource) {
        List<Connection> neighbours = new ArrayList<>();
        Connection aux = new Connection(0, 0, 0);
        neighbours.add(nodeSource);
        
        while (neighbours.size() == 1) {
            aux = neighbours.get(0);
            neighbours = new ArrayList<>();
            for (int i = 0; i < xDirections.length; i++) {
                int newX = aux.x + xDirections[i];
                int newY = aux.y + yDirections[i];
                if (newX >= 0 && newX < map.size() && newY >= 0 && newY < map.get(newX).length && map.get(newX)[newY] != '#') {
                    if (newX == aux.sourceX && newY == aux.sourceY) {
                        continue;
                    }
                    neighbours.add(new Connection(aux.x, aux.y, newX, newY, aux.distance + 1));
                }
            }
        }

        Node newNode = new Node(aux.x, aux.y);
        String key = aux.x + "," + aux.y;

        if (paths.containsKey(key)) {
            source.addEdge(paths.get(key), aux.distance);
            paths.get(key).addEdge(source, aux.distance);
            return;
        } 
        
        source.addEdge(newNode, aux.distance);
        newNode.addEdge(source, aux.distance);
        paths.put(key, newNode);
        
        for (int i = 0; i < neighbours.size(); i++) {
            Connection neighbour = neighbours.get(i);
            neighbour.distance = 1;
            createGraph(newNode, neighbour);
        }
    }

    private static void findLongestPath(Node node, Set<Node> visited, int distance) {
        if (visited.contains(node)) {
            return;
        }

        if (node.x == map.size() - 1) {
            longestPath = Math.max(longestPath, distance);
        }

        for (SimpleEntry<Node, Integer> edge : node.edges) {
            visited.add(node);
            findLongestPath(edge.getKey(), visited, distance + edge.getValue());
            visited.remove(node);
        }
    }
}
