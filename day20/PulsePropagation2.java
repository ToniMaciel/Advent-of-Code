import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class PulsePropagation2 {
    private static final String INPUT_FILE = "input.txt";
    private static Map<String, List<Module>> modules = new HashMap<>();
    private static Map<String, Module> modulesPool = new HashMap<>();
    private static Set<String> neededConjuctions = new HashSet<>();
    private static List<Long> clicks = new ArrayList<>();


    public static void main(String[] args) {
        File file = new File(INPUT_FILE);

        processInput(file);
        getNeededConjuctions();
        makePropagations();

        System.out.println(clicks.stream().reduce(1L, (a, b) -> lcm(a, b)));
    }

    private static void processInput(File file) {
        
        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" -> ");

                String keyInput = parts[0].substring(1);
                Module module;

                switch (parts[0].charAt(0)) {
                    case '%':
                        module = new FlipFlop(keyInput);
                        modulesPool.put(keyInput, module);
                        break;
                    case '&':
                        module = new Conjuction(keyInput);
                        modulesPool.put(keyInput, module);    
                        break;
                    default:
                        module = new Broadcaster(keyInput);
                        modulesPool.put("broadcaster", module);
                        break;
                }

                String[] outputs = parts[1].split(", ");

                if (modules.containsKey(keyInput)) {
                    for (Module input : modules.get(keyInput)) {
                        module.inputs.add(input);
                        input.outputs.add(module);
                    }
                }
                
                for (String output : outputs) {
                   if (modulesPool.containsKey(output)) {
                        module.outputs.add(modulesPool.get(output));
                        modulesPool.get(output).inputs.add(module);
                   } else {
                        modules.putIfAbsent(output, new ArrayList<>());
                        modules.get(output).add(module);
                   }
                }
            }

            modules.entrySet()
                .stream()
                .filter(e -> !modulesPool.containsKey(e.getKey()))
                .forEach(e -> {
                    Module module = new Broadcaster(e.getKey());
                    e.getValue().forEach(m -> {
                        m.outputs.add(module);
                        module.inputs.add(m);
                    });
                    modulesPool.put(e.getKey(), module);
                });

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + INPUT_FILE);
        }

    }

    private static void getNeededConjuctions() {
        Module target = modulesPool.get("rx");
        Queue<Module> queue = new LinkedList<>();
        queue.add(target);

        while (!queue.isEmpty()) {
            Module module = queue.poll();
            module.inputs.stream().filter(i -> i instanceof Conjuction).forEach(i -> {
                i.state = !i.state;
                neededConjuctions.add(i.name + i.state);
                queue.add(i);
            });
        }
    }

    private static void makePropagations() {
        Queue<Pulse> queue = new LinkedList<>();
        long counts = 0L;

        while (neededConjuctions.size() > 0) {
            counts++;
            queue.add(new Pulse(null, modulesPool.get("broadcaster"), false));
            while (!queue.isEmpty()) {
                Pulse pulse = queue.poll();
                Boolean value = pulse.target.newPulse(pulse.value, pulse.source);
                
                if (value != null) {
                    for (Module output : pulse.target.outputs) {
                        queue.add(new Pulse(pulse.target, output, value));
                    }

                    if (pulse.target instanceof Conjuction) {
                        if (neededConjuctions.contains(pulse.target.name + value)) {
                            clicks.add(counts);
                            neededConjuctions.remove(pulse.target.name + value);
                        }
                    }
                }
            }
        }
    }

    private static Long lcm(Long a, Long b) {
        return a * (b / gcd(a, b));
    }

    private static Long gcd(Long a, Long b) {
        if (b == 0) {
            return a;
        } else {
            return gcd(b, a % b);
        }
    }
}
