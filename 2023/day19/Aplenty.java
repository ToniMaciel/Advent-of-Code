import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Aplenty {
    private static final String INPUT_FILE = "input.txt";
    private static Map<String, Rules> rulesMap = new HashMap<>();

    public static void main(String[] args) {
        File file = new File(INPUT_FILE);

        processInput(file);
    }

    private static void processInput(File file) {
        
        try {
            Scanner scanner = new Scanner(file);
            long sum = 0;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                
                if (line.isEmpty()) {
                    break;
                }

                String elements[] = line.split("\\{");
                Rules rules = new Rules();
                rules.addRules(elements[1].substring(0, elements[1].length() - 1));
                rulesMap.put(elements[0], rules);

            }

            while (scanner.hasNextLine()) {
                Map<Character, Integer> map = createMapValidator(scanner.nextLine());
                String workflow = "in";

                Rules rules = rulesMap.get(workflow);
                for (int i = 0; i < rules.functionList.size(); i++) {
                    workflow = rules.functionList.get(i).apply(map);
                    
                    if (workflow.equals("R") || workflow.equals("A")) {
                        break;
                    } else if (!workflow.isEmpty()) {
                        rules = rulesMap.get(workflow);
                        i = -1;
                    } 
                }

                if (workflow.equals("A")) {
                    sum += map.values().stream().mapToInt(Integer::intValue).sum();
                }
                
            }

            scanner.close();
            System.out.println(sum);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + INPUT_FILE);
        }

    }

    private static Map<Character, Integer> createMapValidator(String line) {
        line = line.replace("{", "").replace("}", "");
        Map<Character, Integer> map = new HashMap<>();
        String elements[] = line.split(",");
        
        for (String element : elements) {
            String keyValue[] = element.split("=");
            map.put(Character.valueOf(keyValue[0].charAt(0)), Integer.parseInt(keyValue[1]));
        }

        return map;
    }
}
