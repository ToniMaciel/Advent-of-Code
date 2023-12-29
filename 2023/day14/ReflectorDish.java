import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class ReflectorDish {
    
    public static void main(String[] args) {
        String filePath = "input.txt";
        File file = new File(filePath);

        processInput(file, filePath);
    }

    private static void processInput(File file, String filePath) {
        
        try {
            Scanner scanner = new Scanner(file);
            List<char[]> field = new ArrayList<>();

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                field.add(line.toCharArray());
            }

            scanner.close();
            calculateLoad(field);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        }

    }

    private static void calculateLoad(List<char[]> field) {
        slideRocksToNorth(field);
        long load = IntStream.range(0, field.size())
                    .mapToLong(i -> countOSymbols(field.get(i)) * (field.size() - i))
                    .sum();
        System.out.println(load);
    }

    private static void slideRocksToNorth(List<char[]> field) {
        for (int i = 0; i < field.size(); i++) {
            char[] row = field.get(i);
            for (int j = 0; j < row.length; j++) {
                if (row[j] == 'O') {
                    slideRockToNorth(field, i, j);
                }
            }
        }
    }

    private static void slideRockToNorth(List<char[]> field, int i, int j) {
        while (i > 0) {
            char[] row = field.get(i - 1);
            if (row[j] != '.') {
                break;
            }

            row[j] = 'O';
            field.get(i)[j] = '.';
            i--;
        }
    }

    private static int countOSymbols(char[] row) {
        return (int) IntStream.range(0, row.length)
                    .filter(i -> row[i] == 'O')
                    .count();
    }
}
