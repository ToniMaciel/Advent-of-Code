import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class HotSprings2 {
    private static Map<String, Long> cache = new HashMap<>();
    public static void main(String[] args) {
        String filePath = "input.txt";
        File file = new File(filePath);

        processInput(file, filePath);
    }

    private static void processInput(File file, String filePath) {
        
        try {
            Scanner scanner = new Scanner(file);
            long arrangaments = 0;

            while (scanner.hasNext()) {
                String[] line = scanner.nextLine().split(" ");
                StringBuilder springBuilder = new StringBuilder(line[0]);
                StringBuilder sbOccurances = new StringBuilder(line[1]);
                
                for (int i = 0; i < 4; i++) {
                    springBuilder.append("?").append(line[0]);
                    sbOccurances.append(",").append(line[1]);
                }
                
                String spring = springBuilder.toString();
                List<Integer> occurrances = Arrays.stream(sbOccurances.toString().split(",")).map(Integer::parseInt).collect(Collectors.toList());

                arrangaments += calculateArrangaments(spring, occurrances);
            }


            scanner.close();
            System.out.println(arrangaments);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        }

    }

    private static long calculateArrangaments(String spring, List<Integer> data) {
        String key = spring + "$" + data.toString();
        
        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        if (spring.isEmpty()) {
            return data.isEmpty() ? 1 : 0;
        }

        
        long arrangaments = 0;
        
        switch (spring.charAt(0)) {
            case '.':
                arrangaments = calculateArrangaments(spring.substring(1), data);
                break;
            case '?':
                arrangaments = calculateArrangaments(spring.substring(1), data);
                arrangaments += calculateArrangaments("#" + spring.substring(1), data);
                break;
            default:
                if (data.isEmpty()) {
                    break;
                }

                int neededSequence = data.get(0);
                int actualSequence = (int) spring.chars().limit(Math.min(neededSequence, spring.length())).takeWhile(c -> c == '#' || c == '?').count();

                if (actualSequence < neededSequence) {
                    break;
                } else if (actualSequence == spring.length()) {
                    arrangaments = data.size() == 1 ? 1 : 0;
                } else {
                    switch (spring.charAt(neededSequence)) {
                        case '#':
                            break;
                        default:
                            arrangaments = calculateArrangaments(spring.substring(neededSequence + 1), data.subList(1, data.size()));                            
                            break;
                    }
                }
        }

        cache.put(key, arrangaments);
        return arrangaments;
    }
}
