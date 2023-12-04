import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Scratchcards {
    static ArrayList<String> content = new ArrayList<>();

    public static void main(String[] args) {
        String filePath = "input.txt";
        File file = new File(filePath);

        writeInput(file, filePath);
        processInput();
    }

    private static void writeInput(File file, String filePath) {
        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                content.add(scanner.nextLine().split(":")[1].trim());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        }
    }

    private static void processInput() {
        content.stream()
            .map((line) -> {
                String[] numbers = line.split("\\|");

                Set<Integer> winningNumSet = new HashSet<>(selectNumbers(numbers[0]));
                Set<Integer> myNumSet = new HashSet<>(selectNumbers(numbers[1]));

                winningNumSet.retainAll(myNumSet);
                return (winningNumSet.size() == 0 ? 0 : 1 << (winningNumSet.size() - 1));
            })
            .reduce((sum, value) -> sum + value)
            .ifPresent(System.out::println);
    }

    private static List<Integer> selectNumbers(String listString) {
        return Arrays.asList(listString.trim().split(" ")).stream().filter((number) -> !(number.trim().isEmpty())).map(Integer::parseInt).collect(Collectors.toList());
    }
}