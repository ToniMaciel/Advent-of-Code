import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.stream.LongStream;

public class WaitForIt2 {
    static long time = 0;
    static long distance = 0;

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

            StringBuilder timeString = new StringBuilder(); 
            while (scanner.hasNextInt()) {
                timeString.append(scanner.next());
            }

            time = Long.parseLong(timeString.toString());
            
            scanner.nextLine();
            scanner.next();
            
            StringBuilder distanceString = new StringBuilder();
            while (scanner.hasNextInt()) {
                distanceString.append(scanner.next());
            }

            distance = Long.parseLong(distanceString.toString());
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        }
    }

    private static void calculateTimes() {
        long count = LongStream.rangeClosed(0, time)
            .filter(t -> ((time * t) - (t * t)) > distance)
            .count();
        System.out.println(count);
    }
}