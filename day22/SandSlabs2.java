import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.util.Queue;
import java.util.LinkedList;

public class SandSlabs2 {
    private static List<Brick> bricks = new ArrayList<>();
    private static Map<Integer, List<Brick>> levelsOccupation = new HashMap<>();
    public static void main(String[] args) {
        String filePath = "input.txt";
        File file = new File(filePath);

        readInput(file, filePath);
        Collections.sort(bricks);
        fallBricks();
        countFallBricks();
    }

    private static void readInput(File file, String filePath) {
        
        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] lineArray = line.split("~");
                int[] init = Arrays.stream(lineArray[0].split(",")).mapToInt(Integer::parseInt).toArray();
                int[] end = Arrays.stream(lineArray[1].split(",")).mapToInt(Integer::parseInt).toArray();

                bricks.add(new Brick(bricks.size(), init, end));
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        }

    }

    private static void fallBricks() {
        Brick firstBrick = bricks.get(0);
        int delta = firstBrick.init[2] - 1;
        firstBrick.init[2] = 1;
        firstBrick.end[2] = firstBrick.end[2] - delta;

        for (int i = 1; i <= firstBrick.end[2]; i++) {
            if (levelsOccupation.getOrDefault(i, null) == null) {
                levelsOccupation.put(i, new ArrayList<>());
            }

            levelsOccupation.get(i).add(firstBrick);
        }

        for (int i = 1; i < bricks.size(); i++) {
            Brick brick = bricks.get(i);
            delta = 0;
            boolean canFall = true;

            for (int j = brick.init[2]; j > 0 && canFall; j--) {
                List<Brick> level = levelsOccupation.getOrDefault(j, null);

                if (level != null) {
                    for (Brick brickInLevel : level) {
                        if ((brickInLevel.init[0] > brick.end[0] || brickInLevel.end[0] < brick.init[0]) ||
                            (brickInLevel.init[1] > brick.end[1] || brickInLevel.end[1] < brick.init[1])) {
                            continue;
                        }

                        canFall = false;
                        brick.supporters.add(brickInLevel);
                        brickInLevel.supported.add(brick);
                    }
    
                    if (!canFall) {
                        break;
                    }
                }

                delta++;
            }

            brick.init[2] = brick.init[2] - delta + 1;
            brick.end[2] = brick.end[2] - delta + 1;

            for (int j = brick.init[2]; j <= brick.end[2]; j++) {
                if (levelsOccupation.getOrDefault(j, null) == null) {
                    levelsOccupation.put(j, new ArrayList<>());
                }

                levelsOccupation.get(j).add(brick);
            }
        }
    }

    public static void countFallBricks(){
        Optional<Integer> count = bricks.stream()
                            .filter(brick -> brick.supporters.size() == 1)
                            .map(brick -> brick.supporters.get(0))
                            .distinct()
                            .map(brick -> getBrickFallCount(brick))
                            .reduce(Integer::sum);
        System.out.println(count.get());
    }

    private static int getBrickFallCount(Brick brick) {
        Set<Brick> bricksToFall = new HashSet<>();
        Queue<Brick> queue = new LinkedList<>();
        queue.add(brick);

        while (!queue.isEmpty()) {            
            Brick currentBrick = queue.poll();
            bricksToFall.add(currentBrick);
            for (Brick supportedBrick : currentBrick.supported) {
                if (supportedBrick.supporters.stream().allMatch(supporterBrick -> bricksToFall.contains(supporterBrick))) {
                    queue.add(supportedBrick);
                }
            }
        }

        return bricksToFall.size() - 1;
    }
}
