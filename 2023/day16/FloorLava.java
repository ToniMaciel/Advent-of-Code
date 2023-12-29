import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class FloorLava {
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
        createBeam(0, 0, Direction.RIGHT);

        System.out.println(energized.size());
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

    private static void createBeam(int i, int j, FloorLava.Direction direction) {
        if (i < 0 || i >= grid.size() || j < 0 || j >= grid.get(i).length) {
            return;
        }

        if (isVisited(i, j, direction)) {
            return;
        }

        visited.add(code(i, j, direction));
        energized.add(codeCoordinates(i, j));

        char node = grid.get(i)[j];

        switch (direction) {
            case UP:
                if (node == '-') {
                    createBeam(i, j - 1, Direction.LEFT);
                    createBeam(i, j + 1, Direction.RIGHT);
                } else if (node == '/') {
                    createBeam(i, j + 1, Direction.RIGHT);
                } else if (node == '\\') {
                    createBeam(i, j - 1, Direction.LEFT);
                } else {
                    createBeam(i - 1, j, Direction.UP);
                } 
                break;
            case DOWN:
                if (node == '-') {
                    createBeam(i, j - 1, Direction.LEFT);
                    createBeam(i, j + 1, Direction.RIGHT);
                } else if (node == '/') {
                    createBeam(i, j - 1, Direction.LEFT);
                } else if (node == '\\') {
                    createBeam(i, j + 1, Direction.RIGHT);
                } else {
                    createBeam(i + 1, j, Direction.DOWN);
                }   
                break;
            case LEFT:
                if (node == '|') {
                    createBeam(i - 1, j, Direction.UP);
                    createBeam(i + 1, j, Direction.DOWN);
                } else if (node == '/') {
                    createBeam(i + 1, j, Direction.DOWN);
                } else if (node == '\\') {
                    createBeam(i - 1, j, Direction.UP);
                } else {
                    createBeam(i, j - 1, Direction.LEFT);
                }
                break;
            case RIGHT:
                if (node == '|') {
                    createBeam(i - 1, j, Direction.UP);
                    createBeam(i + 1, j, Direction.DOWN);
                } else if (node == '/') {
                    createBeam(i - 1, j, Direction.UP);
                } else if (node == '\\') {
                    createBeam(i + 1, j, Direction.DOWN);
                } else {
                    createBeam(i, j + 1, Direction.RIGHT);
                }
                break;
        }
    }

    private static boolean isVisited(int i, int j, FloorLava.Direction direction) {
        return visited.contains(code(i, j, direction));
    }

    private static String codeCoordinates(int i, int j) {
        return (i + ", " + j);
    }

    private static String code(int i, int j, FloorLava.Direction direction) {
        return codeCoordinates(i, j) + " - " + direction;
    }
}
