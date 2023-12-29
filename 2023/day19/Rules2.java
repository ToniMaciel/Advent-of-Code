import java.util.ArrayList;
import java.util.List;

public class Rules2 {
    public Character key;
    public Character operation;
    public Integer value;
    public String output;

    public Rules2(Character key, Character operation, Integer value, String output) {
        this.key = key;
        this.operation = operation;
        this.value = value;
        this.output = output;
    }

    public static List<Rules2> getList(String command) {
        List<Rules2> list = new ArrayList<>();
        String rules[] = command.split(",");

        for (String rule : rules) {
            String elements[] = rule.split(":");	
            
            if (elements.length == 2) {
                String ruleName = elements[0].trim();
                String ruleValue = elements[1].trim();
                
                char key = ruleName.charAt(0);
                char operation = ruleName.charAt(1);
                int value = Integer.parseInt(ruleName.substring(2));

                list.add(new Rules2(key, operation, value, ruleValue));
            } else {
                list.add(new Rules2(null, null, null, rule));
            }
        }

        return list;
    }
}
