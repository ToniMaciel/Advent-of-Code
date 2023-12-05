import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.AbstractMap.SimpleEntry;

public class Fertilizer2 {
    static Map<Long, Long> conversor = new HashMap<>();
    static ArrayList<SimpleEntry<Long, Long>> ranges = new ArrayList<>();
    static ArrayList<SimpleEntry<Long, Long>> seeds = new ArrayList<>();

    public static void main(String[] args) {
        String filePath = "input.txt";
        File file = new File(filePath);

        processInput(file, filePath);
        convertSeeds();

        System.out.println(Collections.min(seeds, (a, b) -> a.getKey().compareTo(b.getKey())).getKey());
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
        for (int i = 0; i < seedsArray.length; i += 2) {
            seeds.add(new SimpleEntry<>(Long.parseLong(seedsArray[i]), Long.parseLong(seedsArray[i]) + Long.parseLong(seedsArray[i + 1]) - 1));
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
            ranges.add(new SimpleEntry<>(Long.parseLong(map[1]), Long.parseLong(map[1]) + range - 1));
        }	
    }

    private static void convertSeeds() {
        ranges.sort((a, b) -> a.getKey().compareTo(b.getKey()));
        for (int i = 0; i < seeds.size(); i++) {
            SimpleEntry<Long, Long> seed = seeds.get(i);
            Long seedLeft = seed.getKey();
            Long seedRight = seed.getValue();

            for (SimpleEntry<Long, Long> range : ranges) {
                Long rangeLeft = range.getKey();
                Long rangeRight = range.getValue();

                if (seedLeft < rangeLeft) {
                    if (seedRight >= rangeLeft) {
                        SimpleEntry<Long, Long> newSeed = new SimpleEntry<>(seedLeft, rangeLeft - 1);
                        seeds.add(newSeed);
                        
                        seedLeft = conversor.get(rangeLeft);
                        seedRight = seedLeft + (seedRight - rangeLeft);	
                        break;
                    } else {
                        break;
                    }
                } else {
                    if (seedLeft <= rangeRight) {
                        if (rangeRight < seedRight) {
                            SimpleEntry<Long, Long> newSeed = new SimpleEntry<>(rangeRight + 1, seedRight);
                            seeds.add(newSeed);
                            
                            seedRight = (rangeRight - seedLeft);
                        } else {
                            seedRight = (seedRight - seedLeft);
                        }
                        seedLeft = conversor.get(rangeLeft) + (seedLeft - rangeLeft);
                        seedRight += seedLeft;
                        break;
                    } else {
                        continue;
                    }
                }
            }
            seed = new SimpleEntry<>(seedLeft, seedRight);
            seeds.set(i, seed);
        }
    }
}