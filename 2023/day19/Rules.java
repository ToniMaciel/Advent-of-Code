import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Rules {
    List<Function<Map<Character, Integer>, String>> functionList;
    
    public Rules() {
        functionList = new ArrayList<>();
    }

    public void addRules(String command) {
        String rules[] = command.split(",");

        for (String rule : rules) {
            String elements[] = rule.split(":");	
            
            if (elements.length == 2) {
                String ruleName = elements[0].trim();
                String ruleValue = elements[1].trim();
                
                char key = ruleName.charAt(0);
                char operation = ruleName.charAt(1);
                int value = Integer.parseInt(ruleName.substring(2));

                Function<Map<Character, Integer>, String> function = (map) -> {
                    if (operation == '>') {
                        return map.get(key) > value ? ruleValue : "";
                    } else {
                        return map.get(key) < value ? ruleValue : "";
                    }
                };

                functionList.add(function);
            } else {
                Function<Map<Character, Integer>, String> function = (map) -> {
                    return rule;
                };

                functionList.add(function);
            }
        }
    }
}
