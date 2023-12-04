import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Scratchcards2 {
    static ArrayList<String> content = new ArrayList<>();

    public static void main(String[] args) {
        String filePath = "input.txt";
        File file = new File(filePath);

        writeInput(file, filePath);
        int[] cardsCopies = new int[content.size()];
        processInput(cardsCopies);
        calculateCards(cardsCopies);
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

    private static void processInput(int[] cardsCopies) {
        for (int index = 0; index < content.size(); index++) {
            cardsCopies[index]++;

            String line = content.get(index);
            String[] numbers = line.split("\\|");

            Set<Integer> winningNumSet = new HashSet<>(selectNumbers(numbers[0]));
            Set<Integer> myNumSet = new HashSet<>(selectNumbers(numbers[1]));

            winningNumSet.retainAll(myNumSet);

            int copies = winningNumSet.size();
            
            for (int i = index + 1; i <= index + copies; i++) {
                cardsCopies[i] += cardsCopies[index];
            }
        }
    }

    private static List<Integer> selectNumbers(String listString) {
        return Arrays.asList(listString.trim().split(" ")).stream().filter((number) -> !(number.trim().isEmpty())).map(Integer::parseInt).collect(Collectors.toList());
    }

    private static void calculateCards(int[] cardsCopies) {
        int totalCards = 0;

        for (int copies : cardsCopies) {
            totalCards += copies;
        }

        System.out.println(totalCards);
    }
}