import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GearRatiosV2 {
    static ArrayList<String> content = new ArrayList<>();
    static int[] vectorX = {1, 1, 0, -1, -1, -1, 0, 1};
    static int[] vectorY = {0, 1, 1, 1, 0, -1, -1, -1};
    static int total = 0;
    static Map<Integer, List<Integer>> map = new HashMap<>();
    
    public static void main(String[] args) {
        String filePath = "input.txt";
        File file = new File(filePath);

        writeInput(file, filePath);
        processInput();

        calculateTotal();
        System.out.println(total);
    }

    private static void writeInput(File file, String filePath) {
        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                content.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        }
    }

    private static void processInput() {
        for (int i = 0; i < content.size(); i++) {
            String line = content.get(i);
            int value = 0;
            int key = -1;
            
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                if (Character.isDigit(c)) {
                    value *= 10;
                    value += Character.getNumericValue(c);
                    key = Math.max(key, hasAdjacentStar(i, j));
                } else {
                    if (key > 0) {
                        map.putIfAbsent(key, new ArrayList<>());
                        map.get(key).add(value);
                    }
                    value = 0;
                    key = -1;
                }
            }

            if (key > 0) {
                map.putIfAbsent(key, new ArrayList<>());
                map.get(key).add(value);
            }
        }
    }

    private static int hasAdjacentStar(int i, int j) {
        for (int k = 0; k < vectorX.length; k++) {
            int x = i + vectorX[k];
            int y = j + vectorY[k];
            if (x >= 0 && x < content.size() && y >= 0 && y < content.get(x).length()) {
                if (content.get(x).charAt(y) == '*') {
                    return calculateHashCode(x, y);
                }
            }
        }
        return -1;
    }

    private static int calculateHashCode(int x, int y) {
        return x * 1000 + y;
    }

    private static void calculateTotal() {
        for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
            List<Integer> values = entry.getValue();
            if (values.size() == 2) {
                total += values.get(0) * values.get(1);
            }
        }
    }

}