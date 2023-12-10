import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.AbstractMap.SimpleEntry;
import java.util.Set;
import java.util.HashSet;

public class PipeMaze {
    static List<String> maze = new ArrayList<>();
    static Set<Point> visited = new HashSet<>();
    static Point start = new Point(0, 0);
    
    public static void main(String[] args) {
        String filePath = "input.txt";
        File file = new File(filePath);

        readInput(file, filePath);
        bfs();
    }

    private static void readInput(File file, String filePath) {
        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                maze.add(line);

                if (line.contains("S")) {
                    start = new Point(maze.size() - 1, line.indexOf("S"));
                }
            }
            
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        }
    }

    private static void bfs() {
        int maxSteps = 0;
        Queue<SimpleEntry<Point, Integer>> queue = new LinkedList<>();
        visited.add(start);
        checkInitialPipes(queue);

        while(!queue.isEmpty()) {
            Point current = queue.peek().getKey();
            int steps = queue.poll().getValue();
            int row = current.x;
            int col = current.y;
            
            if (visited.contains(current)) {
                continue;
            }
            
            maxSteps = Math.max(maxSteps, steps);
            visited.add(current);

            switch (maze.get(row).charAt(col)) {
                case '7':
                    queue.add(new SimpleEntry<>(new Point(row + 1, col), steps + 1));
                    queue.add(new SimpleEntry<>(new Point(row, col - 1), steps + 1));
                    break;
                case 'J':
                    queue.add(new SimpleEntry<>(new Point(row - 1, col), steps + 1));
                    queue.add(new SimpleEntry<>(new Point(row, col - 1), steps + 1));
                    break;
                case 'L':
                    queue.add(new SimpleEntry<>(new Point(row - 1, col), steps + 1));
                    queue.add(new SimpleEntry<>(new Point(row, col + 1), steps + 1));
                    break;
                case 'F':
                    queue.add(new SimpleEntry<>(new Point(row + 1, col), steps + 1));
                    queue.add(new SimpleEntry<>(new Point(row, col + 1), steps + 1));
                    break;
                case '|':
                    queue.add(new SimpleEntry<>(new Point(row - 1, col), steps + 1));
                    queue.add(new SimpleEntry<>(new Point(row + 1, col), steps + 1));
                    break;
                case '-':
                    queue.add(new SimpleEntry<>(new Point(row, col - 1), steps + 1));
                    queue.add(new SimpleEntry<>(new Point(row, col + 1), steps + 1));
                    break;
                default:
                    break;
            }
        }
        
        System.out.println(maxSteps);
    }


    private static void checkInitialPipes(Queue<SimpleEntry<Point, Integer>> queue) {
        int row = start.x;
        int col = start.y;

        if (row - 1 >= 0 && (maze.get(row - 1).charAt(col) == 'F' || maze.get(row - 1).charAt(col) == '7' || maze.get(row - 1).charAt(col) == '|')) {
            queue.add(new SimpleEntry<>(new Point(row - 1, col), 1));
        }

        if (row + 1 < maze.size() && (maze.get(row + 1).charAt(col) == 'J' || maze.get(row + 1).charAt(col) == 'L' || maze.get(row + 1).charAt(col) == '|')) {
            queue.add(new SimpleEntry<>(new Point(row + 1, col), 1));
        }

        if (col - 1 >= 0 && (maze.get(row).charAt(col - 1) == 'F' || maze.get(row).charAt(col - 1) == 'L' || maze.get(row).charAt(col - 1) == '-')) {
            queue.add(new SimpleEntry<>(new Point(row, col - 1), 1));
        }

        if (col + 1 < maze.get(row).length() && (maze.get(row).charAt(col + 1) == 'J' || maze.get(row).charAt(col + 1) == '7' || maze.get(row).charAt(col + 1) == '-')) {
            queue.add(new SimpleEntry<>(new Point(row, col + 1), 1));
        }
    }
}
