import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.AbstractMap.SimpleEntry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.HashSet;

public class CosmicExpansion2 {
    static List<SimpleEntry<Long, Long>> galaxies = new ArrayList<>();

    public static void main(String[] args) {
        String filePath = "input.txt";
        File file = new File(filePath);

        readInput(file, filePath);
        calculateDistancesBetweenGalaxies();
    }

    private static void readInput(File file, String filePath) {
        
        try {
            Scanner scanner = new Scanner(file);
            Set<Integer> columnsWithGalaxies = new HashSet<>();
            Set<Integer> rowsWithGalaxies = new HashSet<>();
            int rows = 0;
            int columns = 0;

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                columns = line.length();
                rows++;

                if (line.contains("#")) {
                    rowsWithGalaxies.add(rows - 1);
                    for (int i = 0; i < line.length(); i++) {
                        if (line.charAt(i) == '#') {
                            columnsWithGalaxies.add(i);
                            galaxies.add(new SimpleEntry<>((long) rows - 1,(long) i));
                        }
                    }
                }
            }
            
            shiftRightGalaxies(columnsWithGalaxies, columns);
            shiftDownGalaxies(rowsWithGalaxies, rows);
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        }

    }
    
    private static void shiftRightGalaxies(Set<Integer> columnsWithGalaxies, int columns) {
        Set<Integer> emptyColumns = IntStream.range(0, columns)
            .boxed()
            .collect(Collectors.toSet());
        emptyColumns.removeAll(columnsWithGalaxies);
        
        galaxies.forEach(galaxy -> {
            long column = galaxy.getValue();
            long shift = emptyColumns.stream().filter(c -> c < column).count();
            galaxy.setValue(column + (shift * 999999));
        });
    }

    private static void shiftDownGalaxies(Set<Integer> rowsWithGalaxies, int rows) {
        Set<Integer> emptyRows = IntStream.range(0, rows)
            .boxed()
            .collect(Collectors.toSet());
        emptyRows.removeAll(rowsWithGalaxies);
        
        galaxies = galaxies.stream().map(galaxy -> {
            long row = galaxy.getKey();
            long shift = emptyRows.stream().filter(r -> r < row).count();
            SimpleEntry<Long, Long> newGalaxy = new SimpleEntry<>(row + (shift * 999999), galaxy.getValue());
            return newGalaxy;
        }).collect(Collectors.toList());
    }

    private static void calculateDistancesBetweenGalaxies() {
        BigInteger sumDistances = new BigInteger("0");
        for (int i = 0; i < galaxies.size() - 1; i++) {
            SimpleEntry<Long, Long> galaxy1 = galaxies.get(i);
            for (int j = i + 1; j < galaxies.size(); j++) {
                SimpleEntry<Long, Long> galaxy2 = galaxies.get(j);
                long distance = getDistance(galaxy1, galaxy2);
                sumDistances = sumDistances.add(BigInteger.valueOf(distance));
            }
        }
        System.out.println(sumDistances);
    }

    private static long getDistance(SimpleEntry<Long, Long> galaxy1, SimpleEntry<Long, Long> galaxy2) {
        return Math.abs(galaxy1.getKey() - galaxy2.getKey()) + Math.abs(galaxy1.getValue() - galaxy2.getValue());
    }
}
