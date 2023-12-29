import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;

public class PipeMaze2 {
    static List<String> maze = new ArrayList<>();
    static Set<Point> visited = new HashSet<>();
    static List<Point> vertices = new ArrayList<>();
    static Point start = new Point(0, 0);

    public static void main(String[] args) {
        String filePath = "input.txt";
        File file = new File(filePath);

        readInput(file, filePath);
        clockwiseLoop();
        calculateInnerPoint();
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

    private static void clockwiseLoop() {
        vertices.add(start);
        Point current = checkInitialPipe();
        Point previous = start;

        while(!current.equals(start)) {
            int row = current.x;
            int col = current.y;

            vertices.add(current);

            switch (maze.get(row).charAt(col)) {
                case '7':
                    if (previous.y == col - 1) {
                        current = new Point(row + 1, col);
                    } else {
                        current = new Point(row, col - 1);
                    }
                    break;
                case 'J':
                    if (previous.x == row - 1) {
                        current = new Point(row, col - 1);
                    } else {
                        current = new Point(row - 1, col);
                    }
                    break;
                case 'L':
                    if (previous.x == row - 1) {
                        current = new Point(row, col + 1);
                    } else {
                        current = new Point(row - 1, col);
                    }
                    break;
                case 'F':
                    if (previous.y == col + 1) {
                        current = new Point(row + 1, col);
                    } else {
                        current = new Point(row, col + 1);
                    }
                    break;
                case '|':
                    if (previous.x == row - 1) {
                        current = new Point(row + 1, col);
                    } else {
                        current = new Point(row - 1, col);
                    }
                    break;
                case '-':
                    if (previous.y == col - 1) {
                        current = new Point(row, col + 1);
                    } else {
                        current = new Point(row, col - 1);
                    }
                    break;
                default:
                    break;
            }

            previous = vertices.get(vertices.size() - 1);
        }
    }


    private static Point checkInitialPipe() {
        int row = start.x;
        int col = start.y;

        if (row - 1 >= 0 && (maze.get(row - 1).charAt(col) == 'F' || maze.get(row - 1).charAt(col) == '7' || maze.get(row - 1).charAt(col) == '|')) {
            return new Point(row - 1, col);
        }
        
        else if (col + 1 < maze.get(row).length() && (maze.get(row).charAt(col + 1) == 'J' || maze.get(row).charAt(col + 1) == '7' || maze.get(row).charAt(col + 1) == '-')) {
            return new Point(row, col + 1);
        }

        else if (row + 1 < maze.size() && (maze.get(row + 1).charAt(col) == 'J' || maze.get(row + 1).charAt(col) == 'L' || maze.get(row + 1).charAt(col) == '|')) {
            return new Point(row + 1, col);
        }

        else {
            return new Point(row, col - 1);
        }

    }

    private static void calculateInnerPoint() {
        int area = shoelaceFormula();
        System.out.println(pickTheorem(area));
    }

    private static int shoelaceFormula() {
        int area = 0;
        
        for (int i = 0; i < vertices.size(); i++) {
            Point current = vertices.get(i);
            Point next = vertices.get((i + 1) % vertices.size());
            area += (current.x * next.y) - (current.y * next.x);
        }

        return Math.abs(area / 2);
    }

    private static int pickTheorem(int area) {
        int boundaryPoints = vertices.size();
        int innerPoints = area + 1 - (boundaryPoints / 2);
        return innerPoints;
    }
}
