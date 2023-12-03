import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class GearRatios {
    static ArrayList<String> content = new ArrayList<>();
    static int[] vectorX = {1, 1, 0, -1, -1, -1, 0, 1};
    static int[] vectorY = {0, 1, 1, 1, 0, -1, -1, -1};
    static int total = 0;
    public static void main(String[] args) {
        String filePath = "input.txt";
        File file = new File(filePath);

        writeInput(file, filePath);
        processInput();

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
            boolean shouldCount = false;
            
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                if (Character.isDigit(c)) {
                    value *= 10;
                    value += Character.getNumericValue(c);
                    shouldCount |= validChar(i, j);
                } else {
                    if (shouldCount) {
                        total += value;
                    }
                    value = 0;
                    shouldCount = false;
                }
            }

            if (shouldCount) {
                total += value;
            }
        }
    }

    private static boolean validChar(int row, int column) {
        for (int i = 0; i < vectorX.length; i++) {
            int x = row + vectorX[i];
            int y = column + vectorY[i];
            if (x >= 0 && x < content.size() && y >= 0 && y < content.get(x).length()) {
                if (!Character.isDigit(content.get(x).charAt(y)) && !(content.get(x).charAt(y) == '.')) {
                    return true;
                }
            }
        }
        return false;
    }
}