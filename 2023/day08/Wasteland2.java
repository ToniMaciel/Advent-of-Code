import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Wasteland2 {
    static String moves;
    static Map<String, String[]> network = new HashMap<>();
    static ArrayList<String> start = new ArrayList<>();
    static ArrayList<Integer> stepsToDestination = new ArrayList<>();
    public static void main(String[] args) {
        String filePath = "input.txt";
        File file = new File(filePath);

        readInput(file, filePath);
        processInput();
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

                String source = edge[0].trim();

                network.put(source, paths);

                if (source.charAt(2) == 'A') {
                    start.add(source);
                }
            }
            
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        }
    }

    private static void processInput() {
        long totalSteps =  start.stream().map(source -> {
            String current = source;
            long steps = 0;

            while (current.charAt(2) != 'Z') {
                if (moves.charAt((int) (steps % moves.length())) == 'L')
                    current = network.get(current)[0];
                else
                    current = network.get(current)[1];
                steps++;
            }

            return steps;
        }).reduce(1L, (a, b) -> lcm(a, b)).longValue();       
        
        System.out.println(totalSteps);
    }

    private static long lcm(long a, long b) {
        return a * (b / gcd(a, b));
    }

    private static long gcd(long a, long b) {
        if (b == 0) {
            return a;
        }

        return gcd(b, a % b);
    }
}
