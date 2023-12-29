import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CubeConundrum {
    private static ArrayList<String> lines = new ArrayList<>();
    private static final int RED = 12;
    private static final int GREEN = 13;
    private static final int BLUE = 14;

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
            String[] split = string.split(": ");
            String[] sets = split[1].split("; ");
            Boolean valid = true;

            for (String set : sets) {
                String[] colors = set.split(", ");

                for (String color : colors) {
                    valid &= validColorSet(color);
                }
            }

            return valid ? getId(split[0]) : 0;
        }).reduce(0, (first, second) -> first + second);

        System.out.println(answer);
    }

    private static Boolean validColorSet(String color) {
        Boolean valid = true;
        
        String[] colorSplit = color.split(" ");
        int colorValue = Integer.parseInt(colorSplit[0]);

        if (colorSplit[1].equals("red")) {
            valid &= colorValue <= RED;
        } else if (colorSplit[1].equals("green")) {
            valid &= colorValue <= GREEN;
        } else if (colorSplit[1].equals("blue")) {
            valid &= colorValue <= BLUE;
        }

        return valid;
    }

    private static int getId(String gameName) {
        return Integer.parseInt(gameName.split(" ")[1]);
    }

}