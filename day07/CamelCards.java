import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.stream.IntStream;

public class CamelCards {
    static ArrayList<Card> cards = new ArrayList<>();

    public static void main(String[] args) {
        String filePath = "input.txt";
        File file = new File(filePath);

        readInput(file, filePath);
        Collections.sort(cards);
        calculateCardsBind();
    }

    private static void readInput(File file, String filePath) {
        try {
            Scanner scanner = new Scanner(file);
            
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] split = line.split(" ");
                cards.add(new Card(split[0], Integer.parseInt(split[1])));
            }
            
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        }
    }
    
    private static void calculateCardsBind() {
        int totalBind = IntStream.range(0, cards.size())
                .map(i -> cards.get(i).bind * (i + 1))
                .sum();
        System.out.println(totalBind);
    }
}
