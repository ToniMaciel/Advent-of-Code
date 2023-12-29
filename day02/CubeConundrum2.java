import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CubeConundrum2 {
    private static ArrayList<String> lines = new ArrayList<>();

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
            int red = 0, green = 0, blue = 0;

            for (String set : sets) {
                String[] colors = set.split(", ");

                for (String color : colors) {
                    String[] colorSplit = color.split(" ");
                    int colorValue = Integer.parseInt(colorSplit[0]);

                    if (colorSplit[1].equals("red")) {
                        red = Math.max(red, colorValue);
                    } else if (colorSplit[1].equals("green")) {
                        green = Math.max(green, colorValue);
                    } else if (colorSplit[1].equals("blue")) {
                        blue = Math.max(blue, colorValue);
                    }
                }
            }

            return red * green * blue;
        }).reduce(0, (first, second) -> first + second);

        System.out.println(answer);
    }

}