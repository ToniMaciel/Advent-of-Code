import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class LensLibrary2 {
    private static Map<String, Integer>[] map = new LinkedHashMap[256];
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
        for (String word : words) {
            placeInMap(word);
        }

        int sum = 0, hashIndex = 1;
        for (Map<String, Integer> m : map) {
            if (m != null) {
                int position = 1;
                for (Map.Entry<String, Integer> entry : m.entrySet()) {
                    sum += hashIndex * position * entry.getValue();
                    position++;
                }
            }
            hashIndex++;
        }

        System.out.println(sum);
    }

    private static void placeInMap(String word) {
        if (word.contains("=")) {
            String[] split = word.split("=");
            int hash = hashValue(split[0]);
            if (map[hash] == null) {
                map[hash] = new LinkedHashMap<>();
            }
            map[hash].put(split[0], Integer.parseInt(split[1]));
        } else {
            word = word.substring(0, word.length() - 1);
            int hash = hashValue(word);
            if (map[hash] == null) {
                map[hash] = new LinkedHashMap<>();
            }
            map[hash].remove(word);
        }
    }

    private static int hashValue(String word) {
        return word.chars().reduce(0, (a, b) -> b = ((a + b) * 17) % 256);
    }
}
