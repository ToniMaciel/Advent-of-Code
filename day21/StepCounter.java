import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class StepCounter {
    private static List<String> map = new ArrayList<>();
    private static final int STEPS_COUNTS = 64;
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
        int numberOfPlaces = 0;

        Queue<Point> points = new LinkedList<>();
        points.add(new Point(startRow, startCol));

        for (int i = 0; i < STEPS_COUNTS; i++) {
            Set<Point> seenPoints = new HashSet<>();
            while (!points.isEmpty()) {
                Point currentPoint = points.poll();
                for (int j = 0; j < xMoves.length; j++) {
                    int nextRow = currentPoint.row + xMoves[j];
                    int nextCol = currentPoint.col + yMoves[j];
                    if (nextRow >= 0 && nextRow < map.size() && nextCol >= 0 && nextCol < map.get(nextRow).length()
                        && map.get(nextRow).charAt(nextCol) != '#') {
                        Point nextPoint = new Point(nextRow, nextCol);
                        seenPoints.add(nextPoint);
                    }
                }
            }
            numberOfPlaces = seenPoints.size();
            points.addAll(seenPoints);
        }

        System.out.println(numberOfPlaces);
    }
}
