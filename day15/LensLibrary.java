import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class LensLibrary {
    
    public static void main(String[] args) {
        String filePath = "input.txt";
        File file = new File(filePath);

        processInput(file, filePath);
    }

    private static void processInput(File file, String filePath) {
        
        try {
            Scanner scanner = new Scanner(file);
            String line = scanner.nextLine();
            scanner.close();
            calculateHash(line.split(","));
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        }

    }

    private static void calculateHash(String[] words) {
        int sum = Arrays.stream(words).map(word -> hashValue(word)).reduce(0, Integer::sum);
        System.out.println(sum);
    }

    private static int hashValue(String word) {
        return word.chars().reduce(0, (a, b) -> b = ((a + b) * 17) % 256);
    }
}
