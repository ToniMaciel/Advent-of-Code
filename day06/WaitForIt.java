import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.IntStream;

public class WaitForIt {
    static ArrayList<Integer> times = new ArrayList<>();
    static ArrayList<Integer> distances = new ArrayList<>();

    public static void main(String[] args) {
        String filePath = "input.txt";
        File file = new File(filePath);

        readInput(file, filePath);
        calculateTimes();
    }

    private static void readInput(File file, String filePath) {
        try {
            Scanner scanner = new Scanner(file);
            scanner.next();
            
            while (scanner.hasNextInt()) {
                times.add(scanner.nextInt());
            }
            
            scanner.nextLine();
            scanner.next();

            while (scanner.hasNextInt()) {
                distances.add(scanner.nextInt());
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        }
    }

    private static void calculateTimes() {
        IntStream.range(0, times.size()).map(i -> {
            int time = times.get(i);
            return (int) IntStream.rangeClosed(0, time).filter((t) -> ((time * t) - (t * t)) > distances.get(i)).count();
        }).reduce((a, b) -> a * b).ifPresent(System.out::println);
    }
}