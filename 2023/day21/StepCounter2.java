import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class StepCounter2 {
    private static List<String> map = new ArrayList<>();
    private static final int STEPS_COUNTS = 26501365;
    private static int startRow = 0;
    private static int startCol = 0;

    public static void main(String[] args) {
        String filePath = "input.txt";
        File file = new File(filePath);

        readInput(file, filePath);
        countPlaces();
    }

    private static void readInput(File file, String filePath) {
        
        try {
            Scanner scanner = new Scanner(file);
            int row = 0;

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (line.contains("S")) {
                    startRow = row;
                    startCol = line.indexOf("S");
                }
                map.add(line);
                row++;
            }


            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        }

    }

    private static void countPlaces() {
        int[] xMoves = { 1, 0, -1, 0 };
        int[] yMoves = { 0, 1, 0, -1 };
        List<Integer> necessaryStepsToQuadratic = new ArrayList<>();

        Queue<Point2> points = new LinkedList<>();
        points.add(new Point2(startRow, startCol));

        int target = STEPS_COUNTS % (map.size());

        for (int i = 0; i < target + 2 * map.size(); i++) {
            Set<Point2> seenPoints = new HashSet<>();
            while (!points.isEmpty()) {
                Point2 currentPoint = points.poll();
                for (int j = 0; j < xMoves.length; j++) {
                    int nextRow = (currentPoint.row + xMoves[j] + map.size()) % map.size();
                    int nextCol = (currentPoint.col + yMoves[j] + map.get(nextRow).length()) % map.get(nextRow).length();
                    if (map.get(nextRow).charAt(nextCol) != '#') {
                        Point2 nextPoint = new Point2(nextRow, nextCol, currentPoint.realRow + xMoves[j], currentPoint.realCol + yMoves[j]);
                        seenPoints.add(nextPoint);
                    }
                }
            }

            points.addAll(seenPoints);
            
            if (i == target - 1 || i == target - 1 + map.size() || i == target - 1 + 2*map.size()) {
                necessaryStepsToQuadratic.add(points.size());
            }
        }

        System.out.println(lagrangeInterpolation(necessaryStepsToQuadratic));
    }

    private static long lagrangeInterpolation(List<Integer> values) {
        long a = values.get(0)/2 - values.get(1) + values.get(2)/2;
        long b = -3*values.get(0)/2 + 2*values.get(1) - values.get(2)/2;
        long c = values.get(0);

        long x = STEPS_COUNTS / map.size();

        return a*x*x + b*x + c;
    }
}
