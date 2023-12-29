import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Trebuchet2 {
    private static ArrayList<String> lines = new ArrayList<>();
    private static String[] numbers = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
    private static Map<String, Integer> numberMap = Map.of(
        "one", 1,
        "two", 2,
        "three", 3,
        "four", 4,
        "five", 5,
        "six", 6,
        "seven", 7,
        "eight", 8,
        "nine", 9
    );

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
            int first = findFirstInt(string);
            int last = findLastInt(string);

            return first*10 + last;
        }).reduce(0, (first, second) -> first + second);

        System.out.println(answer);
    }
    
    private static int findFirstInt(String string) {
        int index = Integer.MAX_VALUE;
        int value = 0;
        
        for (String number : numbers) {
            int temp = string.indexOf(number);
            if (temp != -1 && temp < index) {
                index = temp;
                value = numberMap.get(number);
            }
        }

        for (int i = 0; i < index && i < string.length(); i++) {
            if (string.charAt(i) >= '0' && string.charAt(i) <= '9') {
                return string.charAt(i) - '0';
            }
        }

        return value;
    }

    private static int findLastInt(String string) {
        int index = Integer.MIN_VALUE;
        int value = 0;
        
        for (String number : numbers) {
            int temp = string.lastIndexOf(number);
            if (temp != -1 && temp > index) {
                index = temp;
                value = numberMap.get(number);
            }
        }

        for (int i = Math.max(index, 0); i < string.length(); i++) {
            if (string.charAt(i) >= '0' && string.charAt(i) <= '9') {
                value = string.charAt(i) - '0';
            }
        }

        return value;
    }
}