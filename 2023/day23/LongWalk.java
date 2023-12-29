import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.PriorityQueue;

public class LongWalk {
    private static List<String> map = new ArrayList<>();
    private static final int X = 0;
    private static final int Y = 1;

    public static void main(String[] args) {
        String filePath = "input.txt";
        File file = new File(filePath);

        readInput(file, filePath);
        dikjstra();
    }

    private static void readInput(File file, String filePath) {
        
        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                map.add(line);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        }

    }

    private static void dikjstra() {
        int biggestDistance = Integer.MIN_VALUE;
        Queue<Connection> queue = new PriorityQueue<>();
        queue.add(new Connection(X, Y, 0));

        while (!queue.isEmpty()) {
            Connection current = queue.poll();
            int x = current.x;
            int y = current.y;
            int distance = current.distance;
            char currentChar = map.get(x).charAt(y);

            if (x == map.size() - 1) {
                biggestDistance = Math.max(biggestDistance, Math.abs(distance));
                continue;
            }

            if (x + 1 < map.size() && map.get(x + 1).charAt(y) != '#' && (currentChar == '.' || currentChar == 'v')) {
                if (current.sourceX != x + 1 || current.sourceY != y) {
                    queue.add(new Connection(x, y, x + 1, y, distance - 1));
                }
            }

            if (x - 1 >= 0 && map.get(x - 1).charAt(y) != '#' && (currentChar == '.' || currentChar == '^')) {
                if (current.sourceX != x - 1 || current.sourceY != y) {
                    queue.add(new Connection(x, y, x - 1, y, distance - 1));
                }
            }

            if (y + 1 < map.get(x).length() && map.get(x).charAt(y + 1) != '#' && (currentChar == '.' || currentChar == '>')) {
                if (current.sourceX != x || current.sourceY != y + 1) {
                    queue.add(new Connection(x, y, x, y + 1, distance - 1));
                }
            }

            if (y - 1 >= 0 && map.get(x).charAt(y - 1) != '#' && (currentChar == '.' || currentChar == '<')) {
                if (current.sourceX != x || current.sourceY != y - 1) {
                    queue.add(new Connection(x, y, x, y - 1, distance - 1));
                }
            }
        }

        System.out.println(biggestDistance);
    }
}
