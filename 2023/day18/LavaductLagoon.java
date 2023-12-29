import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LavaductLagoon {
    private static final String INPUT_FILE = "input.txt";

    public static void main(String[] args) {
        File file = new File(INPUT_FILE);

        processInput(file);
    }

    private static void processInput(File file) {
        
        try {
            Scanner scanner = new Scanner(file);
            Point actual = new Point(0, 0);
            List<Point> points = new ArrayList<>();
            points.add(actual);
            int perimenter = 0;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] tokens = line.split(" ");
                Point next = processNewPoint(actual, tokens[0], Integer.parseInt(tokens[1]));
                points.add(next);
                perimenter += Integer.parseInt(tokens[1]);
                actual = next;
            }

            int area = shoelaceFormula(points);
            System.out.println(perimenter + pickTheorem(perimenter, area));
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + INPUT_FILE);
        }

    }

    private static Point processNewPoint(Point actual, String move, int amount) {
        Point next = new Point(actual.x, actual.y);
        switch (move) {
            case "U":
                next.x -= amount;    
                break;
            case "D":
                next.x += amount;    
                break;
            case "L":
                next.y -= amount;       
                break;
            case "R":
                next.y += amount;            
                break;
        }
        return next;
    }

    private static int shoelaceFormula(List<Point> vertices) {
        int area = 0;
        
        for (int i = 0; i < vertices.size(); i++) {
            Point current = vertices.get(i);
            Point next = vertices.get((i + 1) % vertices.size());
            area += (current.x * next.y) - (current.y * next.x);
        }

        return Math.abs(area / 2);
    }

    private static int pickTheorem(int boundaryPoints, int area) {
        int innerPoints = area + 1 - (boundaryPoints / 2);
        return innerPoints;
    }
}
