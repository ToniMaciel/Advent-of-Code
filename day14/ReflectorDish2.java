import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.IntStream;

public class ReflectorDish2 {
    private static Set<Long> visited = new HashSet<>();
    private static Map<Long, Integer> cycles = new HashMap<>();
    private static List<Long> cyclesLoad = new ArrayList<>();
    
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
        for (int i = 0; i < 1000000000; i++) {
            cycle(field);
            long hash = hashValue(field);
            if (visited.contains(hash)) {
                getLoad(field, cycles.get(hash), i);
                break;
            }
            visited.add(hash);
            cyclesLoad.add(loadValue(field));
            cycles.put(hash, i);
        }
    }
    
    private static void cycle(List<char[]> field) {
        slideRocksToNorth(field);
        slideRocksToWest(field);
        slideRocksToSouth(field);
        slideRocksToEast(field);
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
    
    private static void slideRocksToWest(List<char[]> field) {
        for (int i = 0; i < field.size(); i++) {
            char[] row = field.get(i);
            for (int j = 0; j < row.length; j++) {
                if (row[j] == 'O') {
                    slideRockToWest(field, i, j);
                }
            }
        }
    }

    private static void slideRockToWest(List<char[]> field, int i, int j) {
        while (j > 0) {
            char[] row = field.get(i);
            if (row[j - 1] != '.') {
                break;
            }
            
            row[j - 1] = 'O';
            row[j] = '.';
            j--;
        }
    }

    private static void slideRocksToSouth(List<char[]> field) {
        for (int i = field.size() - 1; i >= 0; i--) {
            char[] row = field.get(i);
            for (int j = 0; j < row.length; j++) {
                if (row[j] == 'O') {
                    slideRockToSouth(field, i, j);
                }
            }
        }
    }

    private static void slideRockToSouth(List<char[]> field, int i, int j) {
        while (i < field.size() - 1) {
            char[] row = field.get(i + 1);
            if (row[j] != '.') {
                break;
            }
            
            row[j] = 'O';
            field.get(i)[j] = '.';
            i++;
        }
    }

    private static void slideRocksToEast(List<char[]> field) {
        for (int i = 0; i < field.size(); i++) {
            char[] row = field.get(i);
            for (int j = row.length - 1; j >= 0; j--) {
                if (row[j] == 'O') {
                    slideRockToEast(field, i, j);
                }
            }
        }
    }

    private static void slideRockToEast(List<char[]> field, int i, int j) {
        while (j < field.get(i).length - 1) {
            char[] row = field.get(i);
            if (row[j + 1] != '.') {
                break;
            }
            
            row[j + 1] = 'O';
            row[j] = '.';
            j++;
        }
    }
    
    private static long hashValue(List<char[]> field) {
        long hash = 0;
        for (int i = 0; i < field.size(); i++) {
            char[] row = field.get(i);
            for (int j = 0; j < row.length; j++) {
                if (row[j] == 'O') {
                    hash += i * j;
                }
            }
        }
        return hash;
    }

    private static void getLoad(List<char[]> field, int cycleIndex, int currentIndex) {
        int cycleLength = currentIndex - cycleIndex;
        int index = (1000000000 - cycleIndex) % cycleLength;
        System.out.println(cyclesLoad.get(cycleIndex + index - 1));
    }

    private static Long loadValue(List<char[]> field) {
        return IntStream.range(0, field.size())
                    .mapToLong(i -> countOSymbols(field.get(i)) * (field.size() - i))
                    .sum();
    }

    private static int countOSymbols(char[] row) {
        return (int) IntStream.range(0, row.length)
                    .filter(i -> row[i] == 'O')
                    .count();
    }
}
