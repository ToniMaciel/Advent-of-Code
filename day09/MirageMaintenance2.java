import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MirageMaintenance2 {
    public static void main(String[] args) {
        String filePath = "input.txt";
        File file = new File(filePath);

        processInput(file, filePath);
    }

    private static void processInput(File file, String filePath) {
        try {
            Scanner scanner = new Scanner(file);
            long total = 0L;

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                List<Integer> numbers = Arrays.stream(line.split(" "))
                                            .map(Integer::parseInt)
                                            .collect(Collectors.toList());
                total += processNumbers(numbers);
            }
            System.out.println(total);
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        }
    }

    private static long processNumbers(List<Integer> numbers) {
        List<Long> initialNumbers = new ArrayList<>();

        while (!numbers.stream().allMatch(n -> n == 0)) {
            initialNumbers.add((long)numbers.get(0));
            for (int i = 0; i < numbers.size() - 1; i++) {
                numbers.set(i, numbers.get(i + 1) - numbers.get(i));
            }
            numbers.remove(numbers.size() - 1);
        }
        
        Collections.reverse(initialNumbers);
        return initialNumbers.stream().reduce(0L, (a, b) -> b - a).longValue();
    }
}
