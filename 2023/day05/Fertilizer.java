import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.AbstractMap.SimpleEntry;
import java.util.Collections;

public class Fertilizer {
    static Map<Long, Long> conversor = new HashMap<>();
    static ArrayList<SimpleEntry<Long, Long>> ranges = new ArrayList<>();
    static ArrayList<Long> seeds = new ArrayList<>();

    public static void main(String[] args) {
        String filePath = "input.txt";
        File file = new File(filePath);

        processInput(file, filePath);
        convertSeeds();

        System.out.println(Collections.min(seeds));
    }

    private static void processInput(File file, String filePath) {
        try {
            Scanner scanner = new Scanner(file);
            String seedsLine = scanner.nextLine();
            scanner.nextLine();
            processSeedsLine(seedsLine);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                processInputLine(line);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        }
    }

    private static void processSeedsLine(String seedsLine) {
        String[] seedsArray = seedsLine.split(":")[1].trim().split(" ");
        for (String seed : seedsArray) {
            seeds.add(Long.parseLong(seed));
        }
    }

    private static void processInputLine(String line) {
        if (line.isBlank()) {
            convertSeeds();
        } else if (line.contains("map")) {
            conversor.clear();
            ranges.clear();
        } else {
            String[] map = line.split(" ");
            Long range = Long.parseLong(map[2]);
            conversor.put(Long.parseLong(map[1]), Long.parseLong(map[0]));
            ranges.add(new SimpleEntry<>(Long.parseLong(map[1]), range));
        }	
    }

    private static void convertSeeds() {
        ranges.sort((a, b) -> a.getKey().compareTo(b.getKey()));
        for (int i = 0; i < seeds.size(); i++) {
            Long seed = seeds.get(i);
            for (SimpleEntry<Long, Long> range : ranges) {
                if (seed >= range.getKey() && seed <= (range.getKey() + range.getValue())) {
                    seed = (seed - range.getKey()) + conversor.get(range.getKey());
                    break;
                } else if (seed < range.getKey()) {
                    break;
                }
            }
            seeds.set(i, seed);
        }
    }
}