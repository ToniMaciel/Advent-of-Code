import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class Snowverload {
    private static Map<String, List<String>> map = new HashMap<>();
    private static String component;

    public static void main(String[] args) {
        String filePath = "input.txt";
        File file = new File(filePath);

        readInput(file, filePath);
        calculateComponentsLength();
    }

    private static void readInput(File file, String filePath) {
        
        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] lineComponents = line.split(": ");
                component = lineComponents[0];

                map.putIfAbsent(component, new ArrayList<>());
                
                Arrays.stream(lineComponents[1].split(" ")).forEach(s -> {
                    map.get(component).add(s);
                    map.putIfAbsent(s, new ArrayList<>());
                    map.get(s).add(component);
                });
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        }

    }

    private static void calculateComponentsLength() {
        int sameSide = 1;
        int otherSide = 0;

        for (String key : map.keySet()) {
            if (key.equals(component)) {
                continue;
            }
    
            Set<String> used = new HashSet<>();
            used.add(component);
            int occurances = 0;
    
            for (String initial : map.get(component)) {
                if (occurances > 3) {
                    break;
                }

                Path path = new Path(initial);
                Queue<Path> queue = new LinkedList<>();
                queue.add(path);
                Set<String> alreadyInProcess = new HashSet<>();
    
                while (!queue.isEmpty()) {
                    Path current = queue.poll();
    
                    if (current.lastUsed.equals(key)) {
                        occurances++;
                        used.addAll(current.used);
                        used.remove(key);
                        break;
                    }
    
                    for (String s : map.get(current.lastUsed)) {
                        if (!alreadyInProcess.contains(s) && !used.contains(s) && !current.used.contains(s)) {
                            Path newPath = new Path(current);
                            newPath.used.add(s);
                            newPath.lastUsed = s;
    
                            queue.add(newPath);
                            alreadyInProcess.add(s);
                        }
                    }
                }
            }
    
            if (occurances >= 4) {
                sameSide++;
            } else {
                otherSide++;
            }	
        }

        System.out.println(sameSide * otherSide);
    }
}
