import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Wasteland {
    static String moves;
    static Map<String, String[]> network = new HashMap<>();
    public static void main(String[] args) {
        String filePath = "input.txt";
        File file = new File(filePath);

        readInput(file, filePath);
        calculatePaths();
    }

    private static void readInput(File file, String filePath) {
        try {
            Scanner scanner = new Scanner(file);
            moves = scanner.next();
            scanner.nextLine();
            scanner.nextLine();

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] edge = line.split("=");
                String[] paths = edge[1].trim().split(",");

                paths[0] = paths[0].trim().replace("(", "");
                paths[1] = paths[1].trim().replace(")", "");

                network.put(edge[0].trim(), paths);
            }
            
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        }
    }

    private static void calculatePaths() {
        String start = "AAA";
        int steps = 0;

        while (!start.equals("ZZZ")) {
            if (moves.charAt(steps % moves.length()) == 'L')
                start = network.get(start)[0];
            else
                start = network.get(start)[1];
            steps++;
        }

        System.out.println(steps);
    }
}
