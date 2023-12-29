import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Trebuchet {
    static ArrayList<String> lines = new ArrayList<>();

    public static void main(String[] args) {
        String filePath = "input.txt";
        File file = new File(filePath);

        readInput(file, filePath);
        calculateCalibration();
    }

    private static void readInput(File file, String filePath) {
        try {
            Scanner scanner = new Scanner(file);
            
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        }
    }

    private static void calculateCalibration() {
        int answer = lines.stream().map(string -> {
            int first = string.chars().filter(c -> c >= '0' && c <= '9').findFirst().getAsInt();
            int last = string.chars().filter(c -> c >= '0' && c <= '9').reduce((f, second) -> second).getAsInt();

            return first*10 + last;
        }).reduce(0, (first, second) -> first + second);

        System.out.println(answer);
    }
}