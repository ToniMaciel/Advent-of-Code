import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class FloorLava2 {
    enum Direction {
        UP, DOWN, LEFT, RIGHT
    };

    private static final String INPUT_FILE = "input.txt";
    private static List<char[]> grid = new ArrayList<>();
    private static Set<String> visited = new HashSet<>();
    private static Set<String> energized = new HashSet<>();

    
    public static void main(String[] args) {
        File file = new File(INPUT_FILE);

        readInput(file);
        int ans = -1;

        for (int i = 0; i < grid.size(); i++) {
            createBeam(i, 0, Direction.RIGHT);
            ans = Math.max(ans, energized.size());
            energized.clear();
            visited.clear();
        }

        for (int i = 0; i < grid.size(); i++) {
            createBeam(i, grid.get(i).length - 1, Direction.LEFT);
            ans = Math.max(ans, energized.size());
            energized.clear();
            visited.clear();
        }

        for (int i = 0; i < grid.get(0).length; i++) {
            createBeam(0, i, Direction.DOWN);
            ans = Math.max(ans, energized.size());
            energized.clear();
            visited.clear();
        }
        
        for (int i = 0; i < grid.get(0).length; i++) {
            createBeam(grid.size() - 1, i, Direction.UP);
            ans = Math.max(ans, energized.size());
            energized.clear();
            visited.clear();
        }

        System.out.println(ans);
    }

    private static void readInput(File file) {
        
        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                grid.add(line.toCharArray());
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + INPUT_FILE);
        }

    }

    private static void createBeam(int i, int j, FloorLava2.Direction direction) {
        Flow flow = new Flow(i, j, direction.ordinal());
        Queue<Flow> queue = new LinkedList<>();
        queue.add(flow);

        while (!queue.isEmpty()) {
            Flow current = queue.poll();
            i = current.x;
            j = current.y;
            direction = Direction.values()[current.dir];

            if (i < 0 || i >= grid.size() || j < 0 || j >= grid.get(i).length) {
                continue;
            }
    
            if (isVisited(i, j, direction)) {
                continue;
            }
    
            visited.add(code(i, j, direction));
            energized.add(codeCoordinates(i, j));
    
            char node = grid.get(i)[j];
    
            switch (direction) {
                case UP:
                    if (node == '-') {
                        queue.add(new Flow(i, j - 1, Direction.LEFT.ordinal()));
                        queue.add(new Flow(i, j + 1, Direction.RIGHT.ordinal()));
                    } else if (node == '/') {
                        queue.add(new Flow(i, j + 1, Direction.RIGHT.ordinal()));
                    } else if (node == '\\') {
                        queue.add(new Flow(i, j - 1, Direction.LEFT.ordinal()));
                    } else {
                        queue.add(new Flow(i - 1, j, Direction.UP.ordinal()));
                    } 
                    break;
                case DOWN:
                    if (node == '-') {
                        queue.add(new Flow(i, j - 1, Direction.LEFT.ordinal()));
                        queue.add(new Flow(i, j + 1, Direction.RIGHT.ordinal()));
                    } else if (node == '/') {
                        queue.add(new Flow(i, j - 1, Direction.LEFT.ordinal()));
                    } else if (node == '\\') {
                        queue.add(new Flow(i, j + 1, Direction.RIGHT.ordinal()));
                    } else {
                        queue.add(new Flow(i + 1, j, Direction.DOWN.ordinal()));
                    }   
                    break;
                case LEFT:
                    if (node == '|') {
                        queue.add(new Flow(i - 1, j, Direction.UP.ordinal()));
                        queue.add(new Flow(i + 1, j, Direction.DOWN.ordinal()));
                    } else if (node == '/') {
                        queue.add(new Flow(i + 1, j, Direction.DOWN.ordinal()));
                    } else if (node == '\\') {
                        queue.add(new Flow(i - 1, j, Direction.UP.ordinal()));
                    } else {
                        queue.add(new Flow(i, j - 1, Direction.LEFT.ordinal()));
                    }
                    break;
                case RIGHT:
                    if (node == '|') {
                        queue.add(new Flow(i - 1, j, Direction.UP.ordinal()));
                        queue.add(new Flow(i + 1, j, Direction.DOWN.ordinal()));
                    } else if (node == '/') {
                        queue.add(new Flow(i - 1, j, Direction.UP.ordinal()));
                    } else if (node == '\\') {
                        queue.add(new Flow(i + 1, j, Direction.DOWN.ordinal()));
                    } else {
                        queue.add(new Flow(i, j + 1, Direction.RIGHT.ordinal()));
                    }
                    break;
            }
        }
    }

    private static boolean isVisited(int i, int j, FloorLava2.Direction direction) {
        return visited.contains(code(i, j, direction));
    }

    private static String codeCoordinates(int i, int j) {
        return (i + ", " + j);
    }

    private static String code(int i, int j, FloorLava2.Direction direction) {
        return codeCoordinates(i, j) + " - " + direction;
    }
}
