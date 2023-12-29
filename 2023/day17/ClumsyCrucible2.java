import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class ClumsyCrucible2 {
    enum Direction {
        UP, DOWN, LEFT, RIGHT
    };

    private static final String INPUT_FILE = "input.txt";
    private static List<char[]> grid = new ArrayList<>();
    private static Map<String, Integer> cost = new HashMap<>();

    
    public static void main(String[] args) {
        File file = new File(INPUT_FILE);

        readInput(file);
        makeCostMap();
        
        int x_goal = grid.size() - 1;
        int y_goal = grid.get(x_goal).length - 1;

        cost.entrySet()
            .stream()
            .filter(entry -> entry.getKey().startsWith(x_goal + "," + y_goal) && Integer.parseInt(entry.getKey().split(",")[3]) >= 3)
            .map(entry -> entry.getValue())
            .min(Integer::compare)
            .ifPresent(System.out::println);
    }

    private static void readInput(File file) {
        
        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                grid.add(line.toCharArray());
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + INPUT_FILE);
        }

    }

    private static void makeCostMap() {
        cost.put(codeCoordinates(0, 0, 0, 0), 0);
        cost.put(codeCoordinates(0, 0, 2, 0), 0);
        Flow flow_right = new Flow(0, 1, ClumsyCrucible2.Direction.RIGHT.ordinal(), 0, 0);
        Flow flow_down = new Flow(1, 0, ClumsyCrucible2.Direction.DOWN.ordinal(), 0, 0);
        Queue<Flow> queue = new LinkedList<>();
        queue.add(flow_right);
        queue.add(flow_down);

        while (!queue.isEmpty()) {
            Flow current = queue.poll();
            int i = current.x;
            int j = current.y;
            int costValue = current.cost;
            int sameDirection = current.sameDirection;
            ClumsyCrucible2.Direction direction = Direction.values()[current.dir];
            
            
            if (i < 0 || i >= grid.size() || j < 0 || j >= grid.get(i).length || sameDirection == 10) {
                continue;
            }
            
            char val = grid.get(i)[j];
            int newCost = costValue + (val - '0');
            
            if (cost.getOrDefault(codeCoordinates(i, j, direction.ordinal(), sameDirection), Integer.MAX_VALUE) <= newCost) {
                continue;
            }
            
            cost.put(codeCoordinates(i, j, direction.ordinal(), sameDirection), newCost);
            

            switch (direction) {
                case UP:
                    if (sameDirection < 3) {
                        queue.add(new Flow(i - 1, j, ClumsyCrucible2.Direction.UP.ordinal(), sameDirection + 1, newCost));
                        continue;
                    }
                    queue.add(new Flow(i - 1, j, ClumsyCrucible2.Direction.UP.ordinal(), sameDirection + 1, newCost));
                    queue.add(new Flow(i, j - 1, ClumsyCrucible2.Direction.LEFT.ordinal(), 0, newCost));
                    queue.add(new Flow(i, j + 1, ClumsyCrucible2.Direction.RIGHT.ordinal(), 0, newCost));
                    break;
                case DOWN:
                    if (sameDirection < 3) {
                        queue.add(new Flow(i + 1, j, ClumsyCrucible2.Direction.DOWN.ordinal(), sameDirection + 1, newCost));
                        continue;
                    }
                    queue.add(new Flow(i + 1, j, ClumsyCrucible2.Direction.DOWN.ordinal(), sameDirection + 1, newCost));
                    queue.add(new Flow(i, j - 1, ClumsyCrucible2.Direction.LEFT.ordinal(), 0, newCost));
                    queue.add(new Flow(i, j + 1, ClumsyCrucible2.Direction.RIGHT.ordinal(), 0, newCost));
                    break;
                case LEFT:
                    if (sameDirection < 3) {
                        queue.add(new Flow(i, j - 1, ClumsyCrucible2.Direction.LEFT.ordinal(), sameDirection + 1, newCost));
                        continue;
                    }
                    queue.add(new Flow(i, j - 1, ClumsyCrucible2.Direction.LEFT.ordinal(), sameDirection + 1, newCost));
                    queue.add(new Flow(i - 1, j, ClumsyCrucible2.Direction.UP.ordinal(), 0, newCost));
                    queue.add(new Flow(i + 1, j, ClumsyCrucible2.Direction.DOWN.ordinal(), 0, newCost));
                    break;
                case RIGHT:
                    if (sameDirection < 3) {
                        queue.add(new Flow(i, j + 1, ClumsyCrucible2.Direction.RIGHT.ordinal(), sameDirection + 1, newCost));
                        continue;
                    }
                    queue.add(new Flow(i, j + 1, ClumsyCrucible2.Direction.RIGHT.ordinal(), sameDirection + 1, newCost));
                    queue.add(new Flow(i - 1, j, ClumsyCrucible2.Direction.UP.ordinal(), 0, newCost));
                    queue.add(new Flow(i + 1, j, ClumsyCrucible2.Direction.DOWN.ordinal(), 0, newCost));
                    break;
            }
        }
    }

    private static String codeCoordinates(int i, int j, int dir, int qnt) {
        return (i + "," + j + "," + dir + "," + qnt);
    }
}
