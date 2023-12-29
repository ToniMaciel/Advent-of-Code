import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Aplenty2 {
    private static final String INPUT_FILE = "input.txt";
    private static Map<String, List<Rules2>> rulesMap = new HashMap<>();
    private static Long combinations = 0L;

    public static void main(String[] args) {
        File file = new File(INPUT_FILE);

        processInput(file);
        Constraints constraints = new Constraints();
        calculateCombinations("in", constraints);
        System.out.println(combinations);
    }

    private static void processInput(File file) {
        
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                
                if (line.isEmpty()) {
                    break;
                }

                String elements[] = line.split("\\{");
                rulesMap.put(elements[0], Rules2.getList(elements[1].substring(0, elements[1].length() - 1)));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + INPUT_FILE);
        }

    }

    private static void calculateCombinations(String key, Constraints constraints) {
        if (key.equals("A")) {
            combinations += constraints.getCombinations();
            return;
        } else if (key.equals("R")) {
            return;
        }

        List<Rules2> rules = rulesMap.get(key);
        for (Rules2 rule : rules) {
            if (rule.key == null) {
                calculateCombinations(rule.output, constraints);
            } else {
                if (rule.operation == '>') {
                    Constraints newConstraints = new Constraints(constraints.map);   
                    newConstraints.map.get(rule.key).set(0, Math.max(newConstraints.map.get(rule.key).get(0), rule.value + 1));
                    constraints.map.get(rule.key).set(1, Math.min(constraints.map.get(rule.key).get(1), rule.value));
                    calculateCombinations(rule.output, newConstraints);
                } else {
                    Constraints newConstraints = new Constraints(constraints.map);
                    constraints.map.get(rule.key).set(0, Math.max(newConstraints.map.get(rule.key).get(0), rule.value));  
                    newConstraints.map.get(rule.key).set(1, Math.min(newConstraints.map.get(rule.key).get(1), rule.value - 1));
                    calculateCombinations(rule.output, newConstraints);
                }
            }
        }
    }
}
