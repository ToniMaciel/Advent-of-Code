import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LavaductLagoon2 {
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
            long perimenter = 0;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] tokens = line.split(" ");
                Instruction instruction = new Instruction(tokens[2]);
                Point next = processNewPoint(actual, instruction);
                points.add(next);
                perimenter += instruction.amount;
                actual = next;
            }

            long area = shoelaceFormula(points);
            System.out.println(perimenter + pickTheorem(perimenter, area));
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + INPUT_FILE);
        }

    }

    private static Point processNewPoint(Point actual, Instruction instruction) {
        Point next = new Point(actual.x, actual.y);
        switch (instruction.move) {
            case 'U':
                next.x -= instruction.amount;    
                break;
            case 'D':
                next.x += instruction.amount;    
                break;
            case 'L':
                next.y -= instruction.amount;       
                break;
            case 'R':
                next.y += instruction.amount;            
                break;
        }
        return next;
    }

    private static long shoelaceFormula(List<Point> vertices) {
        long area = 0;
        
        for (int i = 0; i < vertices.size(); i++) {
            Point current = vertices.get(i);
            Point next = vertices.get((i + 1) % vertices.size());
            area += (current.x * next.y) - (current.y * next.x);
        }

        return Math.abs(area / 2);
    }

    private static long pickTheorem(long boundaryPoints, long area) {
        long innerPoints = area + 1 - (boundaryPoints / 2);
        return innerPoints;
    }
}
